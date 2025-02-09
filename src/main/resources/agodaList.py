import re
import sys
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, StaleElementReferenceException, NoSuchElementException
import time
from selenium.webdriver.chrome.options import Options 
from selenium.webdriver.common.action_chains import ActionChains
from webdriver_manager.chrome import ChromeDriverManager
from urllib.parse import urlparse, parse_qs, urlencode, urlunparse

options = Options()
# options.add_argument("--headless")
# options.add_argument("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
options.add_argument("--disable-gpu")
options.add_argument("--window-size=1920,1080")
options.add_argument("--disable-blink-features=AutomationControlled")
options.add_experimental_option("excludeSwitches", ["enable-automation"])
options.add_experimental_option("useAutomationExtension", False)

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
driver.implicitly_wait(5)

def agodaFind(inputData):
    driver.get("https://www.agoda.com/zh-tw/?site_id=1922895&tag=e9ea26c2-c046-468f-939d-97d11075d6e0&gad_source=1&device=c&network=g&adid=702678770791&rand=9547786221666415290&expid=&adpos=&aud=kwd-2230651387&gclid=EAIaIQobChMI_abB68LCigMVTcdMAh1hEzFZEAAYASAAEgK--fD_BwE&pslc=1&ds=Q8goOEZePfADNhzP")
    try:
        #輸入地點
        driver.find_element(By.ID, "textInput").send_keys(inputData["area"])
        time.sleep(0.5)
        #解除focus狀態
        ActionChains(driver).move_by_offset(100, 100).click().perform()
        #點擊搜尋按鈕
        driver.find_element(By.CSS_SELECTOR, "button[data-selenium='searchButton']").click()

        while True:
            # 獲取所有窗口的句柄（視窗）
            window_handles = driver.window_handles
            # 新視窗產生結束檢查
            if (len(window_handles) > 1):
				driver.switch_to.window(window_handles[-1])
				page_url = driver.current_url
                break
        	if (len(window_handles) == 1):
				page_url = driver.current_url
				break


    except Exception as e:
        pass
    parsed_url = urlparse(page_url)
    query_params = parse_qs(parsed_url.query)

    # 更新參數
    query_params['checkIn'] = inputData["checkinDate"]
    query_params['checkOut'] = inputData["checkoutDate"]
    query_params['rooms'] = inputData["rooms"]
    query_params['adults'] = inputData["adults"]
    query_params['children'] = inputData["children"]
    age =""
    if "childages" in query_params:
        for i in range (int(inputData["children"])):
            if i == int(inputData["children"])-1:
                age = age + "10"
            else:
                age = age + "10,"
    # 重組 URL
    updated_query = urlencode(query_params, doseq=True)
    updated_url = urlunparse(parsed_url._replace(query=updated_query))

    driver.get(updated_url)
    nameSet = set()
    allData = []
    resp =[]

    if "number" in inputData:
        max_number = int(inputData["number"])
    else:
        max_number = 300

    start_numbe = 0

    while (len(resp)+len(allData)) < max_number:
        
        if len(allData) != 0 and start_numbe == len(allData):
            break
        start_numbe = len(allData)
        
        card = driver.find_elements(By.CSS_SELECTOR, "[aria-label='住宿欄位']")
        for i in range (start_numbe,len(card)):
            try:
                card = driver.find_elements(By.CSS_SELECTOR, "[aria-label='住宿欄位']")
                #滑動卷軸到目標卡片
                driver.execute_script("arguments[0].scrollIntoView();", card[i])

                name = card[i].find_element(By.CSS_SELECTOR, '[data-selenium="hotel-name"]').text
                if name in nameSet:
                    continue
                price_div = WebDriverWait(card[i], 0.25).until(EC.visibility_of_element_located((By.CSS_SELECTOR, 'div[data-element-name="final-price"]'))).text
                if len(price_div.split("\n")) != 1:
                    currency = price_div.split("\n")[0]
                    price=int(price_div.split("\n")[1].replace(",",""))
                elif len(price_div.split(" ")) != 1:
                    currency = price_div.split(" ")[0]
                    price=int(price_div.split(" ")[1].replace(",",""))
                
                if currency == "NT$":
                    currency = "TWD"
                img = card[i].find_element(By.CSS_SELECTOR, "button[data-element-name='ssrweb-mainphoto'] img").get_attribute("src")
                url = card[i].find_element(By.CSS_SELECTOR, "a[data-element-name='property-card-content']").get_attribute("href")
                opinion = card[i].find_element(By.CSS_SELECTOR, '[data-element-name="property-card-review"]')
                opinion = opinion.text.split('\n')
                for i in opinion:
                    if re.match(r'^\d+\.\d+$', i):
                        opinion = float(i)
            except Exception as e:
                if i == 0 :
                    break

            time.sleep(0.1)
            nameSet.add(name)
            dictionary = {}
            dictionary['webName'] = "agoda"
            dictionary['name'] = name
            dictionary['currency'] = currency
            dictionary['price'] = price
            dictionary['img'] = img
            dictionary['url'] = url
            dictionary['opinion'] = opinion
            allData.append(dictionary)
            if (len(resp)+len(allData)) == max_number:
                return resp + allData
    return resp + allData

if __name__ == "__main__":
    start_time = time.time()
    sys.stdout.reconfigure(encoding='utf-8')
    sys.stdin.reconfigure(encoding='utf-8')
    sys.stderr.reconfigure(encoding='utf-8')

    file_path = sys.argv[1]

    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            file_content = file.read()
        input_data = json.loads(file_content)
        result = agodaFind(input_data)       
        print(json.dumps(result))
    except FileNotFoundError:
        print(f"Error: The file '{file_path}' was not found.")
    except json.JSONDecodeError:
        print(f"Error: Failed to decode JSON from the file '{file_path}'.")
    except Exception as e:
        print(f"Error: {e}")

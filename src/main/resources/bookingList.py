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

def bookingFind(inputData): 
    driver.get("https://www.booking.com/index.zh-tw.html?aid=2311236;label=zh-xt-tw-booking-desktop-EbyZWwQLwTW_IbH*0eHNCgS654267613595:pl:ta:p1:p2:ac:ap:neg:fi:tikwd-65526620:lp1012818:li:dec:dm;ws=&gad_source=1&gclid=CjwKCAiAmfq6BhAsEiwAX1jsZ_cjODVX18PabJdOk_jUBAsXEG0-JGMbFFdphMB-0brvLW50MfVqnhoChgQQAvD_BwE")  
    try:
        wait = WebDriverWait(driver, 10)
        close_button = wait.until(EC.element_to_be_clickable((By.XPATH, '//button[@aria-label="關閉登入的資訊。"]')))
        close_button.click() 
    except TimeoutException:
        pass

    try:
       input_box = WebDriverWait(driver, 1).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, 'input[name="ss"].eb46370fe1'))
        )
        input_box.clear()
        input_box.send_keys(inputData["area"])
        time.sleep(1)
        input_box.send_keys(Keys.ENTER)
        url = driver.current_url
        returnUrl = url[:url.find("group_adults")] + "checkin=" + inputData["checkinDate"] + "&checkout=" + inputData["checkoutDate"] + "&group_adults=" + str(inputData["adults"]) + "&no_rooms=" + str(inputData["rooms"]) + "&group_children=" + str(inputData["children"])
        for i in range(len(inputData["children"])):
            returnUrl = returnUrl + "&age=10"
        driver.get(returnUrl)
        input_box = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'input[name="ss"].eb46370fe1')))

        value = driver.find_element(By.CSS_SELECTOR, 'input[name="ss"].eb46370fe1').get_attribute('value')
        
        while value.find(inputData["area"]) ==-1:
			print(" F ")
            input_box = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'input[name="ss"].eb46370fe1')))
            print(" A ")
            for i in range(len(input_box.get_attribute('value'))):
                input_box.send_keys(Keys.BACKSPACE)
            print(" B ")
            input_box.send_keys(inputData["area"])
            print(" C ")
            button = WebDriverWait(driver, 10).until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'button.a83ed08757.c21c56c305.a4c1805887.f671049264.a2abacf76b.c082d89982.cceeb8986b.b9fd3c6b3c')))
            print(" D ")
            button.click()
            print(" E ")
    except TimeoutException:
        pass

    nameSet = set()
    allData = []
    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, 'div[data-testid="property-card"]')))
    last_height = 0
    condition = True
    if "number" in inputData:
        condition = len(driver.find_elements(By.CSS_SELECTOR, 'div[data-testid="property-card"]')) < int(inputData["number"])

    while condition:
        time.sleep(1)
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight-1000);")
        new_height = driver.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            break
        last_height = new_height

    while condition:
        try:
            button = WebDriverWait(driver, 2).until(
                EC.element_to_be_clickable((By.CSS_SELECTOR, 'button[type="button"].a83ed08757.c21c56c305.bf0537ecb5.f671049264.af7297d90d.c0e0affd09'))
            )
            ActionChains(driver).move_to_element(button).perform()
            button.click()
        except Exception:
            break

    card = driver.find_elements(By.CSS_SELECTOR, 'div[data-testid="property-card"]')
    returnSize = 0
    if "number" in inputData:
        returnSize = min(len(card), int(inputData["number"]))
    else:
        returnSize = len(card)

    i = 0
    for i in range(returnSize):
        
        try:
            card = driver.find_elements(By.CSS_SELECTOR, 'div[data-testid="property-card"]')
            WebDriverWait(driver, 10,0.5).until(EC.presence_of_element_located((By.CSS_SELECTOR, '[data-testid="title"]')))
            name = card[i].find_element(By.CSS_SELECTOR, '[data-testid="title"]')
            price = card[i].find_elements(By.CSS_SELECTOR, '.f6431b446c.fbfd7c1165.e84eb96b1f')
            # 確保元素未過期

            if name and price and name.text not in nameSet:
                url = card[i].find_element(By.CSS_SELECTOR, 'a[data-testid="title-link"]').get_attribute("href")
                try:
                    opinion = float(card[i].find_element(By.CSS_SELECTOR, 'div.a3b8729ab1.d86cee9b25').text[-3:])
                except NoSuchElementException:
                    opinion = 0.0
               
                img = card[i].find_element(By.CSS_SELECTOR, "img[data-testid='image']").get_attribute("src")

                dictionary = {}
                dictionary['webName'] = "booking"
                dictionary['name'] = name.text
                match = re.match(r"([A-Z]+)\s([\d,]+)", price[0].text)
                dictionary['currency'] = match.group(1)
                dictionary['price'] = int(match.group(2).replace(",", ""))
                dictionary['url'] = url
                dictionary['opinion'] = opinion

                dictionary['img'] = img
                nameSet.add(name.text)
                allData.append(dictionary)
        except StaleElementReferenceException:
            try:
                card = driver.find_elements(By.CSS_SELECTOR, 'div[data-testid="property-card"]')
                WebDriverWait(driver, 10,0.5).until(EC.presence_of_element_located((By.CSS_SELECTOR, '[data-testid="title"]')))
                name = card[i].find_element(By.CSS_SELECTOR, '[data-testid="title"]')
                price = card[i].find_elements(By.CSS_SELECTOR, '.f6431b446c.fbfd7c1165.e84eb96b1f')
                # 確保元素未過期

                if name and price and name.text not in nameSet:
                    url = card[i].find_element(By.CSS_SELECTOR, 'a[data-testid="title-link"]').get_attribute("href")
                    try:
                        opinion = float(card[i].find_element(By.CSS_SELECTOR, 'div.a3b8729ab1.d86cee9b25').text[-3:])
                    except NoSuchElementException:
                        opinion = 0.0
                    
                    img = card[i].find_element(By.CSS_SELECTOR, "img[data-testid='image']").get_attribute("src")

                    dictionary = {}
                    dictionary['webName'] = "booking"
                    dictionary['name'] = name.text
                    match = re.match(r"([A-Z]+)\s([\d,]+)", price[0].text)
                    dictionary['currency'] = match.group(1)
                    dictionary['price'] = int(match.group(2).replace(",", ""))
                    dictionary['url'] = url
                    dictionary['opinion'] = opinion

                    dictionary['img'] = img
                    nameSet.add(name.text)
                    allData.append(dictionary)
            except StaleElementReferenceException:
                continue
    return allData

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
        result = bookingFind(input_data)
        
        print(json.dumps(result))
    except FileNotFoundError:
        print(f"Error: The file '{file_path}' was not found.")
    except json.JSONDecodeError:
        print(f"Error: Failed to decode JSON from the file '{file_path}'.")
    except Exception as e:
        print(f"Error: {e}")

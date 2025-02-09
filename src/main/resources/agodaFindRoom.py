import json
import re
from selenium.webdriver.chrome.options import Options 

import sys
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.action_chains import ActionChains
from webdriver_manager.chrome import ChromeDriverManager


options = Options()
options.add_argument("--headless")
options.add_argument("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
options.add_argument("--disable-gpu")
options.add_argument("--window-size=1920,1080")
options.add_argument("--disable-blink-features=AutomationControlled")
options.add_experimental_option("excludeSwitches", ["enable-automation"])
options.add_experimental_option("useAutomationExtension", False)

driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)


def findRoomData(inputData):
    driver.get(inputData["url"])
    WebDriverWait(driver, 10).until(EC.visibility_of_element_located((By.CSS_SELECTOR,  '[data-selenium="MasterRoom"]')))
    card = driver.find_elements(By.CSS_SELECTOR, '[data-selenium="MasterRoom"]')
    allData =[]
    for room in card:
        driver.execute_script("arguments[0].scrollIntoView();", room)
        room_type = room.find_element(By.CSS_SELECTOR, '[data-selenium="masterroom-title-name"]').text
        img = room.find_element(By.CSS_SELECTOR, '[data-selenium="MasterRoom-infoPhoto-image"]').get_attribute("src")
        bed_type_temp = room.find_elements(By.CSS_SELECTOR, '[data-testid="drone-box"]')
        for i in range (len(bed_type_temp)):
            if (bed_type_temp[i].text.find("床") != -1):
                bed_type = bed_type_temp[i].text
                bed_type=bed_type.split("&")
                break
        try:
            driver.find_element(By.CSS_SELECTOR, "[data-selenium='MasterRoom-showMoreLessButton']").click()
        except Exception as e:
            pass
        room_detail = room.find_elements(By.CSS_SELECTOR, '[data-selenium="ChildRoomsList-room"]')
        for detail in room_detail:

            if detail.find_elements(By.CSS_SELECTOR, 'span.Capacity-iconGroup.Capacity-iconGroup--red') != []:
                continue
            people =  detail.find_element(By.CSS_SELECTOR, 'span.Capacity-iconGroup').find_elements(By.CSS_SELECTOR, '[data-ppapi="occupancyIcon"]')
            number = len(people)
            if number == 0:
                number = int(detail.find_element(By.CSS_SELECTOR, 'span.Capacity-iconGroup.Capacity-iconGroup--num').find_element(By.CSS_SELECTOR, '[data-ppapi="occupancy"]').text)
            currency = detail.find_element(By.CSS_SELECTOR, '[data-ppapi="room-price-currency"]').text
            price = detail.find_element(By.CSS_SELECTOR, '[data-ppapi="room-price"]').text
            price = int(price.replace(",",""))
            notificationList = detail.find_elements(By.CSS_SELECTOR, '[data-testid="text"]')
            notification = []
            for i in notificationList:
                notification.append(i.text)
            dictionary={}
            dictionary['roomType'] = room_type
            dictionary['bedType'] = bed_type
            dictionary['img']=img
            dictionary['currency']=currency
            dictionary['price']=price
            dictionary['hightFloor'] = False
            dictionary['maxMemberAdults'] = number
            dictionary['maxMemberChildren'] = 0
            dictionary['notificationList'] = notification
            allData.append(dictionary)
    return allData
if __name__ == "__main__":
    start_time = time.time()
    sys.stdout.reconfigure(encoding='utf-8')
    sys.stdin.reconfigure(encoding='utf-8')
    sys.stderr.reconfigure(encoding='utf-8')
    
    file_path =sys.argv[1]

    try:
        with open(file_path, 'r',encoding='utf-8') as file:
            file_content = file.read()
        input_data = json.loads(file_content)
        result = findRoomData(input_data)
        
        print(json.dumps(result, ensure_ascii=False))

    except FileNotFoundError:
        print(f"Error: The file '{file_path}' was not found.")
    except json.JSONDecodeError:
        print(f"Error: Failed to decode JSON from the file '{file_path}'.")
    except Exception as e:
        print(f"Error: {e}")


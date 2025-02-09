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

    allData =[]

    # 抓取目標元素

    card = driver.find_elements(By.CSS_SELECTOR, "tr.js-rt-block-row")
    WebDriverWait(driver, 10).until(EC.visibility_of_element_located((By.CSS_SELECTOR, "tr.js-rt-block-row")))

    imgDict=matchImg(inputData)
    for i in range (0,len(card)):
        room_type = card[i].find_elements(By.CSS_SELECTOR, 'span.hprt-roomtype-icon-link')
        bed_type = card[i].find_elements(By.CSS_SELECTOR, "li.art-bed-type spn")
        
        if len(bed_type) ==0 :
            bed_type = card[i].find_elements(By.CSS_SELECTOR, "li.bedroom_bed_type")
        if len(bed_type) ==0 :
            bed_type = card[i].find_elements(By.CSS_SELECTOR, "li.rt-bed-type")
        price = card[i].find_elements(By.CSS_SELECTOR, 'span.prco-valign-middle-helper')
        hight_floor = card[i].find_elements(By.CSS_SELECTOR, 'div.hprt-higher-floor')
        infant_bed = card[i].find_elements(By.CSS_SELECTOR, 'span.hprt-roomtype-crib-label')
        #早餐、剩餘房量
        notificationOne = card[i].find_elements(By.CSS_SELECTOR, 'div.bui-list__description')
        notificationTwo = card[i].find_elements(By.CSS_SELECTOR, "span.bui-text.bui-text--variant-small_1.bui-text--color-constructive")
        max_member = card[i].find_elements(By.CSS_SELECTOR, 'span.bui-u-sr-only')
        
        dictionary={}
        if room_type and bed_type:
            dictionary['roomType'] = room_type[0].text
            bedType = []
            if bed_type != []:
                for i in range (0,len(bed_type)):
                    bedType.append(bed_type[i].text)
            dictionary['bedType'] = bedType  

        elif len(allData) != 0:
            dictionary['roomType'] = allData[-1]['roomType']
            dictionary['bedType'] = allData[-1]['bedType']
            dictionary['bedType'] = allData[-1]['bedType']
        else:
            dictionary['roomType'] = room_type[0].text
            bedType = []
            if bed_type != []:
                for i in range (0,len(bed_type)):
                    bedType.append(bed_type[i].text)
            dictionary['bedType'] = bedType  

        if dictionary['roomType'] in imgDict:
            dictionary['img']=imgDict[dictionary['roomType']]
        else:
            dictionary['img'] = "https://i.imgur.com/Y1GLBdW.png"
        #-----------------------------------------
        match = re.match(r"([A-Z]+)\s([\d,]+)", price[0].text)
        dictionary['currency'] = match.group(1)
        dictionary['price'] =int(match.group(2).replace(",", ""))
        if hight_floor:
            dictionary['hightFloor'] = True
        else:
            dictionary['hightFloor'] = False
        if infant_bed:
            dictionary['infantBed'] = infant_bed[0].text
        
        notificationList=[]
        if notificationOne:
            for i in range (0,len(notificationOne)):
                notificationList.append(notificationOne[i].text)

        if notificationTwo:
                for i in range (0,len(notificationTwo)):
                    notificationList.append(notificationTwo[i].text)
        maxMenber = max_member[0].text[max_member[0].text.find(":")+1:]
        if (maxMenber.find("<br>") == -1):
            dictionary['maxMemberAdults'] = int(maxMenber[maxMenber.find(" ")+1:])
            dictionary['maxMemberChildren'] = 0
        else:
            if (maxMenber[2:4] == "成人"):
                adults = maxMenber[:maxMenber.find("<br>")-1]
                dictionary['maxMemberAdults'] = int(adults[adults.find(" ")+1:])
                children = maxMenber[maxMenber.find("<br>")+5:]
                dictionary['maxMemberChildren'] =int(children[children.find(" ")+1:])
            else:
                children = maxMenber[:maxMenber.find("<br>")-1]
                dictionary['maxMemberChildren'] = int(children[children.find(" ")+1:])
                adults = maxMenber[maxMenber.find("<br>")+5:]
                dictionary['maxMembeAadults'] =int(adults[adults.find(" ")+1:])

        dictionary['notificationList'] = notificationList
        allData.append(dictionary)
    
    return allData

def matchImg(inputData):
    imgs = driver.find_elements(By.CLASS_NAME, "hprt-roomtype-link")
    imgDict = {}
    for i in range(len(imgs)):
        try:
            imgs[i].click()
            while True:
                try:
                    img_link =  driver.find_element(By.CSS_SELECTOR, "a.js-hotel-thumb img").get_attribute("src")
                    break
                except Exception:
                    pass
            # 增加照片畫質
            img_link = img_link.replace("square60", "square600")
            while True:
                try:
                    room_name = driver.find_elements(By.CSS_SELECTOR, "h1#hp_rt_room_gallery_modal_room_name")[-1].text
                    if len(room_name) == 0:
                        continue
                    break
                except Exception:
                    pass

        except Exception:
            a=1
        finally:
            if img_link and room_name: 
                imgDict[room_name] = img_link
        try:
            img_close =  driver.find_element(By.XPATH, '//button[@class="modal-mask-closeBtn"]')
            img_close.click() 
        except Exception: 
            try:
                button = driver.find_element(By.CSS_SELECTOR, "button.a83ed08757.c21c56c305.f38b6daa18.d691166b09.ab98298258.f4552b6561")
                ActionChains(driver).move_to_element(button).click(button).perform()
            except Exception:
                a = 1
    return imgDict
if __name__ == "__main__":
    start_time = time.time()
    sys.stdout.reconfigure(encoding='utf-8')
    sys.stdin.reconfigure(encoding='utf-8')
    sys.stderr.reconfigure(encoding='utf-8')
    # True 測試 
    flag = False
    # 取得檔案路徑
    if (flag):
        file_path ="findRoom.txt"
    else:
        file_path =sys.argv[1]

    try:
        with open(file_path, 'r',encoding='utf-8') as file:
            file_content = file.read()
        input_data = json.loads(file_content)
        result = findRoomData(input_data)

        if (flag):
            print(time.time() - start_time)

            for i in range (0,len(result)):
                #房型 -> "roomType"
                #床型 -> "bedType"
                #幣別 -> "currency"
                #價格 -> "price" int
                #高樓層 -> "hightFloor" boolean
                #嬰兒床 -> "infantBed"
                #住房須知 -> "notificationList"
                #最大入住人數 -> "maxMember"
                #房型 -> "roomType"
                # print(f"第 {i} 條-> 房型: {result[i].get("roomType")} 床型: {result[i].get("bedType")} 價格: {result[i].get("price")} 最大成人入住人數: {result[i].get("maxMemberAdults")} 最大兒童入住人數: {result[i].get("maxMemberChildren")}")
                print(f"第 {i} 條-> 房型: {result[i].get("roomType")} 床型: {result[i].get("img")} ")

        else:
            print(json.dumps(result, ensure_ascii=False))

    except FileNotFoundError:
        print(f"Error: The file '{file_path}' was not found.")
    except json.JSONDecodeError:
        print(f"Error: Failed to decode JSON from the file '{file_path}'.")
    except Exception as e:
        print(f"Error: {e}")


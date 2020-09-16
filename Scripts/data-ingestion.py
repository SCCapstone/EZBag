import pandas as pd
import requests
data = pd.read_csv("../Data/products.csv")
print(data.head())
Barcode,Name,Quantity,Description,Type,Date

for index, row in data.iterrows():
    print(row['Barcode'], row['Name'])
    data = {"barcode":row}

for index in data.index:
    data["Barcode"][index]
    data["Name"][index]
    data[""]

json = {
    "ean":"test",
    "upc":"test",
    "barcode":"test",
    "name":"test",
    "price":0.99,
    "description":"test",
    "business-id":"test"
}

r = requests.post(url="http://localhost:8080/EZBagWebapp/webapi/product", data=json)
= r.text 
print(f"inserted: {r}") 



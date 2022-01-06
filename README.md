# themongo - spring boot application with mongodb

##REST Example
http://localhost:10091/bootmongo/catalog/documents/search?category=Mobile+Phones,Tablets&brand=Huawei,apple&pageNo=1&pageSize=50&sortBy=price.amount+DESC

##MongoDB Compass Example 
{"category": {"$in": ["Mobile Phones", "Tablets"]}, "brand": {"$in": ["Huawei", "apple"]}}, "sort": {"price.amount": 1}


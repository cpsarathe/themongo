# themongo - spring boot application with mongodb

### We use Spring Mongo Data Repository , Mongo Document to work with Mongo Database

## REST Example
http://localhost:10091/bootmongo/catalog/documents/search?category=Mobile+Phones,Tablets&brand=Huawei,apple&pageNo=1&pageSize=50&sortBy=price.amount+DESC

## MongoDB Compass Example 
{"category": {"$in": ["Mobile Phones", "Tablets"]}, "brand": {"$in": ["Huawei", "apple"]}}, "sort": {"price.amount": 1}

## bigdecimal range query
http://localhost:10091/bootmongo/catalog/documents/search?price=121.00,126.00&pageSize=10

    //by default between results are excluding given criteria i.e  gt>amount1 , lt<amount2
    @Query("{'price.amount':{$gte:?0,$lte:?1}}")
    Page<CatalogModel> findByPrice_AmountBetween(BigDecimal amount1, BigDecimal amount2, Pageable pageable);

# date range query
http://localhost:10091/bootmongo/catalog/documents/search?createdDate=2021-12-21,2021-12-27&pageSize=10
Page<CatalogModel>findByCreatedDateBetween(LocalDateTime fromDate,LocalDateTime toDate,Pageable pageable);


# Requirements to run this application
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven](https://maven.apache.org/)
- [Mock API](https://cdn-dev.preoday.com/test/mock-api.zip)

# How to run
## 1) Execute mock-api
[Download the mock-api](https://cdn-dev.preoday.com/test/mock-api.zip) and extract its content.
After extraction, execute the file 'start.sh', inside the folder 'wiremock'.

*Make sure that port 8081 is available.

## 2) Generating a .jar file
Inside 'pos' folder, run:
```
mvn clean install
```

## 3) Run this application
Inside 'pos' folder, run:
```
java -jar .\target\supermarket-pos.jar
```

*Make sure that port 8080 is available.

## Endpoints
### Basket
To create a new Basket:
- POST http://localhost:8080/basket

To add a new Item to a Basket
- POST http://localhost:8080/basket/{basketId}/{productId}

To retrieve informations about a Basket
- GET http://localhost:8080/basket/{basketId}

To checkout a Basket
- POST http://localhost:8080/basket/{basketId}/checkout

### Products
To list all products
- GET http://localhost:8080/products

To get details about a specific product
- GET http://localhost:8080/product/{productId}


# How to run automated test
Inside the folder "pos", run this script:
```
mvn test
```

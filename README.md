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

# How to test
Inside the folder "pos", run this script:
```
mvn test
```

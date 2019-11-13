# bysykkel-stasjonsstatus
This project queries the [Oslo Bysykkel open API](https://oslobysykkel.no/apne-data/sanntid) and offers a REST-API to query the different bikestations and the corresponding number of current available locks and bikes.

Usage out-of-the-box:
```
java -jar target/bysykkel-stasjonsstatus-1.2-jar-with-dependencies.jar
```
Usage if you want to compile and run tests:
```
mvn clean test exec:java
```
WADL is then available at http://localhost:8080/bysykkel/application.wadl

## Development
### Prerequisites
```
Java version 9+
Maven version 3+
```

### Installing
To run the tests and build the project, use
```
mvn clean install 
```

## Changelog
### [1.2] - 2019-11-10
- RESTful services
### [1.1] - 2019-11-05
- supporting Java9+
### [1.0] - 2019-11-01
- initial release


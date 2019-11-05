# bysykkel-stasjonsstatus
This project queries the [Oslo Bysykkel open API](https://oslobysykkel.no/apne-data/sanntid) and prints a list of the different bikestations and the corresponding number of current available locks and bikes.

Usage:
```
java -jar target/bysykkel-stasjonsstatus-1.1-SNAPSHOT-jar-with-dependencies.jar 
```

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
### [1.1] - 2019-11-05
- supporting Java9+
### [1.0] - 2019-11-01
- initial release


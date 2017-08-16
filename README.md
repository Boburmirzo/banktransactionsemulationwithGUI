# Bank-transactions
REST API to process bank transactions using Spring Boot, with persistence
In memory with JPA and Hibernate using H2 database, transactions are processed
Asynchronously using a threadpool.


## Technical specifications
- Java 1.8
- Spring boot 1.4.1
- Maven 4.0
- H2 database 1.4
- JPA and Hibernate
- Log4j 2
- JUnit 4.12


## Setup:

- Clone repository.
Sh
$ Git clone https://github.com/Boburmirzo/banktransactionsemulation
(I.e.
- Build with maven.
Sh
$ Mvn clean package
(I.e.
- Run with the tomcat embedded spring boot.
Sh
$ Mvn spring-boot: run
(I.e.
- The application is available at: ** http: // localhost: 8080 **

## Description
The application has 5 preloaded accounts, and has the following endpoint available:

- ** GET: / api / account **, returns list of accounts in json format
- ** GET: / api / account / {id} **, returns the detail of an account in json format
- ** POST: / api / account **, receives an account json without ** 'id' ** (self-generated id)
- ** PUT: / api / account **, receives an account json to edit and returns the modified account in json format
- ** DELETE: / api / account / {id} **, receive the account id to remove

Account example in json format:

Json
  {
    "Id": 100000,
    "Bank": "Sberbank",
    "Country": "Russia",
    "Balance": 250000
  }
(I.e.
To load and query transactions, the following endpoints are available:

- ** GET: / api / transaction **, returns list of successful transactions in json format
- ** GET: / api / transaction / {id} **, returns the detail of a transaction in json format

Example of transaction processed:

Json
{
  "Id": 1,
  "Source": 100000,
  "Target": 100004,
  "Amount": 222,
  "Type": "INTERNATIONAL",
  "Tax": 11.100000000000001,
  "Status": "SUCCESS"
}
(I.e.

- ** POST: / api / transaction **, receives a json from a transaction that includes:
** id of the source account, id of the target account and amount to be transferred ** Example:

Json
  {
    "Source": "100000",
    "Target": "100004",
    "Amount": 222.5
  }
(I.e.

In the project root, a log of the transactions is generated in the project root in the path: ** / logs / transactions.txt **

In the file ** application.properties ** the following properties are configured:

Sh
# Thread pool configurations for processing transactions
Threadpool.corepoolsize = 10
Threadpool.maxpoolsize = 100

# This property does all asynchronous logging
Log4jContextSelector = org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
(I.e.
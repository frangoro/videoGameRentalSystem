# Video Game Rental System
Api rest Spring Boot

## Design and Development
1. Incremental development method. Complete one functionality every iteration.
2. Development patterns: MVC, DAO.

## Next tasks
1. Testing
2. Exception Handling
3. Continous Integration
4. Quality processes
5. Secure (SSL)
6. Autenthication
7. Increase logging
8. Documentantion (code and api)
9. Validations
10. Increase functionality (user administration, relate loyality and prices, etc)

## Libraries
Jackson JSON library to automatically marshal DTOs intances with Spring.
Spring MVC
Spring Boot
Spring Test, AssertJ, MockitoBDD

## Technologies
Java 8
Hibernate 5
JPA 2
Tomcat 8
Maven 3
Spring 4 
MySql 5

## How to build and run

mvn clean spring-boot:run

## How to configure

application.properties file for database configuration

## How to use

http://localhost:8080/rental/rent?gameId=1,2,3,4&days=1,5,2,7&userId=1

[
  {
    "price": 4,
    "gameName": "No Man's Sky",
    "days": 1
  },
  {
    "price": 9,
    "gameName": "Resident Evil 6",
    "days": 5
  },
  {
    "price": 3,
    "gameName": "Fallout 5",
    "days": 2
  },
  {
    "price": 9,
    "gameName": "Fallout 3",
    "days": 7
  }
]

http://localhost:8080/rental/return?gameId=1,2,3,4&userId=1

{
  "totalLateCharge": 11,
  "invoiceItems": [
    {
      "surcharge": 8,
      "gameName": "No Man's Sky",
      "extraDays": 2
    },
    {
      "surcharge": 3,
      "gameName": "Resident Evil 6",
      "extraDays": 1
    }
  ]
}

## How to run test

mvn test


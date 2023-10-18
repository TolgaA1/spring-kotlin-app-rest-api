# Kotlin/Spring Boot App For REST api

## A fully functional REST api that comes with authentication, custom API error responses, standard REST CRUD and an optimised Docker image

The REST api was built using Spring Boot with Kotlin. This app supports:
* In-memory user-based auth that comes with two roles: guest and admin
* JPA/Hibernate with in-memory H2
* Standard REST CRUD for the entity "person". Only Admin can access these operations.=
* Extra endpoint that returns a list of users that can be filtered by name and/or age. Username and password is not shown in this endpoint using DTO. Both guest and admin users are able to access this
* Custom API error messages for readability
* Comes with an optimised docker image of size 323MB

## How to install
1. Clone the project into your preferred IDE
2. Build the project
3. Run from main application "SpringKotlinAppApplication"


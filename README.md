# UserContacts - Backend

# Table of contents 
* [About the project](#about-the-project)
* [Current state of the project](#current-state-of-the-project)
* [Plans for the future](#plans-for-the-future)
* [Technologies used](#technologies-used)
* [How to run it?](#how-to-run-it)
* [How to use this API?](#how-to-use-this-api)
    * [Registration and login](#registration-and-login)
    * [Expense](#expense)

# About the project
UserContacts is a web application that allows people to store their contacts. 

This is only backend part of the project, frontend can be found there. Work is still in progress. This is also a college related project for one of the courses.

# Current state of the project
Currently project has implemented REST API with all the endpoints for data access user related. Project is also connected to H2 database with PostgreSQL ready to be connected in the future. 

Users database is integrated with spring security context. It is possible to login to API with JWT, extend JWT duration and register. JWT is not yet fully finished. Only logged in users can read the API, and also they need to be authorized to see data. Plans for the future are specified below.

Login, registration data validation and email verification after creating account are added. 

# Plans for the future
* Redis JWT Secret storage for each user
* Logout from all accounts
* Password change
* Admin API

# Technologies used
* Java 11
* Spring Boot 2.4.5
* H2 Database 
* Spring Data JPA
* Spring Security
* Lombok
* JJWT 0.9.1

# How to run it?

 Firstly you have to download the repository

```cmd
git clone https://github.com/Piotr0W0/user-contacts.git
 ```

You can run the application in the terminal. To do it you need [maven](https://maven.apache.org/install.html).

In order run run the application go to project directory with the project using
```cmd
cd github-user-contacts
```
Then you can run the app using 

```
mvn spring-boot:run
```
# How to use this API?

* Application by default uses port 8080, to change it edit ``application.properties`` by adding line ``server.port=n`` where n is chosen port. 
* Values of the json properties in documentation are types that have to be provided to receive 2xx response status. 
* Application requires JWT token in order to access resources


## Registration and login
### Data Transfer Objects
#### User
```
{
    "name": string,
    "password": string
}
```
* ``name`` - name of the user, it has to be an email address
* ``password`` - password of the user

#### Jwt 
```
{
    "token": string
}
```
* ``token`` - JWT token, valid for 24 hours

### Methods
#### Register - POST method
In order to register in the oszczedzator3000 API you have to go to the endpoint
```
/api/register
```
This endpoint requires [User](#user) as request body. If the user was successfully register the application returns 201 HTTP status code, otherwise it returns 409 code. It sends an confirmation mail to the given email address. 

#### Login - POST method
To obtain JWT token user has to login to API. To login you have to go to the endpoint
```
/api/login
```
This endpoint requires [User](#user) as request body. If login was successful it returns [Jwt](#jwt) as a response.

#### Extend token duration - POST method
The token is not infinite. At some point in order to not logout user from a website that uses API the token has to be extended. It can be done by sending a request to the endpoint
```
/api/token-extension
```
This endpoint returns [Jwt](#jwt) as the response only when the authorization token is valid or not expired.


## Contact
Contacts are accessible only for the users that owns them. Users can get, post, put or delete their contacts. User can get all of his/her contacts or filter them.

### Data Transfer Objects
#### Request with contact data
```
{
    "name": string,
    "phoneNumber": string
}
```
* ``name`` - name of the searched contact
* ``phoneNumber`` - phone number of the searched contact

#### Contact Response
```
{
    "contactId": long,
    "name": string,
    "phoneNumber": string
}
```
All of properties are the same as in [Request with contact data](#request-with-contact-data) with one addition

``contactId`` - unique id of an contact

### Methods

All requests but POST and DELETE return one or more [Contact contact](#contact-response).

#### GET all contacts
```
/api/contacts
```

There are also optional parameters
* ``page`` - id of page, starting from 0, default is 0
* ``size`` - size of page, 10 by default

#### GET filtered contacts
```
/api/contacts/filtered
```
This request has the same optional parameters as unfiltered one.

#### POST contact
```
/api/contacts
```
This request accepts [Request with contact data](#request-with-contact-data) as request body. Contact can be posted only if every property is filled. 

#### PUT contact
```
/api/contacts/{contactId}
```
* ``contactId`` - unique id of an contact
This request accepts [Request with contact data](#request-with-contact-data) as request body. Fill only properties that you want to have updated. User can update only his/her contacts. 

#### DELETE contact
```
/api/contacts/{contactId}
```
* ``contactId`` - unique id of an contact
User can delete only his/her contacts. 

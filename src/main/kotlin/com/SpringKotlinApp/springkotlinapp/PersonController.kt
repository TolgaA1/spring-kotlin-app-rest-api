package com.SpringKotlinApp.springkotlinapp

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

//creating the endpoints for the API
@RestController
class PersonController(private val personService: PersonService) {

    //CRUD endpoints
    @GetMapping("/people")
    fun getAllPeople(pageable: Pageable): Page<Person> = personService.getAllPeople(pageable)

    @GetMapping("/people/{id}")
    fun getPeopleByUsername(@PathVariable("id") personID: String): Person =
        personService.getPeopleByID(personID)

    @PostMapping("/people")
    fun createPerson(@RequestBody payload: Person): Person = personService.createPerson(payload)

    @PutMapping("/people/{id}")
    fun updatePersonByID(@PathVariable("id") id: String, @RequestBody payload: Person): Person? =
        personService.updatePersonByID(id, payload)

    @Transactional
    @DeleteMapping("/people/{username}")
    fun deletePersonByUsername(@PathVariable("username") username: String): Unit =
        personService.deletePersonsByUsername(username)

    //Extra endpoint that returns a list of users that can be filtered by name (also partial)
    // and/or age (admin and guest can access). Username and password should not be returned on this endpoint.
    @GetMapping("/users")
    fun getUsersNameAgeFilter(@RequestParam(required = false, name = "name") firstName: String?, @RequestParam(required = false, name = "age") age: String?): List<PersonDTO>{
        return personService.getAllPeopleByFirstnameAndAge(firstName,age)
    }

}
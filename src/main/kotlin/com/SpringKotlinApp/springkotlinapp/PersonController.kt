package com.SpringKotlinApp.springkotlinapp

import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*

//creating the endpoints for the API
@RestController
class PersonController(private val personService: PersonService) {

    //CRUD endpoints
    @GetMapping("/people")
    fun getAllPeople(): List<Person> = personService.getAllPeople()

    @GetMapping("/people/{username}")
    fun getPeopleByUsername(@PathVariable("username") username: String): Person =
        personService.getPeopleByUsername(username)

    @PostMapping("/people")
    fun createPerson(@RequestBody payload: Person): Person = personService.createPerson(payload)

    @PutMapping("/people/{username}")
    fun updatePersonByUsername(@PathVariable("username") username: String, @RequestBody payload: Person): Person? =
        personService.updatePersonByUsername(username, payload)

    @Transactional
    @DeleteMapping("/people/{username}")
    fun deletePersonByUsername(@PathVariable("username") username: String): Unit =
        personService.deletePersonsByUsername(username)

    //Extra endpoint that returns a list of users that can be filtered by name (also partial)
    // and/or age (admin and guest can access). Username and password should not be returned on this endpoint.

}
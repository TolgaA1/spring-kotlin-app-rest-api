package com.SpringKotlinApp.springkotlinapp.controller

import com.SpringKotlinApp.springkotlinapp.entity.Person
import com.SpringKotlinApp.springkotlinapp.dto.PersonDTO
import com.SpringKotlinApp.springkotlinapp.service.PersonService
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

//creating the endpoints for the API
@RestController
class PersonController(private val personService: PersonService) {

    //CRUD endpoints, only users with the admin role can access these.
    @GetMapping("/people")
    fun getAllPeople(pageable: Pageable): Page<Person> = personService.getAllPeople(pageable)

    @GetMapping("/people/{id}")
    fun getPeopleByID(@PathVariable("id") personID: String): Person =
        personService.getPeopleByID(personID)

    @PostMapping("/people")
    fun createPerson(@RequestBody payload: Person): Person = personService.createPerson(payload)

    @PutMapping("/people/{id}")
    fun updatePersonByID(@PathVariable("id") id: String, @RequestBody payload: Person): Person? =
        personService.updatePersonByID(id, payload)

    @Transactional
    @DeleteMapping("/people/{username}")
    fun deletePersonByID(@PathVariable("username") id: String): Unit =
        personService.deletePersonsByID(id)

    /**
     * Extra endpoint that returns a list of users that can be filtered by name (also partial) and/or age
     * Both admin and guest users can access this endpoint
     * DTO is used to hide away the username and password
     * Request parameters are used so the end point is flexible
     */
    @GetMapping("/users")
    fun getUsersNameAgeFilter(@RequestParam(required = false, name = "name") firstName: String?, @RequestParam(required = false, name = "age") age: String?): List<PersonDTO>{
        return personService.getAllPeopleByFirstnameAndAge(firstName,age)
    }

}
package com.SpringKotlinApp.springkotlinapp

import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getAllPeople(): List<Person> = personRepository.findAll();

    fun getPeopleByUsername(username: String): Person = personRepository.findByUsername(username)

    fun createPerson(person:Person) = personRepository.save(person)


    fun updatePersonByUsername(personUsername: String, person: Person): Person? {
         if (personRepository.existsByUsername(personUsername)) {
            return personRepository.save(
                Person(
                    name = person.name,
                    surname = person.surname,
                    email = person.email,
                    phone = person.phone,
                    dateOfBirth = person.dateOfBirth,
                    age = person.age,
                    username = person.username,
                    password = person.password
                )
            )
        }
         else
         {
             println("not found")
             return null
         }

    }

    fun deletePersonsByUsername(username: String): Unit {
        if (personRepository.existsByUsername(username)) {
            personRepository.deleteByUsername(username)
        }
    }

}
package com.SpringKotlinApp.springkotlinapp

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.IllegalFormatCodePointException
import java.util.IllegalFormatException


@Service
class PersonService(private val personRepository: PersonRepository) {

    fun mapPersonToPersonDTO(personList: List<Person>): List<PersonDTO>
    {
        val personsDTO:List<PersonDTO> = personList.map{
                person ->
            PersonDTO(person.name,
                person.surname,person.email,
                person.phone,
                person.dateOfBirth,
                person.age)
        }
        return personsDTO
    }
    fun getAllPeople(pageable: Pageable): Page<Person> = personRepository.findAll(pageable)

    fun getAllPeopleByFirstnameAndAge(personName:String?, age: Int?):List<PersonDTO>{
        val persons: List<Person> = personRepository.findAllByNameContainingAndAge(personName?.lowercase(),age)
        if(personName != null && age != null)
        {
            val persons: List<Person> = personRepository.findAllByNameContainingAndAge(personName.lowercase(),age)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName == null && age != null)
        {
            val persons: List<Person> = personRepository.findAllByAge(age)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName != null)
        {
            val persons: List<Person> = personRepository.findAllByNameContaining(personName.lowercase())
            return mapPersonToPersonDTO(persons)
        }
        //TEMP DO EXCEPTION
        return mapPersonToPersonDTO(persons)

    }

    fun getPeopleByUsername(username: String): Person = personRepository.findByUsername(username)

    fun createPerson(person:Person): Person {
        //making sure strings are in all lowercase except the unique username
        return personRepository.save(
            Person(
                name = person.name.lowercase(),
                surname = person.surname.lowercase(),
                email = person.email,
                phone = person.phone,
                dateOfBirth = person.dateOfBirth,
                age = person.age,
                username = person.username,
                password = person.password
            )
        )
    }


    fun updatePersonByUsername(personUsername: String, person: Person): Person? {
         if (personRepository.existsByUsername(personUsername)) {
            return personRepository.save(
                Person(
                    name = person.name.lowercase(),
                    surname = person.surname.lowercase(),
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
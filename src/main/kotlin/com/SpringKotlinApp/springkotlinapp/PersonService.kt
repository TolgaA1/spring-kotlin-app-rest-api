package com.SpringKotlinApp.springkotlinapp

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class PersonService(private val personRepository: PersonRepository) {

    fun getAllPeople(pageable: Pageable): Page<Person> = personRepository.findAll(pageable)

    fun getAllPeopleByFirstnameAndAge(personName:String?, age: Int?):List<Person>{
        if(personName != null && age != null)
        {
            return personRepository.findAllByNameContainingAndAge(personName.lowercase(),age)
        }
        else if(personName == null && age != null)
        {
            return personRepository.findAllByAge(age)
        }
        else if(personName != null)
        {
            return personRepository.findAllByNameContaining(personName.lowercase())
        }

        return personRepository.findAllByNameContainingAndAge(personName,age)
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
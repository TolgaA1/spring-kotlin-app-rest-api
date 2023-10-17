package com.SpringKotlinApp.springkotlinapp

import com.SpringKotlinApp.springkotlinapp.Exceptions.UsernameAlreadyExistsException
import com.SpringKotlinApp.springkotlinapp.Exceptions.UsernameNotFoundException
import com.SpringKotlinApp.springkotlinapp.Exceptions.WrongDataTypeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


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

    fun getAllPeopleByFirstnameAndAge(personName:String?, age: String?):List<PersonDTO>
    {
        var ageInt: Int? = age?.toIntOrNull()

        if(age != null)
        {
            if(ageInt == null)
            {
                throw WrongDataTypeException("ERROR: Invalid data type provided for age.")
            }
        }

        if(personName != null && age != null)
        {
            val persons: List<Person> = personRepository.findAllByNameContainingAndAge(personName.lowercase(),ageInt)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName == null && age != null)
        {
            val persons: List<Person> = personRepository.findAllByAge(ageInt)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName != null)
        {
            val persons: List<Person> = personRepository.findAllByNameContaining(personName.lowercase())
            return mapPersonToPersonDTO(persons)
        }

        val persons: List<Person> = personRepository.findAll()
        return mapPersonToPersonDTO(persons)

    }

    fun getPeopleByID(personID: String): Person
    {
        var idLong: Long? = personID.toLongOrNull()

        if(idLong == null)
        {
            throw WrongDataTypeException("ERROR: Invalid data type provided for ID")
        }

        return personRepository.getById(idLong)
    }

    fun createPerson(person:Person): Person {
        //making sure strings are in all lowercase except the unique username
        return if(!personRepository.existsByUsername(person.username))
        {
            personRepository.save(
                Person(
                    id = person.id,
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
        } else throw UsernameAlreadyExistsException("ERROR: This person already exists, please enter a new username.")
    }

    fun updatePersonByID(personID: String, person: Person): Person? {
        var idLong: Long? = personID.toLongOrNull()

        if(idLong == null)
        {
            throw WrongDataTypeException("ERROR: Invalid data type provided for ID")
        }

        if(person.username != personRepository.getById(idLong).username && personRepository.existsByUsername(person.username))
        {
            throw UsernameAlreadyExistsException("ERROR: Another user with this username exists. Please choose a different username.")
        }

        return if (personRepository.existsById(idLong)) {
            personRepository.save(
                Person(
                    id = idLong,
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
        } else throw UsernameNotFoundException("ERROR: No matching user was found.")

    }
    fun updatePersonByUsername(personUsername: String, person: Person): Person? {
        return if (personRepository.existsByUsername(personUsername)) {
            personRepository.save(
                Person(
                    id = getPeopleByID(personUsername).id,
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
        } else throw UsernameNotFoundException("ERROR: No matching user was found.")

    }

    fun deletePersonsByUsername(username: String): Unit {
        if (personRepository.existsByUsername(username)) {
            personRepository.deleteByUsername(username)
        } else throw UsernameNotFoundException("ERROR: No matching user was found.")
    }
}
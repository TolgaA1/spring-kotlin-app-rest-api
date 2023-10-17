package com.SpringKotlinApp.springkotlinapp

import com.SpringKotlinApp.springkotlinapp.Exceptions.UsernameAlreadyExistsException
import com.SpringKotlinApp.springkotlinapp.Exceptions.UsernameNotFoundException
import com.SpringKotlinApp.springkotlinapp.Exceptions.WrongDataTypeException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

//creating the service layer that provides all the business logic.
@Service
class PersonService(private val personRepository: PersonRepository) {

    //this method is responsible for mapping the person class to the person DTO
    //the DTO will hide the username and password data field
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

    //this method checks whether the given string can be a number
    fun isAValidNumber(personID:String?, exceptionMessage:String):Long
    {
        val idLong: Long? = personID?.toLongOrNull()

        if(idLong == null)
        {
            throw WrongDataTypeException(exceptionMessage)
        }
        return idLong
    }

    //finds every person and takes in a pageable to allow for pagination
    fun getAllPeople(pageable: Pageable): Page<Person> = personRepository.findAll(pageable)

    //returns a list of users that can be filtered by name and/or age
    fun getAllPeopleByFirstnameAndAge(personName:String?, age: String?):List<PersonDTO>
    {
        //checking if either personName or age are null, depending on which is null, execute the corresponding method
        if(personName != null && age != null)
        {
            //checks if age can be an integer, if it can't, then a number has not been entered and throw an exception
            val ageInt: Int = isAValidNumber(age,"ERROR: Invalid data type provided for age. Please enter a number.").toInt()
            val persons: List<Person> = personRepository.findAllByNameContainingAndAge(personName.lowercase(),ageInt)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName == null && age != null)
        {
            val ageInt: Int = isAValidNumber(age,"ERROR: Invalid data type provided for age. Please enter a number.").toInt()
            val persons: List<Person> = personRepository.findAllByAge(ageInt)
            return mapPersonToPersonDTO(persons)
        }
        else if(personName != null)
        {
            val persons: List<Person> = personRepository.findAllByNameContaining(personName.lowercase())
            return mapPersonToPersonDTO(persons)
        }
        //if age or name has not been specified, then just find all users
        val persons: List<Person> = personRepository.findAll()
        return mapPersonToPersonDTO(persons)

    }

    //Gets a person by searching for an ID
    fun getPeopleByID(personID: String): Person
    {
        val id = isAValidNumber(personID,"ERROR: Invalid data type provided for ID")
        return if(personRepository.existsById(id))
        {
            personRepository.getById(id)
        }
        else throw UsernameNotFoundException("ERROR: This person does not exist in the database.")
    }

    fun createPerson(person:Person): Person
    {
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

    //this method creates a person that is found with the given ID
    fun updatePersonByID(personID: String, person: Person): Person?
    {
        val id = isAValidNumber(personID,"ERROR: Invalid data type provided for ID")
        if(personRepository.existsById(id))
        {
            if(person.username != personRepository.getById(id).username && personRepository.existsByUsername(person.username))
            {
                throw UsernameAlreadyExistsException("ERROR: Another user with this username exists. Please choose a different username.")
            }
            else
            {
                return personRepository.save(
                    Person(
                        id = id,
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
        } else throw UsernameNotFoundException("ERROR: No matching user was found.")
    }

    fun deletePersonsByID(personID: String)
    {
        val id = isAValidNumber(personID,"ERROR: Invalid data type provided for ID")
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id)
        } else throw UsernameNotFoundException("ERROR: No matching user was found.")
    }
}
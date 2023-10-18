package com.SpringKotlinApp.springkotlinapp.repository

import com.SpringKotlinApp.springkotlinapp.entity.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//This repository interfaces for person will be used to interact with the database.
@Repository
interface PersonRepository: JpaRepository<Person, Long> {

    fun existsByUsername(username: String): Boolean
    fun findAllByNameContainingAndAge(name: String?, age: Int?): List<Person>
    fun findAllByNameContaining(name: String): List<Person>
    fun findAllByAge(age: Int?): List<Person>

}
package com.SpringKotlinApp.springkotlinapp

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long>{

    fun findByUsername(username:String): Person
    fun existsByUsername(username: String): Boolean
    fun deleteByUsername(username: String): Unit


    fun findAllByNameContainingAndAge(name: String?, age: Int?): List<Person>
    fun findAllByNameContaining(name: String): List<Person>
    fun findAllByAge(age: Int?): List<Person>

}

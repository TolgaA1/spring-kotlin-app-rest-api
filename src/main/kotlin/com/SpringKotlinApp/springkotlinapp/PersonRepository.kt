package com.SpringKotlinApp.springkotlinapp

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long>{

    fun findByUsername(username:String): Person
    fun existsByUsername(username: String): Boolean
    fun deleteByUsername(username: String): Unit

    @Query("select * from person where name like %?1%", nativeQuery = true)
    fun search(s: String): List<Person>
}

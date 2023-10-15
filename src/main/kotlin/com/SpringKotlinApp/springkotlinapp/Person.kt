package com.SpringKotlinApp.springkotlinapp

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "person")
//defining the person entity and it's properties
data class Person(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "surname", nullable = false)
    val surname: String?,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "phone", nullable = false)
    val phone: String,

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: String,

    @Column(name = "age", nullable = false)
    val age: Int,

    @Id @Column(name = "user_name", unique = true, nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String
)


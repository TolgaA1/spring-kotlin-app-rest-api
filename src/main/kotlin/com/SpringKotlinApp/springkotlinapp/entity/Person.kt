package com.SpringKotlinApp.springkotlinapp.entity

import jakarta.persistence.*

//This is defining the person entity.
@Entity
@Table(name = "person")
data class Person(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "surname", nullable = false)
    val surname: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "phone", nullable = false)
    val phone: String,

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: String,

    @Column(name = "age", nullable = false)
    val age: Int,

    @Column(name = "user_name", unique = true, nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String
)
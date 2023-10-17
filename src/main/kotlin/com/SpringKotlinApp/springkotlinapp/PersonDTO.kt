package com.SpringKotlinApp.springkotlinapp

import jakarta.persistence.Column
import jakarta.persistence.Id

//This is a Data Transfer Object that is used to hide the username and password from unauthorized users.
class PersonDTO (

    val name: String,

    val surname: String,

    val email: String,

    val phone: String,

    val dateOfBirth: String,

    val age: Int,

)
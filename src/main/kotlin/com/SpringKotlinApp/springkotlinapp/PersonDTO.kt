package com.SpringKotlinApp.springkotlinapp

import jakarta.persistence.Column
import jakarta.persistence.Id

class PersonDTO (

    val name: String,

    val surname: String,

    val email: String,

    val phone: String,

    val dateOfBirth: String,

    val age: Int,

)
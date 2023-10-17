package com.SpringKotlinApp.springkotlinapp.Model

/**
 * Template for error messages
 * @param status is the error status
 * @param message is the custom message of the error
 */
class ErrorMessageModel(
    var status: Int? = null,
    var message: String? = null
)
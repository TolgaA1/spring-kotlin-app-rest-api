package com.SpringKotlinApp.springkotlinapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class SpringKotlinAppApplication{

	@GetMapping
	fun hi(): String {
		return "hello"
	}
}

fun main(args: Array<String>) {
	runApplication<SpringKotlinAppApplication>(*args)
}

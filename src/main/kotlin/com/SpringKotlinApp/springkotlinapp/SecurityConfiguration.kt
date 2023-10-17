package com.SpringKotlinApp.springkotlinapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {

    //this filter ensures security on endpoints. Certain endpoints require certain roles for the request to be accepted.
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke{
            http.csrf().disable()
            authorizeRequests {
                authorize("/people/**",hasRole("ADMIN"))
                authorize("/users/**",hasRole("GUEST"))
                authorize(anyRequest,permitAll)
            }
            formLogin { disable() }
            http.httpBasic()
            logout {  }
        }
        return http.build()
    }

    //this method builds different types of users and roles. There will be a guest and an admin role.
    @Bean
    fun userDetails(): UserDetailsService {
        val encoder:PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val guest = User.builder()
            .username("guest")
            .password(encoder.encode("guestpassword"))
            .roles("GUEST")
            .build()
        val admin = User.builder()
            .username("admin")
            .password(encoder.encode("adminpassword"))
            .roles("GUEST","ADMIN")
            .build()
        return InMemoryUserDetailsManager(guest, admin)
    }
}
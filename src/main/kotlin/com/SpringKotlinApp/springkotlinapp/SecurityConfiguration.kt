package com.SpringKotlinApp.springkotlinapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke{
            http.csrf().disable()
            authorizeRequests {
                authorize("/people/**",authenticated)
                authorize("/users/**",authenticated)
                authorize(anyRequest,permitAll)
            }
            formLogin { disable() }
            http.httpBasic()
            logout {  }
        }
        return http.build()
    }
}
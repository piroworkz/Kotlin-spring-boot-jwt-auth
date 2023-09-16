package com.davidluna.jwtauth.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.davidluna.jwtauth")
@SpringBootApplication
class JwtAuthApplication

fun main(args: Array<String>) {
    runApplication<JwtAuthApplication>(*args)
}

package com.davidluna.jwtauth.app

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan("com.davidluna.jwtauth")
@SpringBootApplication
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
class JwtAuthApplication

fun main(args: Array<String>) {
    runApplication<JwtAuthApplication>(*args)
}

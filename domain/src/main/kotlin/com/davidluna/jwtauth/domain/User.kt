package com.davidluna.jwtauth.domain

data class User(
    val username: String = "",
    val password: String = "",
    val salt: String = "",
    val role: String = Role.USER.value
)

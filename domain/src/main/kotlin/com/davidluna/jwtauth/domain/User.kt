package com.davidluna.jwtauth.domain

data class User(
    val id: String,
    val username: String,
    val password: String,
    val salt: String,
    val role: Role = Role.USER
)

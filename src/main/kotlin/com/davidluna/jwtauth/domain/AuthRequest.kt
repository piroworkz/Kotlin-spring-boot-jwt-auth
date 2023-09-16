package com.davidluna.jwtauth.domain

data class AuthRequest(
    val username: String,
    val password: String,
    val role: Role = Role.USER
)
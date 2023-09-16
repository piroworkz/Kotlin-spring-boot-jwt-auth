package com.davidluna.jwtauth.domain

data class SaltedHash(
    val salt: String,
    val hash: String
)
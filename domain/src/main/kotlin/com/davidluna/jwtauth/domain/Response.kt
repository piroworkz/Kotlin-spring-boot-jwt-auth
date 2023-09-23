package com.davidluna.jwtauth.domain

data class Response(
    val code: StatusCode,
    val message: String,
    val token: String = "",
    val body: String
)
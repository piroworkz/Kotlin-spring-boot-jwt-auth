package com.davidluna.jwtauth.domain

sealed class JwtError(
    code: Int,
    description: String
) : AppError(code, description) {

    data class ExpiredJwt(
        val statusCode: Int,
        val errorMessage: String = "Access Denied. Expired Jwt."
    ) : JwtError(statusCode, errorMessage)

    data class MalformedJwt(
        val statusCode: Int,
        val errorMessage: String = "Access Denied. Invalid Jwt."
    ) : JwtError(statusCode, errorMessage)

    data class JwtNotFound(
        val statusCode: Int,
        val errorMessage: String = "Access Denied. Jwt not found."
    ) : JwtError(statusCode, errorMessage)
}
package com.davidluna.jwtauth.domain

sealed class AppError(
    val code: Int,
    val description: String
) : Throwable() {

    data class UnknownError(
        val statusCode: Int,
        val statusDescription: String = "Unknown Error"
    ) : AppError(statusCode, statusDescription)

    data class IOError(
        val statusCode: Int,
        val errorMessage: String = "Could not write to database"
    ) : AppError(statusCode, errorMessage)

    data class UserNotFound(
        val statusCode: Int,
        val errorMessage: String = "Account does not exist, please register"
    ) : AppError(statusCode, errorMessage)

    data class AccountExists(
        val statusCode: Int,
        val errorMessage: String = "Account already exists, please Login."
    ) : AppError(statusCode, errorMessage)

    data class UnAuthorized(
        val statusCode: Int,
        val errorMessage: String = "Please check your password and try again."
    ) : AppError(statusCode, errorMessage)
}
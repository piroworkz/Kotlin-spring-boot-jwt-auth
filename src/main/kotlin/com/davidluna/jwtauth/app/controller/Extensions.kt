package com.davidluna.jwtauth.app.controller

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.*
import java.io.IOException
import java.net.SocketTimeoutException

fun User.getClaims(): Array<JWTClaim> {
    return arrayOf(
        JWTClaim(
            R.JWTConfig.CLAIM_USERNAME,
            username
        ),
        JWTClaim(
            R.JWTConfig.CLAIM_AUTHORITIES,
            role.name
        )
    )
}

fun <T> T.buildSuccessResponse(token: String = ""): Response<T> = Response(
    code = StatusCode(value = 200, description = "Success"),
    message = "Success",
    token = token,
    body = this
)

fun AppError.buildFailResponse(token: String = ""): Response<String> = Response(
    code = StatusCode(value = code, description = description),
    message = description,
    token = token,
    body = ""
)

suspend fun <T> tryCatchSuspended(action: suspend () -> T): Either<AppError, T> = try {
    action().right()
} catch (e: Exception) {
    println("<-- ${e.message} ${e.printStackTrace()}")
    e.toAppError().left()
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is AppError -> this
        is SocketTimeoutException -> AppError.IOError(statusCode = 400)
        is IOException -> AppError.IOError(statusCode = 500)
        is Exception -> AppError.UnknownError(500)
        else -> AppError.UnknownError(500)
    }
}
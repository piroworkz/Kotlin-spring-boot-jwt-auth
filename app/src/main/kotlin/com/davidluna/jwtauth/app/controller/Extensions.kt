package com.davidluna.jwtauth.app.controller

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.*
import com.davidluna.jwtauth.usecases.crypto.CryptoUseCases
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
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

fun throwCryptoException(): Response = AppError.CryptoError(400).buildFailResponse()

inline fun <reified T> CryptoUseCases.getRequest(request: Request): T? = decrypt<T>(request.body)

fun String.buildSuccessResponse(token: String = ""): Response = Response(
    code = StatusCode(value = 200, description = "Success"),
    message = "Success",
    token = token,
    body = this
)

fun AppError.buildFailResponse(token: String = ""): Response = Response(
    code = StatusCode(value = code, description = description),
    message = description,
    token = token,
    body = ""
)

suspend fun <T> tryCatchSuspended(action: suspend () -> T): Either<AppError, T> = try {
    action().right()
} catch (e: Exception) {
    e.printStackTrace()
    println("<-- tryCatchSuspended error: ${e.stackTrace}")
    e.toAppError().left()
}

fun <T> tryCatch(action: () -> T): Either<AppError, T> = try {
    action().right()
} catch (e: Exception) {
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

fun Throwable.toJwtError(): AppError {
    return when (this) {
        is UnsupportedJwtException -> JwtError.MalformedJwt(500)
        is MalformedJwtException -> JwtError.MalformedJwt(500)
        is SignatureException -> JwtError.MalformedJwt(500)
        is IllegalArgumentException -> JwtError.MalformedJwt(500)
        is ExpiredJwtException -> JwtError.ExpiredJwt(500)
        is Exception -> AppError.UnknownError(500)
        else -> AppError.UnknownError(500)
    }
}


fun Response.toJson(): String =
    ObjectMapper().writeValueAsString(this)
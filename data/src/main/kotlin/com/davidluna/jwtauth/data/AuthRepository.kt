package com.davidluna.jwtauth.data

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.davidluna.jwtauth.data.sources.AuthDataSource
import com.davidluna.jwtauth.data.sources.HashDataSource
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import org.springframework.stereotype.Component

@Component
class AuthRepository(
    private val auth: AuthDataSource,
    private val hash: HashDataSource
) {

    suspend fun loginUser(request: AuthRequest): Either<AppError, User> = either {
        validateLogin(request)
        findUser(request).bind()
    }

    suspend fun registerUser(request: AuthRequest): Either<AppError, Boolean> = either {
        println("<-- registerUser repository")
        if (userExists(request).bind()) raise(AppError.AccountExists(400))
        val saltedHash = hash.createSaltedHash(request.password)
        auth.saveUser(request, saltedHash).bind()
    }

    suspend fun findUser(authRequest: AuthRequest): Either<AppError, User> = either {
        auth.findUser(authRequest).bind() ?: raise(AppError.UserNotFound(400))
    }

    private suspend fun userExists(authRequest: AuthRequest): Either<AppError, Boolean> =
        auth.userExists(authRequest)

    private suspend fun Raise<AppError>.validateLogin(request: AuthRequest) {
        if (!userExists(request).bind()) raise(AppError.UserNotFound(400))
        val isPasswordValid = hash.validateHash(request.password, createSaltedHash(request.password))
        if (!isPasswordValid) raise(AppError.UnAuthorized(400))
    }

    private fun createSaltedHash(requestPass: String): SaltedHash =
        hash.createSaltedHash(requestPass)

}
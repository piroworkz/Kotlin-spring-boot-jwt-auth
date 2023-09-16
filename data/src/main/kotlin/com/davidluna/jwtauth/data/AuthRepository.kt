package com.davidluna.jwtauth.data

import arrow.core.Either
import arrow.core.Eval.Companion.raise
import arrow.core.computations.EitherEffect
import arrow.core.computations.either
import com.davidluna.jwtauth.data.sources.AuthDataSource
import com.davidluna.jwtauth.data.sources.HashDataSource
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import org.springframework.stereotype.Component

@Component
class AuthRepository(
    private val local: AuthDataSource,
    private val hash: HashDataSource
) {

    suspend fun loginUser(request: AuthRequest): Either<AppError, User> = either {
        validateLogin(request)
        findUser(request).bind()
    }

    suspend fun registerUser(request: AuthRequest): Either<AppError, Boolean> = either {
        if (userExists(request).bind()) raise(AppError.AccountExists(400))
        val saltedHash = hash.createSaltedHash(request.password)
        local.saveUser(request, saltedHash).bind()
    }

    suspend fun findUser(authRequest: AuthRequest): Either<AppError, User> = either {
        val user: User? = local.findUser(authRequest).bind()
        if (user == null) raise(AppError.UserNotFound(400))
        user!!
    }

    private suspend fun userExists(authRequest: AuthRequest): Either<AppError, Boolean> =
        local.userExists(authRequest)

    private suspend fun EitherEffect<AppError, *>.validateLogin(request: AuthRequest) {
        if (!userExists(request).bind()) raise(AppError.UserNotFound(400))
        val isPasswordValid = hash.validateHash(request.password, createSaltedHash(request.password))
        if (!isPasswordValid) raise(AppError.UnAuthorized(400))
    }

    private fun createSaltedHash(requestPass: String): SaltedHash =
        hash.createSaltedHash(requestPass)


}

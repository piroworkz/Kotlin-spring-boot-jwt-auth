package com.davidluna.jwtauth.data.sources

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User

interface AuthDataSource {
    suspend fun userExists(request: AuthRequest): Either<AppError, Boolean>
    suspend fun findUser(request: AuthRequest): Either<AppError, User?>
    suspend fun saveUser(authRequest: AuthRequest, saltedHash: SaltedHash): Either<AppError, Boolean>
}
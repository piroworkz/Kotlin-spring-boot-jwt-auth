package com.davidluna.jwtauth.app.framework.local.database.daos

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.User

interface UserDao {
    suspend fun findByUsername(username: String): Either<AppError, User?>
    suspend fun save(user: User): Either<AppError, Boolean>
    suspend fun userExists(username: String): Either<AppError, Boolean>
    suspend fun updateUser(user: User): Either<AppError, Boolean>
}


package com.davidluna.jwtauth.app.framework.local.sources

import arrow.core.Either
import com.davidluna.jwtauth.app.controller.tryCatchSuspended
import com.davidluna.jwtauth.app.framework.local.database.daos.UserDao
import com.davidluna.jwtauth.app.framework.local.utils.toDomain
import com.davidluna.jwtauth.app.framework.local.utils.toRemote
import com.davidluna.jwtauth.data.sources.AuthDataSource
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import org.springframework.stereotype.Component

@Component
class LocalAuthDataSource(
    private val dao: UserDao
) : AuthDataSource {
    override suspend fun userExists(request: AuthRequest): Either<AppError, Boolean> =
        tryCatchSuspended { dao.existsDBUserByUsername(request.username) }

    override suspend fun findUser(request: AuthRequest): Either<AppError, User?> =
        tryCatchSuspended { dao.findByUsername(request.username).toDomain() }

    override suspend fun saveUser(authRequest: AuthRequest, saltedHash: SaltedHash): Either<AppError, Boolean> =
        tryCatchSuspended {
            val request = authRequest.toRemote(saltedHash)
            dao.save(request).username == authRequest.username
        }
}
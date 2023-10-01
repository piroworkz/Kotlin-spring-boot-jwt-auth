package com.davidluna.jwtauth.app.framework.local.sources

import arrow.core.Either
import com.davidluna.jwtauth.app.framework.local.database.daos.UserDao
import com.davidluna.jwtauth.app.framework.local.utils.toUser
import com.davidluna.jwtauth.data.sources.AuthDataSource
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import org.springframework.stereotype.Component

@Component
class UserLocalDataSource(
    private val dao: UserDao
) : AuthDataSource {

    override suspend fun userExists(request: AuthRequest): Either<AppError, Boolean> =
        dao.userExists(request.username)

    override suspend fun findUser(request: AuthRequest): Either<AppError, User?> =
        dao.findByUsername(request.username)

    override suspend fun saveUser(authRequest: AuthRequest, saltedHash: SaltedHash): Either<AppError, Boolean> =
        dao.save(authRequest.toUser(saltedHash))

}

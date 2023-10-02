package com.davidluna.jwtauth.app.framework.local.database.services

import arrow.core.Either
import com.davidluna.jwtauth.app.framework.local.database.daos.UserDao
import com.davidluna.jwtauth.app.framework.local.database.tables.Users
import com.davidluna.jwtauth.app.framework.local.utils.fill
import com.davidluna.jwtauth.app.framework.local.utils.toDatabaseRow
import com.davidluna.jwtauth.app.framework.local.utils.toDomain
import com.davidluna.jwtauth.app.utils.tryCatchSuspended
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class UserPersistenceService(
    database: Database
) : UserDao {

    init {
        transaction(database) { SchemaUtils.create(Users) }
    }

    override suspend fun findByUsername(username: String): Either<AppError, User?> =
        tryCatchSuspended {
            newSuspendedTransaction(Dispatchers.IO) {
                Users.select { Users.username eq username }.firstOrNull()?.toDomain<User>(Users)
            }
        }


    override suspend fun save(user: User): Either<AppError, Boolean> =
        tryCatchSuspended {
            newSuspendedTransaction(Dispatchers.IO) {
                Users.insert {
                    it.fill(Users, user.toDatabaseRow(Users))
                }.insertedCount > 0
            }
        }

    override suspend fun userExists(username: String): Either<AppError, Boolean> =
        tryCatchSuspended {
            newSuspendedTransaction(Dispatchers.IO) {
                Users.select { Users.username eq username }.firstOrNull() != null
            }
        }


    override suspend fun updateUser(user: User): Either<AppError, Boolean> =
        tryCatchSuspended {
            newSuspendedTransaction(Dispatchers.IO) {
                Users.update({ Users.username eq user.username }) { it.fill(Users, user.toDatabaseRow(Users)) } > 0
            }
        }

}

package com.davidluna.jwtauth.app.framework.local.utils

import com.davidluna.jwtauth.app.framework.local.database.entities.DBUser
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.Role
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import java.sql.ResultSet
import kotlin.reflect.full.memberProperties

fun ResultSet.toDomain(): User = User(
    getString("id"),
    getString("username"),
    getString("password"),
    getString("salt"),
    Role.valueOf(getString("role"))
)

fun AuthRequest.toRemote(saltedHash: SaltedHash): DBUser = DBUser(
    username = username,
    password = saltedHash.hash,
    salt = saltedHash.salt,
    role = "USER"
)

fun DBUser?.toDomain(): User {
    return User(
        id = this?.id?.toString()!!,
        username = username,
        password = password,
        salt = salt,
        role = Role.valueOf(this.role)
    )
}

inline fun <reified T : Any> T.toDatabase(): Array<Any?> =
    T::class.memberProperties.map { it.get(this) }.toTypedArray()
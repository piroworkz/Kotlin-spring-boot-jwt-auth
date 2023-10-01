package com.davidluna.jwtauth.app.framework.local.utils

import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.SaltedHash
import com.davidluna.jwtauth.domain.User
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.javaType
import kotlin.reflect.jvm.javaField


fun AuthRequest.toUser(saltedHash: SaltedHash): User = User(
    username = username,
    password = saltedHash.hash,
    salt = saltedHash.salt
)

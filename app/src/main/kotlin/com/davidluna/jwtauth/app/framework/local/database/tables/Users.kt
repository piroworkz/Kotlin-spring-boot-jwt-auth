package com.davidluna.jwtauth.app.framework.local.database.tables

import com.davidluna.jwtauth.app.framework.local.utils.Cipher.encryptor
import com.davidluna.jwtauth.domain.User
import org.jetbrains.exposed.crypt.encryptedVarchar
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object Users : Table() {
    private val id = uuid("id").autoGenerate()
    val password = encryptedVarchar(User::password.name, 255, encryptor)
    val role = encryptedVarchar(User::role.name, 255, encryptor)
    val salt = encryptedVarchar(User::salt.name, 255, encryptor)
    val username = encryptedVarchar(User::username.name, 255, encryptor)
    override val primaryKey = PrimaryKey(id)
}
package com.davidluna.jwtauth.app.framework.local.database.entities

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class DBUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    val password: String = "",
    val role: String = "",
    val salt: String = "",
    val username: String = ""
)
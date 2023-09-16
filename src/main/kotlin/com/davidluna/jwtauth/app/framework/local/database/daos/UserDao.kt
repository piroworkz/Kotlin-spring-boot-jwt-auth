package com.davidluna.jwtauth.app.framework.local.database.daos

import com.davidluna.jwtauth.app.framework.local.database.entities.DBUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDao : CrudRepository<DBUser, UUID> {
    fun findByUsername(username: String): DBUser?
    fun save(user: DBUser): DBUser
    fun existsDBUserByUsername(username: String): Boolean
}


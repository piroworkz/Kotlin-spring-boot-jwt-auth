package com.davidluna.jwtauth.data.sources

import com.davidluna.jwtauth.domain.SaltedHash

interface HashDataSource {
    fun createSaltedHash(value: String, length: Int = 32): SaltedHash
    fun validateHash(plainPassword: String, hashed: SaltedHash): Boolean
}
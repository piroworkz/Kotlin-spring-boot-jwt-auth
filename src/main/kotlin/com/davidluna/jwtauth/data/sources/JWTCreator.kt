package com.davidluna.jwtauth.data.sources

import com.davidluna.jwtauth.domain.JWTClaim
import javax.crypto.SecretKey

interface JWTCreator {
    fun generateToken(vararg claims: JWTClaim): String
    fun getKey(): SecretKey?
}
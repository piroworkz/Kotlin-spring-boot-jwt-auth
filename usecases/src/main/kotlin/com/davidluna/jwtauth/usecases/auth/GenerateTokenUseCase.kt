package com.davidluna.jwtauth.usecases.auth

import com.davidluna.jwtauth.domain.JWTClaim
import com.davidluna.jwtauth.data.sources.JWTCreator
import org.springframework.stereotype.Component

@Component
class GenerateTokenUseCase(private val jwtCreator: JWTCreator) {
    operator fun invoke(vararg claims: JWTClaim) =
        jwtCreator.generateToken(*claims)
}
package com.davidluna.jwtauth.usecases.auth

import com.davidluna.jwtauth.data.sources.JWTCreator
import org.springframework.stereotype.Component

@Component
class GetJWTKeyUseCase(private val jwtCreator: JWTCreator) {
    operator fun invoke() = jwtCreator.getKey()
}
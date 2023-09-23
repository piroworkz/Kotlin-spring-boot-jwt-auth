package com.davidluna.jwtauth.usecases.crypto

import arrow.core.raise.either
import com.davidluna.jwtauth.data.CryptoManagerRepository
import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class DecryptUseCase(val repository: CryptoManagerRepository) {
    suspend inline operator fun <reified T> invoke(s: String): T? = either {
        Gson().fromJson(repository.decrypt(s).bind(), T::class.java)
    }.fold(
        ifLeft = { null },
        ifRight = { it }
    )

}
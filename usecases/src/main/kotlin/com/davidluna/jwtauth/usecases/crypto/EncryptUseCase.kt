package com.davidluna.jwtauth.usecases.crypto

import com.davidluna.jwtauth.data.CryptoRepository
import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class EncryptUseCase(private val repository: CryptoRepository) {
    operator fun <T> invoke(t: T): String =
        repository.encrypt(Gson().toJson(t)).fold(
            ifLeft = { "{Something went wrong on encryption}" },
            ifRight = { it }
        )
}


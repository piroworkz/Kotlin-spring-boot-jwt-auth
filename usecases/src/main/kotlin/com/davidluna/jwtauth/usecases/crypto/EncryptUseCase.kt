package com.davidluna.jwtauth.usecases.crypto

import com.davidluna.jwtauth.data.CryptoManagerRepository
import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class EncryptUseCase(private val repository: CryptoManagerRepository) {
    suspend operator fun <T> invoke(t: T): String =
        repository.encrypt(Gson().toJson(t)).fold(
            ifLeft = { "{Something went wrong on encryption}" },
            ifRight = { it }
        )
}


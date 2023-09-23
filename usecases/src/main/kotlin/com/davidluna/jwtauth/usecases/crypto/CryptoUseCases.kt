package com.davidluna.jwtauth.usecases.crypto

import org.springframework.stereotype.Component

@Component
data class CryptoUseCases(
    val encrypt: EncryptUseCase,
    val decrypt: DecryptUseCase
)

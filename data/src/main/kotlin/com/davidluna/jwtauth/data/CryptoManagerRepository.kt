package com.davidluna.jwtauth.data

import arrow.core.Either
import com.davidluna.jwtauth.data.sources.Crypto
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class CryptoManagerRepository(
    private val crypto: Crypto
) {
    fun encrypt(value: String): Either<AppError, String> =
        crypto.encrypt(value)

    fun decrypt(encrypted: String?): Either<AppError, String> =
        crypto.decrypt(encrypted)

}
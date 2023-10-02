package com.davidluna.jwtauth.data

import arrow.core.Either
import com.davidluna.jwtauth.data.sources.CryptoManager
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class CryptoRepository(
    private val cryptoManager: CryptoManager
) {

    fun encrypt(value: String): Either<AppError, String> = cryptoManager.encrypt(value)
    fun decrypt(encrypted: String?): Either<AppError, String> = cryptoManager.decrypt(encrypted)

}
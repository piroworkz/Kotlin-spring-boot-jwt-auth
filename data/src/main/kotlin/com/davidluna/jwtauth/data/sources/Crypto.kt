package com.davidluna.jwtauth.data.sources

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError

interface Crypto {
    fun encrypt(value: String): Either<AppError, String>
    fun decrypt(encrypted: String?): Either<AppError, String>
}
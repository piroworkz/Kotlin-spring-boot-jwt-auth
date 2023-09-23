package com.davidluna.jwtauth.data.sources

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError

interface Crypto {
   suspend fun encrypt(value: String): Either<AppError, String>
   suspend fun decrypt(encrypted: String?): Either<AppError, String>
}
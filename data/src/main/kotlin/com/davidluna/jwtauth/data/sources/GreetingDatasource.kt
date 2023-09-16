package com.davidluna.jwtauth.data.sources

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError

interface GreetingDatasource {
    suspend fun getGreeting(name: String?): Either<AppError, String>
}
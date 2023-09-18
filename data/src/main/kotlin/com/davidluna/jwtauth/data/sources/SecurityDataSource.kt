package com.davidluna.jwtauth.data.sources

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError

interface SecurityDataSource {
    fun getToken(): Either<AppError, String>
}
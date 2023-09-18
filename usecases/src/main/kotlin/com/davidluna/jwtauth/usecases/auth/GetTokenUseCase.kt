package com.davidluna.jwtauth.usecases.auth

import arrow.core.Either
import com.davidluna.jwtauth.data.sources.SecurityDataSource
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class GetTokenUseCase(private val repository: SecurityDataSource) {
    operator fun invoke(): Either<AppError, String> = repository.getToken()
}
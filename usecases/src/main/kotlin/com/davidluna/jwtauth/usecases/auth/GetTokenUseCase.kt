package com.davidluna.jwtauth.usecases.auth

import arrow.core.Either
import com.davidluna.jwtauth.data.sources.SecurityRepository
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class GetTokenUseCase(private val repository: SecurityRepository) {
    operator fun invoke(): Either<AppError, String> = repository.getToken()
}
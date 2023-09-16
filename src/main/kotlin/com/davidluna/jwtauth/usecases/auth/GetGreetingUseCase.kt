package com.davidluna.jwtauth.usecases.auth

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.data.GreetingRepository
import org.springframework.stereotype.Component

@Component
class GetGreetingUseCase(private val repository: GreetingRepository) {
    suspend operator fun invoke(name: String?): Either<AppError, String> = repository.getGreeting(name)
}
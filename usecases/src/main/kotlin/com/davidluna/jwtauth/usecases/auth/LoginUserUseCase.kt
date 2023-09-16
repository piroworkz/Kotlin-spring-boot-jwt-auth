package com.davidluna.jwtauth.usecases.auth

import arrow.core.Either
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.User
import com.davidluna.jwtauth.data.AuthRepository
import org.springframework.stereotype.Component

@Component
class LoginUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: AuthRequest): Either<AppError, User> =
        repository.loginUser(request)
}
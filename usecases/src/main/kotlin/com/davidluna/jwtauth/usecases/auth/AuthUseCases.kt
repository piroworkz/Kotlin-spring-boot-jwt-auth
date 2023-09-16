package com.davidluna.jwtauth.usecases.auth

import org.springframework.stereotype.Component

@Component
data class AuthUseCases(
    val findUser: FindUserUseCase,
    val registerUser: RegisterUserUseCase,
    val loginUser: LoginUserUseCase
)
package com.davidluna.jwtauth.app.controller

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.Response
import com.davidluna.jwtauth.usecases.auth.AuthUseCases
import com.davidluna.jwtauth.usecases.auth.GenerateTokenUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(R.Paths.AUTH_PREFIX)
class AuthRouter(
    private val authUseCases: AuthUseCases,
    private val generate: GenerateTokenUseCase
) {

    @GetMapping(R.Paths.SALUTE)
    fun salute(): Response<String> {
        return "Hello world".buildSuccessResponse()
    }


    @PostMapping(R.Paths.GET_USER)
    suspend fun getUser(@RequestBody request: AuthRequest): Response<out Any?> =
        authUseCases.findUser(request).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { it.buildSuccessResponse(token = generate(*it.getClaims())) }
        )

    @PostMapping(R.Paths.SIGNUP)
    suspend fun register(@RequestBody request: AuthRequest): Response<out Any> =
        authUseCases.registerUser.invoke(request).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { it.buildSuccessResponse() }
        )

    @PostMapping(R.Paths.LOGIN)
    suspend fun login(@RequestBody request: AuthRequest): Response<out Any?> {
        return authUseCases.loginUser.invoke(request).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { it.buildSuccessResponse(token = generate(*it.getClaims())) }
        )
    }

}
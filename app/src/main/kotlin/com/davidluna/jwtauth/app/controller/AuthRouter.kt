package com.davidluna.jwtauth.app.controller

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.domain.Request
import com.davidluna.jwtauth.domain.Response
import com.davidluna.jwtauth.usecases.auth.AuthUseCases
import com.davidluna.jwtauth.usecases.auth.GenerateTokenUseCase
import com.davidluna.jwtauth.usecases.crypto.CryptoUseCases
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(R.Paths.AUTH_PREFIX)
class AuthRouter(
    private val authUseCases: AuthUseCases,
    private val generate: GenerateTokenUseCase,
    private val crypto: CryptoUseCases
) {

    @GetMapping(R.Paths.SALUTE)
    fun salute(): Response =
        "Hello world".buildSuccessResponse()

    @PostMapping(R.Paths.SIGNUP)
    suspend fun register(@RequestBody body: Request): Response = with(crypto) {
        val authRequest = getRequest<AuthRequest>(body) ?: return@with throwCryptoException()
        authUseCases.registerUser.invoke(authRequest).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { encrypt(it).buildSuccessResponse() }
        )
    }

    @PostMapping(R.Paths.LOGIN)
    suspend fun login(@RequestBody body: Request): Response = with(crypto) {
        val authRequest = getRequest<AuthRequest>(body) ?: return@with throwCryptoException()
        authUseCases.loginUser.invoke(authRequest).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { encrypt(it).buildSuccessResponse(token = generate(*it.getClaims())) }
        )
    }

    @PostMapping(R.Paths.GET_USER)
    suspend fun getUser(@RequestBody body: Request): Response = with(crypto) {
        val authRequest = getRequest<AuthRequest>(body) ?: return@with throwCryptoException()
        authUseCases.findUser(authRequest).fold(
            ifLeft = { it.buildFailResponse() },
            ifRight = { encrypt(it).buildSuccessResponse(generate(*it.getClaims())) }
        )
    }

}


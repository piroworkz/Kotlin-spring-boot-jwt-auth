package com.davidluna.jwtauth.app.controller

import arrow.core.getOrElse
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.Response
import com.davidluna.jwtauth.usecases.auth.GetTokenUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(R.ProtectedPaths.GREET_PREFIX)
class ProtectedRouter(
    private val getTokenUseCase: GetTokenUseCase,
) {

    @GetMapping(R.ProtectedPaths.HELLO)
    fun sayHello(): Response<String> {
        return tryCatch {
            "Hello world".buildSuccessResponse(getTokenUseCase().getOrElse { "" })
        }.fold(
            ifLeft = {
                println("<-- ::: $it")
                it.buildFailResponse()
            },
            ifRight = {
                it
            }
        )
    }
}
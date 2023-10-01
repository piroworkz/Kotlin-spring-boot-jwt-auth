package com.davidluna.jwtauth.app.controller

import com.davidluna.jwtauth.app.r.R.ProtectedPaths.HELLO
import com.davidluna.jwtauth.app.r.R.ProtectedPaths.PROTECTED_PREFIX
import com.davidluna.jwtauth.app.r.R.Strings.AUTHORIZATION
import com.davidluna.jwtauth.app.r.R.Strings.BEARER
import com.davidluna.jwtauth.app.r.R.Strings.EMPTY_STRING
import com.davidluna.jwtauth.domain.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(PROTECTED_PREFIX)
class ProtectedRouter {

    @GetMapping(HELLO)
    suspend fun hello(request: HttpServletRequest): Response =
        "This is a protected path".buildSuccessResponse(getTokenFromHeader(request))

    private fun getTokenFromHeader(request: HttpServletRequest): String =
        request.getHeader(AUTHORIZATION).replace(BEARER, EMPTY_STRING)

}
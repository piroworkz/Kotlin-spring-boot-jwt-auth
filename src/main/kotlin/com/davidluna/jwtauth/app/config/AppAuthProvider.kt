package com.davidluna.jwtauth.app.config

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.AuthRequest
import com.davidluna.jwtauth.usecases.auth.FindUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AppAuthProvider(
    @Qualifier(R.Strings.SCOPE_IO)
    private val scopeIO: CoroutineScope,
    private val findUserUseCase: FindUserUseCase,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        getRequest(authentication).apply {
            tryLogin(this)
            return UsernamePasswordAuthenticationToken(
                username, password, listOf(SimpleGrantedAuthority(role.value))
            )
        }
    }

    override fun supports(authentication: Class<*>?): Boolean =
        if (authentication != null) UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication) else false

    private fun getRequest(authentication: Authentication?) =
        authentication?.let { AuthRequest(it.name, it.credentials.toString()) } ?: throw AppError.UnAuthorized(400)

    private fun tryLogin(request: AuthRequest?) {
        scopeIO.launch {
            request?.let {
                findUserUseCase(it).findOrNull { u ->
                    if (!passwordEncoder.matches(request.password, u.password))
                        throw AppError.UnAuthorized(400)
                    passwordEncoder.matches(request.password, u.password)
                }
            }
        }
    }
}
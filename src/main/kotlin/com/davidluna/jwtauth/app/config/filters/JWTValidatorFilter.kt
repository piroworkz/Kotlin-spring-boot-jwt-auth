package com.davidluna.jwtauth.app.config.filters

import com.davidluna.jwtauth.app.controller.toAppError
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.usecases.auth.GetJWTKeyUseCase
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTValidatorFilter(
    private val getJWTKeyUseCase: GetJWTKeyUseCase
) : OncePerRequestFilter() {

    private val shouldNotValidate = listOf(
        R.Paths.LOGIN,
        R.Paths.SIGNUP,
        R.Paths.LOGOUT,
        R.Paths.SALUTE,
        R.Paths.GET_USER
    ).map { R.Paths.AUTH_PREFIX + it }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        validate(request)
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath in shouldNotValidate
    }

    private fun validate(request: HttpServletRequest) {
        try {
            println("<-- ${request.getHeader(R.Strings.AUTHORIZATION).trim()}")
            val claims = getClaims(request.getHeader(R.Strings.AUTHORIZATION).trim())

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    claims[R.JWTConfig.CLAIM_USERNAME],
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(
                        claims[R.JWTConfig.CLAIM_AUTHORITIES].toString()
                    )
                )
        } catch (e: Exception) {
            throw e.toAppError()
        }
    }

    private fun getClaims(currentToken: String): Claims {
        val actualToken = if (currentToken.startsWith("Bearer ")) {
            currentToken.substring("Bearer ".length)
        } else {
            currentToken
        }.trim()
        return Jwts.parserBuilder()
            .setSigningKey(getJWTKeyUseCase())
            .build()
            .parseClaimsJws(actualToken)
            .body
    }

}
package com.davidluna.jwtauth.app.config.filters

import com.davidluna.jwtauth.app.controller.toJwtError
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
    ).map { R.Paths.AUTH_PREFIX + it }.plus(R.Paths.HOME)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = request.getHeader(R.Strings.AUTHORIZATION).replace(R.Strings.BEARER, R.Strings.EMPTY_STRING)
            val claims: Claims = getClaims(token)

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(
                    claims[R.JWTConfig.CLAIM_USERNAME],
                    token,
                    AuthorityUtils
                        .commaSeparatedStringToAuthorityList(claims[R.JWTConfig.CLAIM_AUTHORITIES].toString())
                )
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            throw e.toJwtError()
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath in shouldNotValidate
    }

    private fun getClaims(currentToken: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(getJWTKeyUseCase())
            .build()
            .parseClaimsJws(currentToken)
            .body


}

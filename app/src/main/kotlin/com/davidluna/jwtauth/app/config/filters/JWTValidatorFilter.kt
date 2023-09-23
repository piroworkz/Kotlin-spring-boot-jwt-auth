package com.davidluna.jwtauth.app.config.filters

import com.davidluna.jwtauth.app.controller.buildFailResponse
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.domain.AppError
import com.davidluna.jwtauth.domain.JwtError
import com.davidluna.jwtauth.domain.Response
import com.davidluna.jwtauth.usecases.auth.GetJWTKeyUseCase
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
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
            response.apply {
                contentType = R.Strings.APPLICATION_JSON
                characterEncoding = R.Strings.CHARSET_UTF_8
                writer.write(e.toJwtError().buildFailResponse().toJson())
            }
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

    private fun Response.toJson(): String =
        ObjectMapper().writeValueAsString(this)

    fun Throwable.toJwtError(): AppError {
        return when (this) {
            is UnsupportedJwtException -> JwtError.MalformedJwt(500)
            is MalformedJwtException -> JwtError.MalformedJwt(500)
            is SignatureException -> JwtError.MalformedJwt(500)
            is IllegalArgumentException -> JwtError.MalformedJwt(500)
            is ExpiredJwtException -> JwtError.ExpiredJwt(500)
            is Exception -> AppError.UnknownError(500)
            else -> AppError.UnknownError(500)
        }
    }
}


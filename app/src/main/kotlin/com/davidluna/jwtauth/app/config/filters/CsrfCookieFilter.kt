package com.davidluna.jwtauth.app.config.filters

import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CsrfCookieFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val csrfToken: CsrfToken = request.getAttribute(CsrfToken::class.java.getName()) as CsrfToken
        if (csrfToken.headerName != null) {
            response.setHeader(csrfToken.headerName, csrfToken.token)
        }
        filterChain.doFilter(request, response)
    }
}
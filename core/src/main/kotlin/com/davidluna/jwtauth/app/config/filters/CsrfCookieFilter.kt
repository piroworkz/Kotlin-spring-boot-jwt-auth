package com.davidluna.jwtauth.app.config.filters

import org.springframework.security.web.csrf.CsrfToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CsrfCookieFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val csrfToken: CsrfToken? = request.getAttribute(CsrfToken::class.java.name) as CsrfToken?
        csrfToken?.let {
            if (it.headerName != null) {
                response.setHeader(it.headerName, it.token)
            }
        }
        filterChain.doFilter(request, response)
    }
}
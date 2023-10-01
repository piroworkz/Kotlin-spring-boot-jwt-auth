package com.davidluna.jwtauth.app.config

import com.davidluna.jwtauth.app.config.filters.CsrfCookieFilter
import com.davidluna.jwtauth.app.config.filters.JWTValidatorFilter
import com.davidluna.jwtauth.app.r.R.ProtectedPaths.HELLO
import com.davidluna.jwtauth.app.r.R.Strings.ALLOWED_METHODS
import com.davidluna.jwtauth.app.r.R.Strings.ALLOW_ALL
import com.davidluna.jwtauth.app.r.R.Strings.AUTHORIZATION
import com.davidluna.jwtauth.app.r.R.Strings.AUTH_PREFIX
import com.davidluna.jwtauth.app.r.R.Strings.GREET_PREFIX
import com.davidluna.jwtauth.usecases.auth.GetJWTKeyUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfFilter.DEFAULT_CSRF_MATCHER
import org.springframework.web.cors.CorsConfiguration

@Configuration
class SecurityConfiguration(
    private val getJWTKeyUseCase: GetJWTKeyUseCase
) {

    @Bean
    fun customSecurityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .cors(::configCors)
        .csrf(::configCsrf)
        .addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
        .addFilterBefore(JWTValidatorFilter(getJWTKeyUseCase), BasicAuthenticationFilter::class.java)
        .authorizeHttpRequests(::configRequests)
        .formLogin { }
        .httpBasic { }
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()


    private fun configRequests(it: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        it.antMatchers(AUTH_PREFIX, GREET_PREFIX, HELLO).permitAll()
        it.anyRequest().authenticated()
    }

    private fun configCsrf(it: CsrfConfigurer<HttpSecurity>): CsrfConfigurer<HttpSecurity> {
        return it
            .ignoringRequestMatchers(DEFAULT_CSRF_MATCHER)
            .csrfTokenRepository(CookieCsrfTokenRepository())
    }

    private fun configCors(corsConfigurer: CorsConfigurer<HttpSecurity>): CorsConfigurer<HttpSecurity> {
        return corsConfigurer.configurationSource {
            return@configurationSource CorsConfiguration().apply {
                this.allowedOrigins = listOf(ALLOW_ALL)
                this.allowedMethods = listOf(ALLOWED_METHODS)
                this.allowedHeaders = listOf(ALLOW_ALL)
                this.maxAge = 1800L
                this.exposedHeaders = listOf(AUTHORIZATION)
                this.allowCredentials = true
            }
        }
    }

}
@file:Suppress("unused")

package com.davidluna.jwtauth.app.r

sealed interface R {

    object Strings {
        const val CHARSET_UTF_8 = "UTF-8"
        const val APPLICATION_JSON = "application/json"
        const val BEARER = "Bearer "
        const val EMPTY_STRING = ""
        const val SCOPE_IO = "scopeIO"
        const val SCOPE_MAIN = "scopeMain"
        const val CSRF = "_csrf"
        const val AUTHORIZATION = "Authorization"
        const val ALLOWED_METHODS = "POST"
        const val ALLOW_ALL = "*"
        const val AUTH_PREFIX = "/auth/**"
        const val GREET_PREFIX = "/greet/**"
    }

    object ProtectedPaths {
        const val GREET_PREFIX = "/protected"
        const val HELLO = "/hello"
    }

    object Paths {
        const val AUTH_PREFIX = "/auth"
        const val LOGIN = "/login"
        const val SIGNUP = "/signup"
        const val LOGOUT = "/logout"
        const val SALUTE = "/salute"
        const val GET_USER = "/getUser"
    }

    object JWTConfig {
        val expiration = System.currentTimeMillis().plus(600L * 1000L)
        const val CLAIM_USERNAME = "USERNAME"
        const val CLAIM_AUTHORITIES = "AUTHORITIES"
        const val ISSUER = "REST_API"
        const val JWT_SECRET = "JWT_SECRET"
        const val SUBJECT = "Json_Web_Token"
    }

    object EnvVariable {
        private const val BLOCK_MODE = "CBC"
        private const val PADDING = "PKCS5PADDING"
        const val ALGORITHM = "AES"
        const val CRYPTO_KEY = "CRYPTO_KEY"
        const val INIT_VECTOR = "INIT_VECTOR"
        const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}
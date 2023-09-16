package com.davidluna.jwtauth.app.di

import com.davidluna.jwtauth.app.r.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppModule {

    @get:Bean(R.Strings.SCOPE_IO)
    val scopeIo get() = CoroutineScope(Dispatchers.IO)

    @get:Bean(R.Strings.SCOPE_MAIN)
    val scopeMain get() = CoroutineScope(Dispatchers.Main)

    @get:Bean(R.JWTConfig.JWT_SECRET)
    val jwtSecret get() = System.getenv(R.JWTConfig.JWT_SECRET)

}
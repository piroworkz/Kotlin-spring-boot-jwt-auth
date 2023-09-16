package com.davidluna.jwtauth.data

import com.davidluna.jwtauth.data.sources.GreetingDatasource
import org.springframework.stereotype.Component

@Component
class GreetingRepository(private val local: GreetingDatasource) {

    suspend fun getGreeting(name: String?) =
        local.getGreeting(name)

}
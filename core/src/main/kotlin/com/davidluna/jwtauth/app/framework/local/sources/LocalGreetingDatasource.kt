package com.davidluna.jwtauth.app.framework.local.sources

import arrow.core.Either
import com.davidluna.jwtauth.app.utils.tryCatchSuspended
import com.davidluna.jwtauth.data.sources.GreetingDatasource
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class LocalGreetingDatasource : GreetingDatasource {

    override suspend fun getGreeting(name: String?): Either<AppError, String> = tryCatchSuspended {
        if (name.isNullOrEmpty()) throw NullPointerException()
        "Hello, $name"
    }
}
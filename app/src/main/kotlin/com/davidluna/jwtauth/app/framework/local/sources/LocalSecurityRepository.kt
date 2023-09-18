package com.davidluna.jwtauth.app.framework.local.sources

import com.davidluna.jwtauth.app.controller.tryCatch
import com.davidluna.jwtauth.data.sources.SecurityRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class LocalSecurityRepository : SecurityRepository {

    override fun getToken() = tryCatch {
        "${SecurityContextHolder.getContext().authentication.credentials}"
    }

}
package com.davidluna.jwtauth.app.framework.local.sources

import com.davidluna.jwtauth.app.controller.tryCatch
import com.davidluna.jwtauth.data.sources.SecurityDataSource
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class LocalSecurityRepository : SecurityDataSource {

    override fun getToken() = tryCatch {
        "${SecurityContextHolder.getContext().authentication.credentials}"
    }

}
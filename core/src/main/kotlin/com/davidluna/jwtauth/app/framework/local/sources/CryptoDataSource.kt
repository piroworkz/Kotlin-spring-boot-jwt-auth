package com.davidluna.jwtauth.app.framework.local.sources

import arrow.core.Either
import com.davidluna.jwtauth.app.framework.local.utils.Crypto
import com.davidluna.jwtauth.app.utils.tryCatch
import com.davidluna.jwtauth.data.sources.CryptoManager
import com.davidluna.jwtauth.domain.AppError
import org.springframework.stereotype.Component

@Component
class CryptoDataSource : CryptoManager {

    override fun encrypt(value: String): Either<AppError, String> =
        tryCatch { Crypto.encrypt(value) }

    override fun decrypt(encrypted: String?): Either<AppError, String> =
        tryCatch { Crypto.decrypt(encrypted) }

}
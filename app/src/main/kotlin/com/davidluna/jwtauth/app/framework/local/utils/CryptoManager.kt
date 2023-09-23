package com.davidluna.jwtauth.app.framework.local.utils

import arrow.core.Either
import com.davidluna.jwtauth.app.controller.tryCatch
import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.app.r.R.EnvVariable.ALGORITHM
import com.davidluna.jwtauth.app.r.R.EnvVariable.TRANSFORMATION
import com.davidluna.jwtauth.data.sources.Crypto
import com.davidluna.jwtauth.domain.AppError
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.Base64.getDecoder
import java.util.Base64.getEncoder
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

@Component
class CryptoManager(
    @Qualifier(R.EnvVariable.CRYPTO_KEY)
    key: String,
    @Qualifier(R.EnvVariable.INIT_VECTOR)
    iv: String,
) : Crypto {

    private val secretKeySpec = SecretKeySpec(key.toByteArray(UTF_8), ALGORITHM)
    private val ivParameterSpec = IvParameterSpec(iv.toByteArray(UTF_8))


    private val encryptCipher: Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
    }

    private val decryptCypherForIv: Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
    }

    override fun encrypt(value: String): Either<AppError, String> = tryCatch {
        String(getEncoder().encode(encryptCipher.doFinal(value.toByteArray())))
    }

    override fun decrypt(encrypted: String?): Either<AppError, String> = tryCatch {
        String(decryptCypherForIv.doFinal(getDecoder().decode(encrypted)))
    }

}

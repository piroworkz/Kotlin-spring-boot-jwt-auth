package com.davidluna.jwtauth.app.framework.local.utils

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.app.r.R.EnvVariable.ALGORITHM
import com.davidluna.jwtauth.app.r.R.EnvVariable.TRANSFORMATION
import com.google.gson.Gson
import org.springframework.stereotype.Component
import java.util.Base64.getDecoder
import java.util.Base64.getEncoder
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

@Component
object Crypto {

    private val key: String
        get() = System.getenv(R.EnvVariable.CRYPTO_KEY) ?: ""

    private val iv: String
        get() = System.getenv(R.EnvVariable.INIT_VECTOR) ?: ""

    private val secretKeySpec = SecretKeySpec(key.toByteArray(UTF_8), ALGORITHM)
    private val ivParameterSpec = IvParameterSpec(iv.toByteArray(UTF_8))


    private val encryptCipher: Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
    }

    private val decryptCypherForIv: Cipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
    }

    fun encrypt(value: String): String =
        String(getEncoder().encode(encryptCipher.doFinal(value.toByteArray())))


    fun decrypt(encrypted: String?): String =
        String(decryptCypherForIv.doFinal(getDecoder().decode(encrypted)))

}

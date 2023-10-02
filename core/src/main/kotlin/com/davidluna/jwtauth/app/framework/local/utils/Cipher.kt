package com.davidluna.jwtauth.app.framework.local.utils

import org.jetbrains.exposed.crypt.Encryptor

object Cipher {
    val encryptor = Encryptor(
        encryptFn = { value -> Crypto.encrypt(value) },
        decryptFn = { value -> Crypto.decrypt(value) },
        maxColLengthFn = { value -> value * 2 }
    )
}
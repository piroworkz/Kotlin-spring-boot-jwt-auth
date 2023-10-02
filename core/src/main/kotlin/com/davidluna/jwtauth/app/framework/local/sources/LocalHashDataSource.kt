package com.davidluna.jwtauth.app.framework.local.sources

import com.davidluna.jwtauth.data.sources.HashDataSource
import com.davidluna.jwtauth.domain.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class LocalHashDataSource : HashDataSource {

    override fun createSaltedHash(value: String, length: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(length)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(salt = saltAsHex, hash = hash)
    }

    override fun validateHash(plainPassword: String, hashed: SaltedHash): Boolean {
        val hash = DigestUtils.sha256Hex("${hashed.salt}$plainPassword")
        return hash == hashed.hash
    }

}
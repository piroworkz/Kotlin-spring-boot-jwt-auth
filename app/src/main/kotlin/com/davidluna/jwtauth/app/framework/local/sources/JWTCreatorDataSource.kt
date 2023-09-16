package com.davidluna.jwtauth.app.framework.local.sources

import com.davidluna.jwtauth.app.r.R
import com.davidluna.jwtauth.data.sources.JWTCreator
import com.davidluna.jwtauth.domain.JWTClaim
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class JWTCreatorDataSource(
    @Qualifier(R.JWTConfig.JWT_SECRET)
    private val jwtSecret: String
) : JWTCreator {

    override fun generateToken(vararg claims: JWTClaim): String {
        return try {
            val token = Jwts.builder()
            claims.forEach { claim ->
                token.claim(claim.name, claim.value)
            }
            token
                .setIssuedAt(Date())
                .setExpiration(Date(R.JWTConfig.expiration))
                .setIssuer(R.JWTConfig.ISSUER)
                .setSubject(R.JWTConfig.SUBJECT)
                .signWith(getKey()).compact()
        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message + e.cause + e.stackTrace)
            throw e
        }
    }

    override fun getKey(): SecretKey? {
        return Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8))
    }

}
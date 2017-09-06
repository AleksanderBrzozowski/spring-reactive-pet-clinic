package com.brzozowski.springpetclinic.infrastructure.security.token

import com.brzozowski.springpetclinic.infrastructure.security.AccountPrincipal
import com.brzozowski.springpetclinic.infrastructure.security.UnauthorizedException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory

/**
 * @author Aleksander Brzozowski
 */
class TokenService(private val jwtSecretKey: String) {

    private val logger = LoggerFactory.getLogger(TokenService::class.java)

    companion object {
        const val USER_ID = "USER_ID"
    }

    fun generateToken(id: String): String {
        val claims = mapOf(USER_ID to id)
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact()
    }

    fun validateToken(token: String): AccountPrincipal {
        return try {
            Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .body
                    .let { AccountPrincipal(it[USER_ID].toString()) }
        } catch (t: Throwable) {
            logger.error("Validate token error for token: $token", t)
            throw UnauthorizedException()
        }
    }
}
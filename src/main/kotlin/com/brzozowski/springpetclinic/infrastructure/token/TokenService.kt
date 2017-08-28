package com.brzozowski.springpetclinic.infrastructure.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

/**
 * @author Aleksander Brzozowski
 */
class TokenService(private val jwtSecretKey: String) {

    companion object {
        const val USERNAME = "username"
    }

    fun generateToken(username: String): String {
        return Jwts.builder()
                .setClaims(mapOf(USERNAME to username))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .body
        return Authentication(claims[USERNAME].toString())
    }
}
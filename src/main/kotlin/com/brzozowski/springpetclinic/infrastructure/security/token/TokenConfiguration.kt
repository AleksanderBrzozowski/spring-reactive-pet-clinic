package com.brzozowski.springpetclinic.infrastructure.security.token

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Aleksander Brzozowski
 */

@Configuration
class TokenConfiguration {

    @Bean
    fun tokenService(@Value("\${jwt.secretKey}") jwtSecretKey: String) = TokenService(jwtSecretKey)

    fun tokenService() = TokenService("secret")
}
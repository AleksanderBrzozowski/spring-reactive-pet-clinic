package com.brzozowski.springpetclinic.account.domain.dto

/**
 * @author Aleksander Brzozowski
 */
data class CreateAccountDto(
        val firstName: String,
        val lastName: String,
        val username: String,
        val password: String,
        val email: String
)
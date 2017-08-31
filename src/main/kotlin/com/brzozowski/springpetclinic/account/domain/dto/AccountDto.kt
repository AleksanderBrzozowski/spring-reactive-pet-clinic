package com.brzozowski.springpetclinic.account.domain.dto

/**
 * @author Aleksander Brzozowski
 */
data class AccountDto(
        val id: String,
        val firstName: String,
        val lastName: String,
        val username: String,
        val email: String
)
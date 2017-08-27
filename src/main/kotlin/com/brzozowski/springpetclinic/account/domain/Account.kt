package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.dto.AccountDto
import java.util.*

/**
 * @author Aleksander Brzozowski
 */
class Account(
        val id: String = UUID.randomUUID().toString(),
        var firstName: String,
        var lastName: String,
        val username: String,
        var password: String,
        var email: String
) {
    fun toDto() = AccountDto(
            id = id,
            firstName = firstName,
            lastName = lastName,
            username = username,
            email = email
    )
}
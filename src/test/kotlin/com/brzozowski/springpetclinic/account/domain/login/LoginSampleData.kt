package com.brzozowski.springpetclinic.account.domain.login

import com.brzozowski.springpetclinic.account.domain.login.dto.LoginDto

/**
 * @author Aleksander Brzozowski
 */

fun wrongPassword() = correct().copy(password = "pass1")
fun wrongUsername() = correct().copy(username = "jankow1")
fun correct(): LoginDto {
    val credentials = com.brzozowski.springpetclinic.account.domain.create.correct()
    return LoginDto(username = credentials.username, password = credentials.password)
}
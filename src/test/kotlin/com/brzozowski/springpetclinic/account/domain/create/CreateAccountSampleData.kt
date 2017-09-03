package com.brzozowski.springpetclinic.account.domain.create

import com.brzozowski.springpetclinic.account.domain.create.dto.CreateAccountDto

/**
 * @author Aleksander Brzozowski
 */

fun correct() = CreateAccountDto(firstName = "Jan", lastName = "Kowalski", username = "jankow",
        password = "pass", email = "jankowalski@gmail.com")

fun wrongEmailAddressPattern() = correct().copy(email = "jankowalski@gmail")
fun sameEmailAddress() = correct().copy(username = "jankow2")
fun sameEmailAddressUsernameOtherCase() = correct().copy(email = "janKowalski@gmail.com", username = "jankow2")
fun sameUsername() = correct().copy(email = "jankowalski2@gmail.com")

fun emptyFirstName() = correct().copy(firstName = "")
fun emptyLastName() = correct().copy(lastName = "")
fun emptyUsername() = correct().copy(username = "")
fun emptyPassword() = correct().copy(password = "")
fun emptyEmail() = correct().copy(email = "")
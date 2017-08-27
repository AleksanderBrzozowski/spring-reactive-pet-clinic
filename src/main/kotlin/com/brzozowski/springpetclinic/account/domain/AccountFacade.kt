package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import com.brzozowski.springpetclinic.account.domain.dto.CreateAccountDto

/**
 * @author Aleksander Brzozowski
 */
class AccountFacade(private val createAccountService: CreateAccountService) {

    fun createAccount(createAccountDto: CreateAccountDto) = createAccountService.createAccount(createAccountDto)
}
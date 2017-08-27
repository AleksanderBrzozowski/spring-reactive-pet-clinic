package com.brzozowski.springpetclinic.domain.account

import com.brzozowski.springpetclinic.domain.account.create.CreateAccountService
import com.brzozowski.springpetclinic.domain.account.dto.CreateAccountDto

/**
 * @author Aleksander Brzozowski
 */
class AccountFacade(private val createAccountService: CreateAccountService) {

    fun createAccount(createAccountDto: CreateAccountDto) = createAccountService.createAccount(createAccountDto)
}
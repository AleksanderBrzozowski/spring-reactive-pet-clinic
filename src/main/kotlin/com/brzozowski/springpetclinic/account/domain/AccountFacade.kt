package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import com.brzozowski.springpetclinic.account.domain.dto.CreateAccountDto
import com.brzozowski.springpetclinic.account.domain.login.LoginService
import com.brzozowski.springpetclinic.account.domain.login.dto.LoginDto

/**
 * @author Aleksander Brzozowski
 */
class AccountFacade(private val createAccountService: CreateAccountService,
                    private val loginService: LoginService) {

    fun createAccount(createAccountDto: CreateAccountDto) = createAccountService.createAccount(createAccountDto)

    fun login(loginDto: LoginDto) = loginService.authenticate(loginDto)
}
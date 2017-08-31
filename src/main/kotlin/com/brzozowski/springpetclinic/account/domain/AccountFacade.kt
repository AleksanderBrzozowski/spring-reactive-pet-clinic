package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import com.brzozowski.springpetclinic.account.domain.create.dto.CreateAccountDto
import com.brzozowski.springpetclinic.account.domain.dto.AccountDto
import com.brzozowski.springpetclinic.account.domain.login.LoginService
import com.brzozowski.springpetclinic.account.domain.login.dto.LoginDto
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfEmpty
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.SystemException
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
class AccountFacade(private val createAccountService: CreateAccountService,
                    private val loginService: LoginService,
                    private val accountRepository: AccountRepository) {

    fun createAccount(createAccountDto: CreateAccountDto) = createAccountService.createAccount(createAccountDto)

    fun login(loginDto: LoginDto) = loginService.authenticate(loginDto)

    fun findById(id: String): Mono<AccountDto> {
        return accountRepository.findById(id)
                .map { it.toDto() }
                .errorIfEmpty { SystemException("Not found account with id: $id") }
    }

}
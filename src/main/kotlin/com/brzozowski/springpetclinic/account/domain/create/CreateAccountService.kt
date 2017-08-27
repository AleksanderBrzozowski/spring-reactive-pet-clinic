package com.brzozowski.springpetclinic.domain.account.create

import com.brzozowski.springpetclinic.domain.account.Account
import com.brzozowski.springpetclinic.domain.account.AccountRepository
import com.brzozowski.springpetclinic.domain.account.create.exc.EmailAddressAlreadyInUseException
import com.brzozowski.springpetclinic.domain.account.create.exc.EmptyCredentialsException
import com.brzozowski.springpetclinic.domain.account.create.exc.UsernameAlreadyInUseException
import com.brzozowski.springpetclinic.domain.account.create.exc.WrongEmailAddressPatternException
import com.brzozowski.springpetclinic.domain.account.dto.AccountDto
import com.brzozowski.springpetclinic.domain.account.dto.CreateAccountDto
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
class CreateAccountService(private val accountRepository: AccountRepository,
                           private val passwordEncoder: PasswordEncoder) {

    fun createAccount(createAccountDto: CreateAccountDto): Mono<AccountDto> {
        return Mono.fromCallable {
            checkCredentialsNotEmpty(createAccountDto)
            checkEmailAddress(createAccountDto.email)
        }
                .flatMap {
                    accountRepository.findByEmail(createAccountDto.email)
                            .flatMap { Mono.error<Account>(EmailAddressAlreadyInUseException()) }
                }
                .switchIfEmpty(accountRepository.findByUsername(createAccountDto.username)
                        .flatMap { Mono.error<Account>(UsernameAlreadyInUseException()) }
                )
                .switchIfEmpty(accountRepository.save(accountFrom(createAccountDto)))
                .map { it.toDto() }
    }

    private fun checkCredentialsNotEmpty(createAccountDto: CreateAccountDto) {
        val allCredentialsFilled = arrayOf(createAccountDto.email, createAccountDto.firstName,
                createAccountDto.lastName, createAccountDto.password, createAccountDto.username)
                .map { it.isNotBlank() }
                .all { it }
        if (!allCredentialsFilled) {
            throw EmptyCredentialsException()
        }
    }

    private fun checkEmailAddress(email: String) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw WrongEmailAddressPatternException()
        }
    }

    private fun accountFrom(createAccountDto: CreateAccountDto): Account {
        return Account(
                firstName = createAccountDto.firstName,
                lastName = createAccountDto.lastName,
                username = createAccountDto.username,
                password = passwordEncoder.encode(createAccountDto.password),
                email = createAccountDto.email
        )
    }
}


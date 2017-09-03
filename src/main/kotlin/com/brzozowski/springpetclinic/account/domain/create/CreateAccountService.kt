package com.brzozowski.springpetclinic.account.domain.create

import com.brzozowski.springpetclinic.account.domain.Account
import com.brzozowski.springpetclinic.account.domain.AccountRepository
import com.brzozowski.springpetclinic.account.domain.create.dto.CreateAccountDto
import com.brzozowski.springpetclinic.account.domain.create.exc.EmailAddressAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.EmptyCredentialsException
import com.brzozowski.springpetclinic.account.domain.create.exc.UsernameAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.WrongEmailAddressPatternException
import com.brzozowski.springpetclinic.account.domain.dto.AccountDto
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfNotEmpty
import com.brzozowski.springpetclinic.infrastructure.extension.switchIfEmpty
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
            checkEmailAddressPattern(createAccountDto.email)
        }
                .flatMap { accountRepository.findByEmailStartsWithIgnoreCase(createAccountDto.email) }
                .errorIfNotEmpty { EmailAddressAlreadyInUseException() }
                .switchIfEmpty { accountRepository.findByUsername(createAccountDto.username) }
                .errorIfNotEmpty { UsernameAlreadyInUseException() }
                .switchIfEmpty { accountRepository.save(accountFrom(createAccountDto)) }
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

    private fun checkEmailAddressPattern(email: String) {
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


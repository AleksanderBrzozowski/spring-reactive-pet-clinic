package com.brzozowski.springpetclinic.account.domain.login

import com.brzozowski.springpetclinic.account.domain.AccountRepository
import com.brzozowski.springpetclinic.account.domain.login.dto.LoginDto
import com.brzozowski.springpetclinic.account.domain.login.exc.WrongCredentialsException
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfEmpty
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfNot
import com.brzozowski.springpetclinic.infrastructure.token.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
class LoginService(private val accountRepository: AccountRepository,
                   private val passwordEncoder: PasswordEncoder,
                   private val tokenService: TokenService) {

    fun authenticate(loginDto: LoginDto): Mono<String> {
        return accountRepository.findByUsername(loginDto.username)
                .errorIfEmpty { WrongCredentialsException() }
                .errorIfNot(predicate = { passwordEncoder.matches(loginDto.password, it.password) },
                        throwable = { WrongCredentialsException() })
                .map { tokenService.generateToken(it.username) }
    }
}
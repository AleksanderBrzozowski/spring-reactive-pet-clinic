package com.brzozowski.springpetclinic.account.domain.login

import com.brzozowski.springpetclinic.account.domain.AccountRepository
import com.brzozowski.springpetclinic.account.domain.dto.AccountDto
import com.brzozowski.springpetclinic.account.domain.login.dto.LoginDto
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author Aleksander Brzozowski
 */
class LoginService(private val accountRepository: AccountRepository,
                   private val passwordEncoder: PasswordEncoder) {

    fun login(loginDto: LoginDto): AccountDto {
        accountRepository.findByUsername(loginDto.username)
    }
}
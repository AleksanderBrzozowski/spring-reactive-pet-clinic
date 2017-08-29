package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import com.brzozowski.springpetclinic.account.domain.login.LoginService
import com.brzozowski.springpetclinic.infrastructure.security.token.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author Aleksander Brzozowski
 */

@Configuration
class AccountConfiguration {

    fun accountFacade(accountRepository: AccountRepository, passwordEncoder: PasswordEncoder,
                      tokenService: TokenService): AccountFacade {
        val createAccountService = CreateAccountService(
                accountRepository = accountRepository,
                passwordEncoder = passwordEncoder
        )
        val loginService = LoginService(
                accountRepository = accountRepository,
                passwordEncoder = passwordEncoder,
                tokenService = tokenService
        )

        return AccountFacade(createAccountService = createAccountService, loginService = loginService)
    }

    @Bean
    fun accountFacade(tokenService: TokenService): AccountFacade {
        return accountFacade(
                accountRepository = InMemoryAccountRepository(),
                passwordEncoder = BCryptPasswordEncoder(),
                tokenService = tokenService
        )
    }
}
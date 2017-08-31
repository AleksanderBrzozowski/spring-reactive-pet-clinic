package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import com.brzozowski.springpetclinic.account.domain.login.LoginService
import com.brzozowski.springpetclinic.infrastructure.security.token.TokenConfiguration
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

    fun accountFacade(): AccountFacade {
        val accountRepository = InMemoryAccountRepository()
        val passwordEncoder = BCryptPasswordEncoder()
        val tokenService = TokenConfiguration().tokenService("secret")

        return createAccountFacade(
                accountRepository = accountRepository,
                passwordEncoder = passwordEncoder,
                tokenService = tokenService
        )
    }

    @Bean
    fun accountFacade(tokenService: TokenService): AccountFacade {
        return createAccountFacade(
                accountRepository = InMemoryAccountRepository(),
                passwordEncoder = BCryptPasswordEncoder(),
                tokenService = tokenService
        )
    }

    private fun createAccountFacade(accountRepository: AccountRepository, passwordEncoder: PasswordEncoder,
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

        return AccountFacade(
                createAccountService = createAccountService,
                loginService = loginService,
                accountRepository = accountRepository
        )
    }
}
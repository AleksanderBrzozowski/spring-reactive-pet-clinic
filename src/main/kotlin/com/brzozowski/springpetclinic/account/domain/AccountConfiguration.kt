package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author Aleksander Brzozowski
 */

@Configuration
class AccountConfiguration {

    fun accountFacade(accountRepository: AccountRepository, passwordEncoder: PasswordEncoder): AccountFacade {
        val createAccountService = CreateAccountService(
                accountRepository = accountRepository,
                passwordEncoder = passwordEncoder
        )
        return AccountFacade(createAccountService = createAccountService)
    }

    @Bean
    fun accountFacade(): AccountFacade {
        return accountFacade(InMemoryAccountRepository(), BCryptPasswordEncoder())
    }
}
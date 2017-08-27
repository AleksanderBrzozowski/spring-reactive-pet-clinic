package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.exc.EmptyCredentialsException
import com.brzozowski.springpetclinic.account.domain.create.exc.UsernameAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.EmailAddressAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.WrongEmailAddressPatternException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll
/**
 * @author Aleksander Brzozowski
 */

class AccountSpec extends Specification {
    
    AccountRepository accountRepository = new InMemoryAccountRepository()
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
    AccountFacade accountFacade = new AccountConfiguration()
            .accountFacade(accountRepository, passwordEncoder)
    
    def "it should add account when credentials are correct"() {
        given: "correct account credentials"
            def createAccountCredentials = CreateAccountSampleDataKt.correct()
        when: "create account"
            def createdAccount = accountFacade.createAccount(createAccountCredentials).block()
        then: "app has this account"
            def foundAccount = accountRepository.findById(createdAccount.id).block()
        then: "password is matching"
            passwordEncoder.matches(createAccountCredentials.password, foundAccount.password)
    }
    
    def "it should not add account when e-mail is already in use"() {
        given: "created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "create account with same email"
            accountFacade.createAccount(CreateAccountSampleDataKt.sameEmailAddress()).block()
        then:
            thrown(EmailAddressAlreadyInUseException)
    }
    
    def "it should not add account when username is already in use"() {
        given: "credentials with already created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "create account with same username"
            accountFacade.createAccount(CreateAccountSampleDataKt.sameUsername()).block()
        then:
            thrown(UsernameAlreadyInUseException)
        
    }
    
    @Unroll("it should not add account when #property is empty")
    def "it should not add account when one of credentials is empty"() {
        when:
            accountFacade.createAccount(account).block()
        then:
            thrown(EmptyCredentialsException)
        where:
            property    | account
            "firstName" | CreateAccountSampleDataKt.emptyFirstName()
            "lastName"  | CreateAccountSampleDataKt.emptyLastName()
            "email"     | CreateAccountSampleDataKt.emptyEmail()
            "password"  | CreateAccountSampleDataKt.emptyPassword()
            "username"  | CreateAccountSampleDataKt.emptyUsername()
    }
    
    def "it should not add account when email pattern is wrong"() {
        when: "create account with wrong email address pattern in credentials"
            accountFacade.createAccount(CreateAccountSampleDataKt.wrongEmailAddressPattern()).block()
        then:
            thrown(WrongEmailAddressPatternException)
    }
}

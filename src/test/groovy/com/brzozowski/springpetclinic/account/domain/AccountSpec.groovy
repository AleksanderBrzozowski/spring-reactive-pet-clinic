package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.account.domain.create.CreateAccountSampleDataKt
import com.brzozowski.springpetclinic.account.domain.create.exc.EmailAddressAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.EmptyCredentialsException
import com.brzozowski.springpetclinic.account.domain.create.exc.UsernameAlreadyInUseException
import com.brzozowski.springpetclinic.account.domain.create.exc.WrongEmailAddressPatternException
import com.brzozowski.springpetclinic.account.domain.login.LoginSampleDataKt
import com.brzozowski.springpetclinic.account.domain.login.exc.WrongCredentialsException
import spock.lang.Specification
import spock.lang.Unroll
/**
 * @author Aleksander Brzozowski
 */

class AccountSpec extends Specification {
    
    AccountFacade accountFacade = new AccountConfiguration().accountFacade()
    
    def "it should add account when credentials are correct"() {
        given: "correct account credentials"
            def credentials = CreateAccountSampleDataKt.correct()
        when: "create account"
            def createdAccount = accountFacade.createAccount(credentials).block()
        then: "app has this account"
            accountFacade.findById(createdAccount.id).block() == createdAccount
    }
    
    def "it should not add account when e-mail is already in use"() {
        given: "created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "create account with same email"
            accountFacade.createAccount(CreateAccountSampleDataKt.sameEmailAddress()).block()
        then:
            thrown(EmailAddressAlreadyInUseException)
    }
    
    def "it should not add account when e-mail address username ignore case is already in use"() {
        given: "created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "create account with same username, one letter capitalized"
            accountFacade.createAccount(CreateAccountSampleDataKt.sameEmailAddressUsernameOtherCase()).block()
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
            accountFacade.createAccount(credentials).block()
        then:
            thrown(EmptyCredentialsException)
        where:
            property    | credentials
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
    
    @Unroll("it should throw exc when login with wrong #property")
    def "it should throw exc when login with wrong credentials"() {
        given: "created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "login with wrong credential"
            accountFacade.login(credentials).block()
        then:
            thrown(WrongCredentialsException)
        where:
            property   | credentials
            "username" | LoginSampleDataKt.wrongUsername()
            "password" | LoginSampleDataKt.wrongPassword()
    }
    
    def "it should login with correct credentials"() {
        given: "created account"
            accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        when: "login with correct credentials"
            def token = accountFacade.login(LoginSampleDataKt.correct()).block()
        then:
            token != null
    }
}

package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.account.domain.AccountConfiguration
import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.account.domain.create.CreateAccountSampleDataKt
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.AccesDeniedException
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.SystemException
import com.brzozowski.springpetclinic.infrastructure.security.AccountPrincipal
import com.brzozowski.springpetclinic.pet.domain.exc.EmptyPetCredentials
import com.brzozowski.springpetclinic.security.AccountPrincipalSampleKt
import spock.lang.Specification
/**
 * @author Aleksander Brzozowski
 */
class PetSpec extends Specification {
    
    AccountFacade accountFacade = new AccountConfiguration().accountFacade()
    PetFacade petFacade = new PetConfiguration().petFacade(accountFacade)
    
    def "it should add pet for existing owner and correct pet credentials"() {
        given: "existing owner"
            AccountPrincipal owner = existingOwner()
        and: "correct pet credentials"
            def petCredentials = PetSampleDataKt.correct()
        when: "add pet"
            petFacade.addPet(petCredentials, owner).block()
        then:
            noExceptionThrown()
    }
    
    def "it should not add pet for not existing owner"() {
        given: "not existing owner"
            def owner = notExistingOwner()
        and: "correct pet credentials"
            def petCredentials = PetSampleDataKt.correct()
        when: "add pet"
            petFacade.addPet(petCredentials, owner).block()
        then:
            thrown(SystemException)
    }
    
    def "it should not add pet when pet name is empty"() {
        given: "existing owner"
            def owner = existingOwner()
        and: "pet credentials with empty name"
            def petCredentials = PetSampleDataKt.emptyPetName()
        when: "add pet"
            petFacade.addPet(petCredentials, owner)
        then:
            thrown(EmptyPetCredentials)
    }
    
    def "it should find pet if owner owns this pet"() {
        given: "existing owner"
            def owner = existingOwner()
        and: "existing pet for this owner"
            def petCredentials = PetSampleDataKt.correct()
            def pet = petFacade.addPet(petCredentials, owner).block()
        when: "find this pet"
            def foundPet = petFacade.findPet(pet.id, owner).block()
        then:
            foundPet == pet
    }
    
    def "it should throw exception if owner does not own found pet"() {
        given: "existing owner"
            def owner = existingOwner()
        and: "existing pet for this owner"
            def petCredentials = PetSampleDataKt.correct()
            def pet = petFacade.addPet(petCredentials, owner).block()
        and: "other owner that does not own this pet"
            def otherOwner = otherExistingOwner()
        when: "find this pet"
            petFacade.findPet(pet.id, otherOwner).block()
        then:
            thrown(AccesDeniedException)
    }
    
    def "it should throw exception when pet is not found"() {
        given: "existing owner"
            def owner = existingOwner()
        and: "not existing pet id"
            def petId = "not existing pet id"
        when: "find not existing pet"
            petFacade.findPet(petId, owner).block()
        then:
            thrown(SystemException)
    }
    
    private AccountPrincipal existingOwner() {
        return AccountPrincipalSampleKt.fromOwner(
                accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        )
    }
    
    private AccountPrincipal otherExistingOwner() {
        return AccountPrincipalSampleKt.fromOwner(
                accountFacade.createAccount(CreateAccountSampleDataKt.otherCorrect()).block()
        )
    }
    
    private static AccountPrincipal notExistingOwner() {
        return AccountPrincipalSampleKt.notExistingOwner()
    }
}

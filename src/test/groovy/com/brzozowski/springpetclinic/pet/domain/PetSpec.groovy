package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.account.domain.AccountConfiguration
import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.account.domain.create.CreateAccountSampleDataKt
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.SystemException
import com.brzozowski.springpetclinic.pet.domain.exc.EmptyPetCredentials
import spock.lang.Specification
/**
 * @author Aleksander Brzozowski
 */
class PetSpec extends Specification {
    
    AccountFacade accountFacade = new AccountConfiguration().accountFacade()
    PetFacade petFacade = new PetConfiguration().petFacade(accountFacade)
    
    def "it should add pet for existing owner and correct pet credentials"() {
        given: "existing owner"
            def owner = accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        and: "correct pet credentials"
            def petCredentials = PetSampleDataKt.correct(owner.id)
        when: "add pet"
            petFacade.addPet(petCredentials).block()
        then:
            noExceptionThrown()
    }
    
    def "it should not add pet for not existing owner"() {
        given: "pet credentials with not existing owner"
            def petCredentials = PetSampleDataKt.notExistingOwner()
        when: "add pet"
            petFacade.addPet(petCredentials).block()
        then:
            thrown(SystemException)
    }
    
    def "it should not add pet when pet name is empty"() {
        given: "existing owner"
            def owner = accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        and: "pet credentials with empty name"
            def petCredentials = PetSampleDataKt.emptyPetName(owner.id)
        when: "add pet"
            petFacade.addPet(petCredentials)
        then:
            thrown(EmptyPetCredentials)
    }
    
    def "it should find existing pet"() {
        given: "existing owner"
            def owner = accountFacade.createAccount(CreateAccountSampleDataKt.correct()).block()
        and: "existing pet"
            def pet = petFacade.addPet(PetSampleDataKt.correct(owner.id)).block()
        when: "find this pet"
            def foundPet = petFacade.findPet(pet.id).block()
        then:
            foundPet == pet
    }
    
    def "it should throw exception when pet is not found"() {
        given: "not existing pet id"
            def petId = "not existing pet id"
        when: "find not existing pet"
            petFacade.findPet(petId).block()
        then:
            thrown(SystemException)
    }
}

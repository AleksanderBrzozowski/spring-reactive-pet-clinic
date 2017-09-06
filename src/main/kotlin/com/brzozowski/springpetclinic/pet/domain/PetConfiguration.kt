package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.account.domain.AccountFacade
import org.springframework.context.annotation.Configuration

/**
 * @author Aleksander Brzozowski
 */

@Configuration
class PetConfiguration {

    fun petFacade(accountFacade: AccountFacade): PetFacade {
        return PetFacade(petRepository = InMemoryPetRepository(), accountFacade = accountFacade)
    }
}
package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfEmpty
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.AccesDeniedException
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.SystemException
import com.brzozowski.springpetclinic.infrastructure.security.AccountPrincipal
import com.brzozowski.springpetclinic.pet.domain.dto.AddPetDto
import com.brzozowski.springpetclinic.pet.domain.dto.PetDto
import com.brzozowski.springpetclinic.pet.domain.exc.EmptyPetCredentials
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
class PetFacade(private val petRepository: PetRepository,
                private val accountFacade: AccountFacade) {

    fun addPet(addPetDto: AddPetDto, owner: AccountPrincipal): Mono<PetDto> {
        if (addPetDto.name.isBlank()) {
            throw EmptyPetCredentials()
        }

        return accountFacade.findById(owner.id)
                .map { Pet(name = addPetDto.name, type = addPetDto.type, ownerId = it.id) }
                .flatMap { petRepository.save(it) }
                .map { it.toDto() }
    }

    fun findPet(id: String, owner: AccountPrincipal): Mono<PetDto> {
        return petRepository.findById(id)
                .errorIfEmpty { SystemException("Not found pet with id: $id") }
                .doOnNext { checkOwnerOwnsThisPet(it, owner) }
                .map { it.toDto() }
    }

    private fun checkOwnerOwnsThisPet(pet: Pet, owner: AccountPrincipal) {
        if (pet.ownerId != owner.id) {
            throw AccesDeniedException()
        }
    }
}
package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.infrastructure.extension.errorIfEmpty
import com.brzozowski.springpetclinic.infrastructure.presentation.exception.SystemException
import com.brzozowski.springpetclinic.pet.domain.dto.AddPetDto
import com.brzozowski.springpetclinic.pet.domain.dto.PetDto
import com.brzozowski.springpetclinic.pet.domain.exc.EmptyPetCredentials
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
class PetFacade(private val petRepository: PetRepository,
                private val accountFacade: AccountFacade) {

    fun addPet(addPetDto: AddPetDto): Mono<PetDto> {
        if (addPetDto.name.isBlank()) {
            throw EmptyPetCredentials()
        }

        return accountFacade.findById(addPetDto.ownerId)
                .map { Pet(name = addPetDto.name, type = addPetDto.type, ownerId = it.id) }
                .flatMap { petRepository.save(it) }
                .map { it.toDto() }
    }

    fun findPet(id: String): Mono<PetDto> {
        return petRepository.findById(id)
                .map { it.toDto() }
                .errorIfEmpty { SystemException("Not found pet with id: $id") }
    }
}
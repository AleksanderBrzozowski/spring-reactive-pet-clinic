package com.brzozowski.springpetclinic.pet.domain

import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
interface PetRepository {
    fun findById(id: String): Mono<Pet>
    fun save(pet: Pet): Mono<Pet>
}
package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.infrastructure.extension.reactiveFindByValue
import com.brzozowski.springpetclinic.infrastructure.extension.reactivePutAndGetActual
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Aleksander Brzozowski
 */
class InMemoryPetRepository: PetRepository {

    private val map = ConcurrentHashMap<String, Pet>()

    override fun findById(id: String): Mono<Pet> {
        return map.reactiveFindByValue { it.id == id }
    }

    override fun save(pet: Pet): Mono<Pet> {
        return map.reactivePutAndGetActual(pet.id, pet)
    }
}
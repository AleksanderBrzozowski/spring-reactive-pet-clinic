package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.infrastructure.extension.randomUUIDString
import com.brzozowski.springpetclinic.pet.domain.dto.PetDto

/**
 * @author Aleksander Brzozowski
 */

class Pet(
        val id: String = randomUUIDString(),
        val name: String,
        val type: Type,
        val ownerId: String
) {
    enum class Type {
        DOG, CAT
    }

    fun toDto() = PetDto(id = id, name = name, type = type)
}
package com.brzozowski.springpetclinic.pet.domain.dto

import com.brzozowski.springpetclinic.pet.domain.Pet

/**
 * @author Aleksander Brzozowski
 */
data class PetDto(
        val id: String,
        val name: String,
        val type: Pet.Type
)
package com.brzozowski.springpetclinic.pet.domain.dto

import com.brzozowski.springpetclinic.pet.domain.Pet

/**
 * @author Aleksander Brzozowski
 */
data class AddPetDto(
        val name: String,
        val type: Pet.Type
)
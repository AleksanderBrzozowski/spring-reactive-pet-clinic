package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.pet.domain.dto.AddPetDto

/**
 * @author Aleksander Brzozowski
 */

fun correct() = AddPetDto(name = "Max", type = Pet.Type.DOG)
fun emptyPetName() = correct().copy(name = "")


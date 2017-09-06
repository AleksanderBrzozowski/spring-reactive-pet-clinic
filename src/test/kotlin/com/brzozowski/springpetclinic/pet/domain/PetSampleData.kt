package com.brzozowski.springpetclinic.pet.domain

import com.brzozowski.springpetclinic.pet.domain.dto.AddPetDto

/**
 * @author Aleksander Brzozowski
 */

fun correct(ownerId: String) = AddPetDto(name = "Max", type = Pet.Type.DOG, ownerId = ownerId)
fun notExistingOwner() = correct("not existing id")
fun emptyPetName(ownerId: String) = correct(ownerId).copy(name = "")


package com.brzozowski.springpetclinic.pet.domain.exc

import com.brzozowski.springpetclinic.infrastructure.presentation.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class EmptyPetCredentials: BusinessException("domain.pet.error.emptyCredentials")
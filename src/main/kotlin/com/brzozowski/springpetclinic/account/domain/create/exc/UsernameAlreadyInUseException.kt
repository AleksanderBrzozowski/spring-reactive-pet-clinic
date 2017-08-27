package com.brzozowski.springpetclinic.account.domain.create.exc

import com.brzozowski.springpetclinic.infrastructure.presentation.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class UsernameAlreadyInUseException: BusinessException("domain.account.error.usernameAlreadyInUse")
package com.brzozowski.springpetclinic.account.domain.create.exc

import com.brzozowski.springpetclinic.infrastructure.presentation.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class EmailAddressAlreadyInUseException: BusinessException("domain.account.error.emailAlreadyInUse")
package com.brzozowski.springpetclinic.domain.account.create.exc

import com.brzozowski.springpetclinic.system.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class UsernameAlreadyInUseException: BusinessException("domain.account.error.usernameAlreadyInUse")
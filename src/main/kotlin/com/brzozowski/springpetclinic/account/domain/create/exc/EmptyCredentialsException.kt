package com.brzozowski.springpetclinic.domain.account.create.exc

import com.brzozowski.springpetclinic.system.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class EmptyCredentialsException: BusinessException("domain.account.error.emptyCredentials")
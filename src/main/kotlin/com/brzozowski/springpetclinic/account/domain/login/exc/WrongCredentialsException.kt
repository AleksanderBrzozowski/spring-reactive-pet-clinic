package com.brzozowski.springpetclinic.account.domain.login.exc

import com.brzozowski.springpetclinic.infrastructure.presentation.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class WrongCredentialsException: BusinessException("domain.account.error.wrongCredentials")
package com.brzozowski.springpetclinic.account.domain.create.exc

import com.brzozowski.springpetclinic.infrastructure.presentation.exception.BusinessException

/**
 * @author Aleksander Brzozowski
 */
class EmptyAccountCredentialsException : BusinessException("domain.account.error.emptyCredentials")
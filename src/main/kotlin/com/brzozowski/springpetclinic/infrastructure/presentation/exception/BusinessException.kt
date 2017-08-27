package com.brzozowski.springpetclinic.infrastructure.presentation.exception

/**
 * @author Aleksander Brzozowski
 */
open class BusinessException(val messageCode: String): RuntimeException(messageCode)
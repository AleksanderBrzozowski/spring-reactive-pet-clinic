package com.brzozowski.springpetclinic.infrastructure.exception

/**
 * @author Aleksander Brzozowski
 */
open class BusinessException(val messageCode: String): RuntimeException(messageCode)
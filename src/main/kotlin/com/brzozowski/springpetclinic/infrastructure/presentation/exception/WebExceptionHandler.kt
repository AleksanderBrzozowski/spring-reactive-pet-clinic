package com.brzozowski.springpetclinic.infrastructure.presentation.exception

import com.brzozowski.springpetclinic.infrastructure.extension.getDefaultMessage
import com.brzozowski.springpetclinic.infrastructure.security.UnauthorizedException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Aleksander Brzozowski
 */

@RestControllerAdvice
class WebExceptionHandler(private val messageSource: MessageSource) {

    @ExceptionHandler
    fun businessException(exc: BusinessException): ResponseEntity<ApiError> {
        val message = messageSource.getDefaultMessage(exc.messageCode)
        val apiError = ApiError(message = message, type = ExceptionType.BUSINESS)
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun systemException(exc: SystemException): ResponseEntity<ApiError> {
        val message = messageSource.getDefaultMessage("system.error")
        val apiError = ApiError(message = message, type = ExceptionType.SYSTEM)
        return ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    fun unauthorizedException(exc: UnauthorizedException): ResponseEntity<ApiError> {
        val message = messageSource.getDefaultMessage("system.unauthorized.error")
        val apiError = ApiError(message = message, type = ExceptionType.SECURITY)
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }
}
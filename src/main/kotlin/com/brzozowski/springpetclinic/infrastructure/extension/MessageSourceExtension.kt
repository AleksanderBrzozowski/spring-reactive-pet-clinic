package com.brzozowski.springpetclinic.infrastructure.extension

import org.springframework.context.MessageSource
import java.util.*

/**
 * @author Aleksander Brzozowski
 */

fun MessageSource.getDefaultMessage(messageCode: String, args: Array<Any> = emptyArray()): String {
    return this.getMessage(messageCode, args, Locale.getDefault())
}
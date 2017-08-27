package com.brzozowski.springpetclinic.infrastructure.extension

import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */

fun <T> Mono<T>.errorIfEmpty(block: () -> Throwable): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) this else Mono.error(block()) }
}

fun <T> Mono<T>.errorIfNotEmpty(block: (T) -> Throwable): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) Mono.error(block.invoke(this.block()!!)) else this }
}

fun <T> Mono<T>.switchIfEmpty(block: () -> Mono<T>): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) this else block() }
}
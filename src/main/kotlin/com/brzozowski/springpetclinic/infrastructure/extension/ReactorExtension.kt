package com.brzozowski.springpetclinic.infrastructure.extension

import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */

inline fun <T> Mono<T>.errorIfEmpty(crossinline onError: () -> Throwable): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) this else Mono.error(onError()) }
}

inline fun <T> Mono<T>.errorIfNotEmpty(crossinline onError: (T) -> Throwable): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) Mono.error(onError.invoke(this.block()!!)) else this }
}

inline fun <T> Mono<T>.switchIfEmpty(crossinline default: () -> Mono<T>): Mono<T> {
    return this.hasElement()
            .flatMap { if (it) this else default() }
}

inline fun <T> Mono<T>.errorIf(crossinline predicate: (T) -> Boolean, crossinline throwable: (T) -> Throwable): Mono<T> {
    return this.flatMap { if (predicate(it)) Mono.error(throwable(it)) else Mono.just(it) }
}

inline fun <T> Mono<T>.errorIfNot(crossinline predicate: (T) -> Boolean, crossinline throwable: (T) -> Throwable): Mono<T> {
    return this.errorIf(predicate = { !predicate(it) }, throwable = throwable)
}
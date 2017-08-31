package com.brzozowski.springpetclinic.infrastructure.extension

import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.IllegalArgumentException

/**
 * @author Aleksander Brzozowski
 */

fun <K, V> Map<K, V>.reactiveFindByValue(predicate: (V) -> Boolean): Mono<V> {
    val filteredMap = this.filterValues { predicate.invoke(it) }
    if (filteredMap.isEmpty()) {
        return Mono.empty()
    }else if (filteredMap.size > 1) {
        throw IllegalArgumentException("Map contains more than one element matching given predicate")
    } else {
        return filteredMap.entries.iterator().next().value.toMono()
    }
}

fun <K, V> MutableMap<K, V>.reactivePutAndGetActual(key: K, value: V): Mono<V> {
    return Mono.fromCallable{
        this.put(key, value)
        this[key]
    }
}
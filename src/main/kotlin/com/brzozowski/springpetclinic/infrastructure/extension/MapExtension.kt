package com.brzozowski.springpetclinic.extension

import java.lang.IllegalArgumentException

/**
 * @author Aleksander Brzozowski
 */

fun <K, V> Map<K, V>.findByValue(predicate: (V) -> Boolean): V? {
    val filteredMap = this.filterValues { predicate.invoke(it) }
    if (filteredMap.isEmpty()) {
        return null
    }else if (filteredMap.size > 1) {
        throw IllegalArgumentException("Map contains more than one element matching given predicate")
    } else {
        return filteredMap.entries.iterator().next().value
    }
}
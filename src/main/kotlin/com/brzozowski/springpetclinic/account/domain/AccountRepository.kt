package com.brzozowski.springpetclinic.domain.account

import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
interface AccountRepository {
    fun save(account: Account): Mono<Account>
    fun findByEmail(email: String): Mono<Account>
    fun findByUsername(username: String): Mono<Account>
    fun findById(id: String): Mono<Account>
}
package com.brzozowski.springpetclinic.account.domain

import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */
interface AccountRepository {
    fun save(account: Account): Mono<Account>
    fun findByEmailStartsWithIgnoreCase(emailUsername: String): Mono<Account>
    fun findByUsername(username: String): Mono<Account>
    fun findById(id: String): Mono<Account>
}
package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.infrastructure.extension.reactiveFindByValue
import com.brzozowski.springpetclinic.infrastructure.extension.reactivePutAndGetActual
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Aleksander Brzozowski
 */
class InMemoryAccountRepository : AccountRepository {

    val map = ConcurrentHashMap<String, Account>()

    override fun save(account: Account): Mono<Account> {
        return map.reactivePutAndGetActual(account.id, account)
    }

    override fun findByEmailStartsWithIgnoreCase(emailUsername: String): Mono<Account> {
        return map.reactiveFindByValue { it.email.startsWith(emailUsername, ignoreCase = true) }
    }

    override fun findByUsername(username: String): Mono<Account> {
        return map.reactiveFindByValue { it.username == username }
    }

    override fun findById(id: String): Mono<Account> {
        return map.reactiveFindByValue { it.id == id }
    }

    private fun find(predicate: (Account) -> Boolean): Mono<Account> {
        return map.reactiveFindByValue(predicate)
    }
}
package com.brzozowski.springpetclinic.account.domain

import com.brzozowski.springpetclinic.infrastructure.extension.findByValue
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Aleksander Brzozowski
 */
class InMemoryAccountRepository : AccountRepository {

    val map = ConcurrentHashMap<String, Account>()

    override fun save(account: Account): Mono<Account> {
        return Mono.fromCallable {
            map.put(account.id, account)
            val account1 = map[account.id]
            account1
        }
    }

    override fun findByEmail(email: String): Mono<Account> {
        return find { it.email == email }
    }

    override fun findByUsername(username: String): Mono<Account> {
        return find { it.username == username }
    }

    override fun findById(id: String): Mono<Account> {
        return find { it.id == id }
    }

    private fun find(predicate: (Account) -> Boolean): Mono<Account> {
        return Mono.defer {
            map.findByValue(predicate)
                    ?.toMono()
                    ?: Mono.empty()
        }
    }
}
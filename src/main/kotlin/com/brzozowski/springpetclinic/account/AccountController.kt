package com.brzozowski.springpetclinic.account

import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.account.domain.dto.CreateAccountDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI

/**
 * @author Aleksander Brzozowski
 */

@Component
class AccountHandler(private val accountFacade: AccountFacade) {

    fun createAccount(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono<CreateAccountDto>()
                .flatMap { accountFacade.createAccount(it) }
                .flatMap { ServerResponse.created(URI.create("foo")).build() }
    }
}
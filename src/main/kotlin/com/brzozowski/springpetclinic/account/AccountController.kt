package com.brzozowski.springpetclinic.account

import com.brzozowski.springpetclinic.account.domain.AccountFacade
import com.brzozowski.springpetclinic.account.domain.dto.CreateAccountDto
import com.brzozowski.springpetclinic.infrastructure.presentation.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */

@RestController
@RequestMapping("${Api.BASE_URL}/account")
class AccountController(private val accountFacade: AccountFacade) {

    @PostMapping("/create")
    fun createAccount(@RequestBody createAccountDto: CreateAccountDto): Mono<ResponseEntity<Void>> {
        return accountFacade.createAccount(createAccountDto)
                .map { ResponseEntity<Void>(HttpStatus.CREATED) }
    }
}
package com.brzozowski.springpetclinic.pet

import com.brzozowski.springpetclinic.infrastructure.presentation.Api
import com.brzozowski.springpetclinic.infrastructure.security.token.TokenService
import com.brzozowski.springpetclinic.pet.domain.PetFacade
import com.brzozowski.springpetclinic.pet.domain.dto.AddPetDto
import com.brzozowski.springpetclinic.pet.domain.dto.PetDto
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 * @author Aleksander Brzozowski
 */

@RestController
@RequestMapping("${Api.BASE_URL}/pet")
class PetController(private val petFacade: PetFacade,
                    private val tokenService: TokenService) {

    @PostMapping("/add-pet")
    fun addPet(@RequestBody addPetDto: AddPetDto,
               @RequestHeader(HttpHeaders.AUTHORIZATION) token: String): Mono<PetDto> {
        return Mono.fromCallable { tokenService.validateToken(token) }
                .flatMap { petFacade.addPet(addPetDto = addPetDto, owner = it) }
    }

    @GetMapping("/{id}")
    fun getPet(@PathVariable petId: String,
               @RequestHeader(HttpHeaders.AUTHORIZATION) token: String): Mono<PetDto> {
        return Mono.fromCallable { tokenService.validateToken(token) }
                .flatMap { petFacade.findPet(petId, it) }
    }
}
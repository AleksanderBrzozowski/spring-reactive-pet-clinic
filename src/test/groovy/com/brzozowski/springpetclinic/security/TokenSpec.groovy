package com.brzozowski.springpetclinic.security

import com.brzozowski.springpetclinic.infrastructure.security.UnauthorizedException
import com.brzozowski.springpetclinic.infrastructure.security.token.TokenConfiguration
import com.brzozowski.springpetclinic.infrastructure.security.token.TokenService
import spock.lang.Specification

/**
 * @author Aleksander Brzozowski
 */
class TokenSpec extends Specification{
    
    TokenService tokenService = new TokenConfiguration().tokenService()
    
    def "it should generate token for account id and return that account id for this token"() {
        given: "account id"
            def accountId = "1"
        when: "generate token"
            def token = tokenService.generateToken(accountId)
        and: "account id from token"
            def accountIdFromToken = tokenService.validateToken(token).id
        then: "these ids are same"
            accountId == accountIdFromToken
    }
    
    def "it should throw exception when token is wrong"() {
        given: "wrong token"
            def wrongToken = "wrong token"
        when:
            tokenService.validateToken(wrongToken)
        then:
            thrown(UnauthorizedException)
    }
}

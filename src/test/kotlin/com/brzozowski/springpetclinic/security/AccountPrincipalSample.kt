package com.brzozowski.springpetclinic.security

import com.brzozowski.springpetclinic.account.domain.dto.AccountDto
import com.brzozowski.springpetclinic.infrastructure.security.AccountPrincipal

/**
 * @author Aleksander Brzozowski
 */

fun fromOwner(owner: AccountDto) = AccountPrincipal(owner.id)
fun notExistingOwner() = AccountPrincipal("not existing id")
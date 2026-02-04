package com.example.inventoryconfig.security

import com.example.inventoryconfig.exception.UnauthorizedActionException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class CurrentUserProvider {
    fun getUserId(): String {
        val auth = SecurityContextHolder.getContext().authentication
        val jwt = auth?.principal
        if (jwt is Jwt) {
            val userId = jwt.getClaimAsString("sub")
            if (!userId.isNullOrBlank()) {
                return userId
            }
        }
        throw UnauthorizedActionException("userId no encontrado en el token")
    }
}

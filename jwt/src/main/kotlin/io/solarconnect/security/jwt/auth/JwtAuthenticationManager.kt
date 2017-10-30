package io.solarconnect.security.jwt.auth

import io.solarconnect.security.core.auth.FindUserAuthorityService
import io.solarconnect.security.core.exception.NoAuthorityException
import io.solarconnect.security.core.exception.NoRoleException
import io.solarconnect.security.jwt.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class JwtAuthenticationManager : AuthenticationManager {

    private val signingKey: String
    private val findUserAuthorityService: FindUserAuthorityService

    constructor(signingKey: String, findUserAuthorityService: FindUserAuthorityService) {
        this.signingKey = signingKey
        this.findUserAuthorityService = findUserAuthorityService
    }

    override fun authenticate(authentication: Authentication): Authentication {
        //JwtPreAuthenticationToken
        if (authentication is JwtAuthenticationToken) {
            return authentication
        }
        val claims = JwtUtil.getBody(signingKey, authentication.credentials.toString())
        if (claims.authorities == null) {
            throw NoAuthorityException("No authority for service..")
        }
        return JwtAuthenticationToken(claims, claims.authorities)
    }

}
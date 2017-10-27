package io.solarconnect.security.jwt.auth

import io.solarconnect.security.core.auth.FindUserAuthorityService
import io.solarconnect.security.jwt.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

class JwtAuthenticationManager : AuthenticationManager {

    private val signingKey: String
    private val findUserAuthorityService: FindUserAuthorityService

    constructor(signingKey: String, findUserAuthorityService: FindUserAuthorityService) {
        this.signingKey = signingKey
        this.findUserAuthorityService = findUserAuthorityService
    }

    override fun authenticate(authentication: Authentication): Authentication {
        if (authentication is JwtAuthenticationToken) {
            return authentication
        }
        val claims = JwtUtil.getBody(signingKey, authentication.credentials.toString())
        if (claims.u() == null) {
            throw NoRoleAuthenticationException("No roles for service..")
        }
        val authorities = findUserAuthorityService.findGrantedAuthority(claims.getUserRoles().toArray(arrayOf<String>()))
        return SBJwtAuthenticationToken(claims, authorities)
    }

}
package io.solarconnect.security.jwt.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtPreAuthenticationToken : AbstractAuthenticationToken {

    private val principal: Any?
    private val credentials: String

    constructor(credentials: String) : super(null){
        this.principal = null
        this.credentials = credentials
    }
    override fun getCredentials(): Any {
        return credentials
    }

    override fun getPrincipal():  Any? {
        return principal
    }

}
package io.solarconnect.security.jwt.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken : AbstractAuthenticationToken {

    private val principal: JwtUser

    constructor(principal: JwtUser, authorities : Collection<GrantedAuthority>) : super(authorities){
        this.principal = principal
    }
    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return principal
    }

}
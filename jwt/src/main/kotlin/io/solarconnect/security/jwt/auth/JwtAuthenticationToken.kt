package io.solarconnect.security.jwt.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken<USER_ID> : AbstractAuthenticationToken {

	private val principal: JwtUser<USER_ID>

	constructor(principal: JwtUser<USER_ID>, authorities: Collection<GrantedAuthority>) : super(authorities) {
		this.principal = principal
	}

	override fun getCredentials(): Any? {
		return null
	}

	override fun getPrincipal(): Any {
		return principal
	}

}
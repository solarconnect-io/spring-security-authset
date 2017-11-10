package io.solarconnect.security.jwt.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

class JwtAuthenticationProvider<USER_ID, JWT_USER : JwtUser<USER_ID>> : AuthenticationProvider {

	private val authenticationManager: JwtAuthenticationManager<USER_ID, JWT_USER>

	constructor(authenticationManager: JwtAuthenticationManager<USER_ID, JWT_USER>) {
		this.authenticationManager = authenticationManager
	}

	override fun authenticate(authentication: Authentication): Authentication {
		return authenticationManager.authenticate(authentication)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return JwtAuthenticationToken::class.java!!.isAssignableFrom(authentication)
	}
}
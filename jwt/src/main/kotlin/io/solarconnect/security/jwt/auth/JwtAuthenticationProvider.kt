package io.solarconnect.security.jwt.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class JwtAuthenticationProvider : AuthenticationProvider {

	private val authenticationManager: JwtAuthenticationManager

	constructor(authenticationManager: JwtAuthenticationManager) {
		this.authenticationManager = authenticationManager
	}

	override fun authenticate(authentication: Authentication): Authentication {
		return authenticationManager.authenticate(authentication)
	}

	override fun supports(authentication: Class<*>): Boolean {
		return JwtAuthenticationToken::class.java!!.isAssignableFrom(authentication)
	}
}
package io.solarconnect.security.jwt.auth

import io.solarconnect.security.core.exception.NoAuthorityException
import io.solarconnect.security.jwt.custom.JwtHandler
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

class JwtAuthenticationManager<USER_ID, in JWT_USER : JwtUser<USER_ID>> : AuthenticationManager {

	private val signingKey: String
	private val jwtHandler: JwtHandler<USER_ID, JWT_USER>
	constructor(signingKey: String, jwtHandler: JwtHandler<USER_ID, JWT_USER>) {
		this.signingKey = signingKey
		this.jwtHandler = jwtHandler
	}

	override fun authenticate(authentication: Authentication): Authentication {
		//JwtPreAuthenticationToken
		if (authentication is JwtAuthenticationToken<*>) {
			return authentication
		}
		var jwtUser = jwtHandler.generateUser(signingKey, authentication.credentials.toString())
		if (jwtUser.authorities == null) {
			throw NoAuthorityException("No authority for service..")
		}
		return JwtAuthenticationToken(jwtUser, jwtUser.authorities)
	}

}
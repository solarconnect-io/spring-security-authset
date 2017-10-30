package io.solarconnect.security.jwt.auth

import io.jsonwebtoken.JwtHandler
import io.solarconnect.security.core.auth.FindUserAuthorityService
import io.solarconnect.security.core.exception.NoAuthorityException
import io.solarconnect.security.jwt.util.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

class JwtAuthenticationManager<JWT_USER : JwtUser> : AuthenticationManager {

	private val signingKey: String
	private val findUserAuthorityService: FindUserAuthorityService
	private val jwtHandler: JwtHandler<JWT_USER>

	constructor(signingKey: String, findUserAuthorityService: FindUserAuthorityService, jwtHandler: JwtHandler<JWT_USER>) {
		this.signingKey = signingKey
		this.findUserAuthorityService = findUserAuthorityService
		this.jwtHandler = jwtHandler
	}

	override fun authenticate(authentication: Authentication): Authentication {
		//JwtPreAuthenticationToken
		if (authentication is JwtAuthenticationToken) {
			return authentication
		}
		var jwtUser = JwtUtil.getBody(signingKey, jwtHandler, authentication.credentials.toString())
		if (jwtUser.authorities == null) {
			throw NoAuthorityException("No authority for service..")
		}
		return JwtAuthenticationToken(jwtUser, jwtUser.authorities)
	}

}
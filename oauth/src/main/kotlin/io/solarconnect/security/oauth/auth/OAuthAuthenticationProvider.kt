package io.solarconnect.security.oauth.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class OAuthAuthenticationProvider<USER_ID>(private val authenticationManager: OAuthAuthenticationManager<USER_ID>) : AuthenticationProvider {

	override fun authenticate(authentication: Authentication): Authentication {
		return authenticationManager.authenticate(authentication)
	}

	override fun supports(authentication: Class<*>?): Boolean {
		return OAuthAuthenticationToken::class.java.isAssignableFrom(authentication)
	}

}
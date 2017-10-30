package io.solarconnect.security.oauth.auth

import io.solarconnect.security.oauth.dto.OAuthToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class OAuthAuthenticationProvider(val authenticationManager: OAuthAuthenticationManager) : AuthenticationProvider {

	override fun authenticate(authentication: Authentication): Authentication {
		return authenticationManager.authenticate(authentication)
	}

	override fun supports(authentication: Class<*>?): Boolean {
		return OAuthToken::class.java!!.isAssignableFrom(authentication)
	}

}
package io.solarconnect.security.oauth.auth

import io.solarconnect.security.core.auth.FindUserAuthorityService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class OAuthAuthenticationManager<USER_ID> : AuthenticationManager {

	private val signingKey: String
	private val findUserAuthorityService: FindUserAuthorityService<USER_ID>

	constructor(signingKey: String, findUserAuthorityService: FindUserAuthorityService<USER_ID>) {
		this.signingKey = signingKey
		this.findUserAuthorityService = findUserAuthorityService
	}

	override fun authenticate(authentication: Authentication): Authentication {
		//JwtPreAuthenticationToken
		if (authentication is OAuthAuthenticationToken) {
			return authentication
		}
		var principal: OAuthUser = authentication.principal as OAuthUser
		var authorities: Collection<GrantedAuthority> = authentication.authorities
		return OAuthAuthenticationToken(principal, authorities)
	}

}
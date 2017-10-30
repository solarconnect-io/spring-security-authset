package io.solarconnect.security.jwt.filter

import io.solarconnect.security.core.auth.FindUserAuthorityService
import io.solarconnect.security.jwt.auth.JwtUser
import io.solarconnect.security.jwt.util.JwtCookieUtil
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class JwtSsoDefaultHandler : JwtSsoHandler {

	private val serviceName: String
	private val signingKey: String
	private val findUserAuthorityService: FindUserAuthorityService


	constructor(serviceName: String, signingKey: String, jwtUserService: FindUserAuthorityService) {
		this.serviceName = serviceName
		this.signingKey = signingKey
		this.findUserAuthorityService = jwtUserService
	}

	override fun postProcessing(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
		val claims = authentication.principal as JwtUser

		for (domain in findUserAuthorityService.findUserComponent(claims.getUserId())) {
			JwtCookieUtil.tokenToCookie(response, domain, serviceName, signingKey, claims)
		}
	}

}
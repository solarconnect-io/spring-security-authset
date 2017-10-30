package io.solarconnect.security.direct.filter

import io.solarconnect.security.core.auth.SsoAuthSuccessHandler
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.util.Assert
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class DirectAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

	lateinit var serviceName: String
	lateinit var signingKey: String

	lateinit var ssoHandlers: Collection<SsoAuthSuccessHandler>

	override fun afterPropertiesSet() {
		super.afterPropertiesSet()
		Assert.notNull(serviceName, "serviceName must be specified")
		Assert.notNull(signingKey, "signingKey must be specified")
	}

	override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
		super.successfulAuthentication(request, response, chain, authResult)

		for (handler in ssoHandlers){
			handler.postProcessing(request, response, authResult)
		}
	}

}
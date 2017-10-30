package io.solarconnect.security.jwt.filter

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
interface JwtSsoHandler {

	abstract fun postProcessing(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication)
}
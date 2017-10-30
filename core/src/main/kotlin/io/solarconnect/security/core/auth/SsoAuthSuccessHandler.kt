package io.solarconnect.security.core.auth

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
interface SsoAuthSuccessHandler {
	fun postProcessing(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication)
}
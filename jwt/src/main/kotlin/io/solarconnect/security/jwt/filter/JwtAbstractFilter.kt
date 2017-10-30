package io.solarconnect.security.jwt.filter

import io.solarconnect.security.jwt.auth.JwtAuthenticationManager
import io.solarconnect.security.jwt.auth.JwtUser
import lombok.Setter
import org.scriptonbasestar.tool.core.exception.compiletime.SBTextExtractException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.Assert
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
open abstract class JwtAbstractFilter : OncePerRequestFilter() {

	@Setter
	private val authenticationManager: JwtAuthenticationManager? = null

	//not null
	@Setter
	protected var serviceName: String? = null
	//not null
	@Setter
	protected var signingKey: String? = null

//	@Setter
//	protected SBJwtSsoHandler sbJwtSsoHandler;

	@Setter
	protected var successHandler: AuthenticationSuccessHandler? = null
	@Setter
	protected var failureHandler: AuthenticationFailureHandler? = null

	@Throws(ServletException::class)
	override fun initFilterBean() {
		super.initFilterBean()
		try {
			Assert.notNull(authenticationManager, "authenticationManager must not null")
			Assert.notNull(serviceName, "serviceName must not null")
			Assert.notNull(signingKey, "signingKey must not null")
		} catch (e: IllegalArgumentException) {
			throw ServletException(e.message)
		}

	}

	@Throws(ServletException::class, IOException::class)
	override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
		var token: String? = null
		try {
			token = extractTokenString(request, response)
		} catch (e: SBTextExtractException) {
			//			e.printStackTrace();
			filterChain.doFilter(request, response)
			return
		}

		val authResult: Authentication
		try {
			authResult = authenticationManager!!.authenticate(JwtPreAuthenticateToken(token))
		} catch (failed: InternalAuthenticationServiceException) {
			logger.error("An internal error occurred while trying to authenticate the user.", failed)
			unsuccessfulAuthentication(request, response, failed)
			filterChain.doFilter(request, response)
			return
		} catch (failed: AuthenticationException) {
			unsuccessfulAuthentication(request, response, failed)
			filterChain.doFilter(request, response)
			return
		}

		successfulAuthentication(request, response, authResult)
		filterChain.doFilter(request, response)
	}

	@Throws(SBTextExtractException::class)
	protected abstract fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String


	@Throws(IOException::class, ServletException::class)
	protected fun unsuccessfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
											 failed: AuthenticationException) {

		SecurityContextHolder.clearContext()
		if (logger.isDebugEnabled) {
			logger.debug("Authentication request failed: " + failed.toString(), failed)
			logger.debug("Updated SecurityContextHolder to contain null Authentication")
			//			logger.debug("Delegating to authentication failure handler " + failureHandler);
		}
		//failed handler
		if (failureHandler != null) {
			failureHandler!!.onAuthenticationFailure(request, response, failed)
		}
	}

	@Throws(IOException::class, ServletException::class)
	protected fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
										   authResult: Authentication) {

		if (logger.isDebugEnabled) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult)
		}
		val user = authResult.principal as JwtUser

		request.setAttribute(JwtUser.USER_ID, user.getUserId())
		request.setAttribute(JwtUser.USER_USERNAME, user.getUsername())
		request.setAttribute(JwtUser.USER_NICKNAME, user.getNickname())
		request.setAttribute(JwtUser.USER_ROLE, user.getUserRoles())

		SecurityContextHolder.getContext().authentication = authResult

		//		if(sbJwtSsoHandler != null){
		//			sbJwtSsoHandler.postProcessing(request, response, authResult);
		//		}

		//success handler
		if (successHandler != null) {
			successHandler!!.onAuthenticationSuccess(request, response, authResult)
		}
	}
}
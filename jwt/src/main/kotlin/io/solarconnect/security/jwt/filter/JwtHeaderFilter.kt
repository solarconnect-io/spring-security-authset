package io.solarconnect.security.jwt.filter

import io.solarconnect.security.core.exception.JwtTextExtractException
import io.solarconnect.security.jwt.auth.JwtUser
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class JwtHeaderFilter<USER_ID, JWT_USER : JwtUser<USER_ID>> : JwtAbstractFilter<USER_ID, JWT_USER>() {
	override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
		val authHeader = request.getHeader("Authorization") ?: throw JwtTextExtractException("인증 헤더가 없습니다.")
		if (!authHeader.matches("^(?i)Bearer\\s+.+".toRegex())) {
			throw JwtTextExtractException("JWT 인증 헤더가 아닙니다.")
		}
		return authHeader.split("(?i)Bearer\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
	}
}

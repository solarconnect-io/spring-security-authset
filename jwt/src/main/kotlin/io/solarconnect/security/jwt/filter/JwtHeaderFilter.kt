package io.solarconnect.security.jwt.filter

import org.scriptonbasestar.tool.core.exception.compiletime.SBTextExtractException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class JwtHeaderFilter : JwtAbstractFilter() {
	override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
		val authHeader = request.getHeader("Authorization") ?: throw SBTextExtractException("인증 헤더가 없습니다.")
		if (!authHeader.matches("^(?i)Bearer\\s+.+".toRegex())) {
			throw SBTextExtractException("JWT 인증 헤더가 아닙니다.")
		}
		return authHeader.split("(?i)Bearer\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
	}

}
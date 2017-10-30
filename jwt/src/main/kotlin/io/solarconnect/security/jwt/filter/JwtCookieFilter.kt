package io.solarconnect.security.jwt.filter

import io.solarconnect.security.jwt.util.JwtCookieUtil
import org.scriptonbasestar.tool.core.exception.compiletime.SBTextExtractException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class JwtCookieFilter : JwtAbstractFilter() {
	override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
		return JwtCookieUtil.tokenFromCookie(request, serviceName, signingKey) ?: throw SBTextExtractException("쿠키가 존재하지 않습니다.")
	}

}
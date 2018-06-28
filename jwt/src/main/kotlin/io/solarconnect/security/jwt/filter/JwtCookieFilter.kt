package io.solarconnect.security.jwt.filter

import io.solarconnect.security.core.exception.JwtTextExtractException
import io.solarconnect.security.jwt.auth.JwtUser
import io.solarconnect.security.jwt.util.JwtCookieUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class JwtCookieFilter<USER_ID, JWT_USER : JwtUser<USER_ID>> : JwtAbstractFilter<USER_ID, JWT_USER>() {
	override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
		return JwtCookieUtil.tokenFromCookie(request, serviceName, signingKey) ?: throw JwtTextExtractException("쿠키가 존재하지 않습니다.")
	}
}

package io.solarconnect.security.jwt.util

import io.jsonwebtoken.Claims
import io.solarconnect.security.jwt.auth.JwtUser
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
object JwtCookieUtil {

	fun tokenToCookie(response: HttpServletResponse, domain: String, serviceName: String, signingKey: String, claims: Claims) {
		val cookie = Cookie(serviceName, JwtUtil.generateToken(signingKey, claims))
		cookie.isHttpOnly = false
		cookie.secure = false
		cookie.domain = domain
		cookie.path = "/"
		//다음서버로 전송되기까지의 시간만
		cookie.maxAge = 30000 * 1000
		//		cookie.setMaxAge(30*1000);
		response.addCookie(cookie)
	}

//	fun claimFromCookie(request: HttpServletRequest, serviceName: String, signingKey: String): JwtUser? {
//		if (request.cookies == null) {
//			return null
//		}
//		for (cookie in request.cookies) {
//			if (cookie.name == serviceName) {
//				return JwtUtil.getBody(signingKey, cookie.value)
//			}
//		}
//		return null
//	}

	fun tokenFromCookie(request: HttpServletRequest, serviceName: String, signingKey: String): String? {
		if (request.cookies == null) {
			return null
		}
		for (cookie in request.cookies) {
			if (cookie.name == serviceName) {
				return cookie.value
			}
		}
		return null
	}
}
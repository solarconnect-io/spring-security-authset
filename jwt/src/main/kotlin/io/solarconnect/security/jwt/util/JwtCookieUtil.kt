package io.solarconnect.security.jwt.util

import com.auth0.jwt.interfaces.Claim
import io.solarconnect.security.jwt.auth.JwtUser
import io.solarconnect.security.jwt.custom.JwtHandler
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
object JwtCookieUtil {

	fun <JWT_ID, JWT_USER : JwtUser<JWT_ID>>tokenToCookie(response: HttpServletResponse, domain: String, serviceName: String, signingKey: String, claim: Claim, jwtHandler: JwtHandler<JWT_ID, JWT_USER>) {
		val cookie = Cookie(serviceName, jwtHandler.generateToken(signingKey, claim))
		cookie.isHttpOnly = false
		cookie.secure = false
		cookie.domain = domain
		cookie.path = "/"
		//다음서버로 전송되기까지의 시간만
		cookie.maxAge = 30000 * 1000
		//		cookie.setMaxAge(30*1000);
		response.addCookie(cookie)
	}

	fun <JWT_ID, JWT_USER : JwtUser<JWT_ID>>claimFromCookie(request: HttpServletRequest, serviceName: String, signingKey: String, jwtHandler: JwtHandler<JWT_ID, JWT_USER>): JWT_USER? {
		if (request.cookies == null) {
			return null
		}
		for (cookie in request.cookies) {
			if (cookie.name == serviceName) {
				return jwtHandler.generateUser(signingKey, cookie.value)
			}
		}
//		request.cookies
//				.asSequence()
//				.filter { it.name == serviceName }
//				.forEach { return jwtHandler.generateUser(signingKey, it.value) }
		return null
	}

	fun tokenFromCookie(request: HttpServletRequest, serviceName: String, signingKey: String): String? {
		if (request.cookies == null) {
			return null
		}
//		for (cookie in request.cookies) {
//			if (cookie.name == serviceName) {
//				return cookie.value
//			}
//		}
//		return null
		return request.cookies
				.firstOrNull { it.name == serviceName }
				?.value
	}
}
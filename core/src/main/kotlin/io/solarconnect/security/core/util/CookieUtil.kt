package io.solarconnect.security.core.util

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author chaeeung.e
 * @since 2017-11-10
 */
object CookieUtil {

	fun toCookie(response: HttpServletResponse, domain: String, serviceName: String, value:String) {
		val cookie = Cookie(serviceName, value)
		cookie.isHttpOnly = false
		cookie.secure = false
		cookie.domain = domain
		cookie.path = "/"
		//다음서버로 전송되기까지의 시간만
		cookie.maxAge = 30000 * 1000
		//		cookie.setMaxAge(30*1000);
		response.addCookie(cookie)
	}

	fun fromCookie(request: HttpServletRequest, serviceName: String): String? {
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

	fun add(request: HttpServletRequest, response: HttpServletResponse, cookieName: String, cookieDomain: String?, cookieValue: String, maxAge: Int, secureCookie: Boolean) {
		val cookie = Cookie(cookieName, cookieValue)
		cookie.maxAge = maxAge
		cookie.path = getCookiePath(request)
		if (cookieDomain != null) {
			cookie.domain = cookieDomain
		}
		if (maxAge < 1) {
			cookie.version = 1
		}
		cookie.secure = secureCookie
		response.addCookie(cookie)
	}

	fun cancel(request: HttpServletRequest, response: HttpServletResponse, cookieName: String, cookieDomain: String?) {
		val cookie = Cookie(cookieName, null)
		cookie.maxAge = 0
		cookie.path = getCookiePath(request)
		if (cookieDomain != null) {
			cookie.domain = cookieDomain
		}
		response.addCookie(cookie)
	}

	private fun getCookiePath(request: HttpServletRequest): String {
		val contextPath = request.contextPath
		return if (contextPath.length > 0) contextPath else "/"
	}
}
package io.solarconnect.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.solarconnect.security.jwt.auth.JwtUser;
import lombok.experimental.UtilityClass;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chaeeung.e
 * @since 2017-09-19
 */
@UtilityClass
public class JwtCookieUtil {

	public void tokenToCookie(HttpServletResponse response, String domain, String serviceName, String signingKey, Claims claims) {
		Cookie cookie = new Cookie(serviceName, JwtUtil.generateToken(signingKey, claims));
		cookie.setHttpOnly(false);
		cookie.setSecure(false);
		cookie.setDomain(domain);
		cookie.setPath("/");
		//다음서버로 전송되기까지의 시간만
		cookie.setMaxAge(30000 * 1000);
//		cookie.setMaxAge(30*1000);
		response.addCookie(cookie);
	}

	public JwtUser claimFromCookie(HttpServletRequest request, String serviceName, String signingKey) {
		if(request.getCookies()==null){
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(serviceName)) {
				return JwtUtil.getBody(signingKey, cookie.getValue());
			}
		}
		return null;
	}

	public String tokenFromCookie(HttpServletRequest request, String serviceName, String signingKey) {
		if(request.getCookies()==null){
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(serviceName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

}

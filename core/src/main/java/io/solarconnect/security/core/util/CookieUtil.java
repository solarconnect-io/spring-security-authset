package io.solarconnect.security.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@UtilityClass
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CookieUtil {

    public static void add(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieDomain, String cookieValue, int maxAge, boolean secureCookie) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
        if (maxAge < 1) {
            cookie.setVersion(1);
        }
        cookie.setSecure(secureCookie);
        response.addCookie(cookie);
    }

    public static void cancel(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieDomain) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
        response.addCookie(cookie);
    }

    private static String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

}

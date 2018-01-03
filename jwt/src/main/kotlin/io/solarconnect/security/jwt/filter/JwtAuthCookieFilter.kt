package io.solarconnect.security.jwt.filter

import io.solarconnect.security.jwt.auth.JwtUser
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthCookieFilter<USER_ID, JWT_USER : JwtUser<USER_ID>> : JwtAbstractFilter<USER_ID, JWT_USER>() {
    override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

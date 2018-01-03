package io.solarconnect.security.jwt.filter

import io.solarconnect.security.jwt.auth.JwtUser
import org.scriptonbasestar.tool.core.exception.compiletime.SBTextExtractException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthHeaderFilter<USER_ID, JWT_USER : JwtUser<USER_ID>> : JwtAbstractFilter<USER_ID, JWT_USER>() {
    override fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
        val authHeader = request.getHeader("Authorization") ?: throw SBTextExtractException("인증 헤더가 없습니다.")
        if (!authHeader.matches("^(?i)Bearer\\s+.+".toRegex())) {
            throw SBTextExtractException("JWT 인증 헤더가 아닙니다.")
        }
        return authHeader.split("(?i)Bearer\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
    }
//    @Throws(SBTextExtractException::class)
//    protected fun extractTokenString(request: HttpServletRequest, response: HttpServletResponse): String {
//        val authHeader = request.getHeader("Authorization") ?: throw SBTextExtractException("인증 헤더가 없습니다.")
//        if (!authHeader.matches("^(?i)Bearer\\s+.+".toRegex())) {
//            throw SBTextExtractException("JWT 인증 헤더가 아닙니다.")
//        }
//        return authHeader.split("(?i)Bearer\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
//    }

}

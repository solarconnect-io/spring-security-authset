package io.solarconnect.security.core.access

import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SsoAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val redirect: String

    constructor(redirect:String){
        this.redirect = redirect
    }

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null) {
            response.sendRedirect(this.redirect)
        }
    }

}
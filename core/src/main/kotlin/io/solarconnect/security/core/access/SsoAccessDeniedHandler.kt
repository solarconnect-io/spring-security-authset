package io.solarconnect.security.core.access

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SsoAccessDeniedHandler : AccessDeniedHandler {

    private val redirect:String

    constructor(redirect:String) {
        this.redirect = redirect
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null) {
            response.sendRedirect(this.redirect)
        }
    }

}
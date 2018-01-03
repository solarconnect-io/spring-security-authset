package io.solarconnect.security.core.util

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * @author archmagece@gmail.com
 * @since 2018-01-03 오후 12:40
 */
object SecurityMethodUtil {
    @JvmStatic
    fun getAuthentication(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }

    @JvmStatic
    fun <PRINCIPAL> getPrincipal(): PRINCIPAL? {
        var authentication = getAuthentication() ?: return null
        return try {
            authentication.principal as PRINCIPAL
        } catch (e: ClassCastException) {
            null
        }
    }

    @JvmStatic
    fun getUsername(): String? {
        var authentication = getAuthentication() ?: return null
        return authentication.name
    }

    @JvmStatic
    fun loginProcess(userDetailsService: UserDetailsService, username: String) {
        var userDetails = userDetailsService.loadUserByUsername(username)
        loginProcess(userDetails)
    }

    @JvmStatic
    fun loginProcess(userDetails: UserDetails) {
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    @JvmStatic
    fun logoutProcess() {
        SecurityContextHolder.clearContext()
    }

}
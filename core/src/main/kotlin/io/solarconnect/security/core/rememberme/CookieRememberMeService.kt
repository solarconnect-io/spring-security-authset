package io.solarconnect.security.core.rememberme

import io.solarconnect.security.core.auth.FindUserAuthorityService
import io.solarconnect.security.core.util.CookieUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.security.authentication.AccountStatusException
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsChecker
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.codec.Base64
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.rememberme.CookieTheftException
import org.springframework.security.web.authentication.rememberme.InvalidCookieException
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException
import org.springframework.util.Assert
import java.security.SecureRandom
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieRememberMeService : RememberMeServices, InitializingBean, LogoutHandler {

    companion object {
        val logger = LoggerFactory.getLogger(CookieRememberMeService::class.java)
        val DEFAULT_SERIES_LENGTH = 16
        val DEFAULT_TOKEN_LENGTH = 16

        val DEFAULT_PARAMETER = "remember-me"
        val SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY = "remember-me"
        val DEFAULT_COOKIE_MAX_AGE = 320000
        val DEFAULT_COOKIE_SECURE = false
    }

    private val tokenRepository: RememberMeRepository
    private val cookieService: CookieService
    private val findUserAuthorityService: FindUserAuthorityService
    private val random: SecureRandom = SecureRandom()
    private var userDetailsChecker: UserDetailsChecker = AccountStatusUserDetailsChecker()

    private var seriesLength = DEFAULT_SERIES_LENGTH
    private var tokenLength = DEFAULT_TOKEN_LENGTH

    private val signingKey: String

    private var cookieName = SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
    private var cookieDomain: String? = null
    private var cookieMaxAge = DEFAULT_COOKIE_MAX_AGE
    private var cookieSecure = DEFAULT_COOKIE_SECURE

    constructor(signingKey: String, cookieService: CookieService, findUserAuthorityService: FindUserAuthorityService, rememberMeRepository: RememberMeRepository) {
        this.signingKey = signingKey
        this.cookieService = cookieService
        this.findUserAuthorityService = findUserAuthorityService
        this.tokenRepository = rememberMeRepository
    }


    override fun loginSuccess(request: HttpServletRequest, response: HttpServletResponse, successfulAuthentication: Authentication) {
        val username = successfulAuthentication.name

        logger.debug("Creating new persistent login for user " + username)

        val rememberMeToken = RememberMeUser(username, generateSeriesData(), generateTokenData(), Date())
        try {
            tokenRepository.storeToken(username, rememberMeToken)
            CookieUtil.add(request, response, cookieName, cookieDomain, cookieService.encode(rememberMeToken), cookieMaxAge, cookieSecure)
        } catch (e: Exception) {
            logger.error("Failed to save persistent token ", e)
        }
    }

    override fun autoLogin(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        var rememberMeCookie: String? = extractRememberMeCookie(request) ?: return null
        logger.debug("Remember-me cookie detected")

        if (rememberMeCookie!!.length == 0) {
            logger.debug("Cookie was empty")
            CookieUtil.cancel(request, response, cookieName, cookieDomain)
            return null
        }

        try {
            val user = cookieService.decode(rememberMeCookie)
            userDetailsChecker.check(user)

            logger.debug("Remember-me cookie accepted")

            return RememberMeAuthenticationToken(user.username, user, findUserAuthorityService.findGrantedAuthority(user.username))
        } catch (cte: CookieTheftException) {
            CookieUtil.cancel(request, response, cookieName, cookieDomain)
            throw cte
        } catch (noUser: UsernameNotFoundException) {
            logger.debug("Remember-me login was valid but corresponding user not found.",
                    noUser)
        } catch (invalidCookie: InvalidCookieException) {
            logger.debug("Invalid remember-me cookie: " + invalidCookie.message)
        } catch (statusInvalid: AccountStatusException) {
            logger.debug("Invalid UserDetails: " + statusInvalid.message)
        } catch (e: RememberMeAuthenticationException) {
            logger.debug(e.message)
        }


        CookieUtil.cancel(request, response, cookieName, cookieDomain)
        return null
    }

    private fun extractRememberMeCookie(request: HttpServletRequest): String? {
        val cookies = request.cookies

        if (cookies == null || cookies.size == 0) {
            return null
        }

        for (cookie in cookies) {
            if (cookieName == cookie.name) {
                return cookie.value
            }
        }

        return null
    }

    override fun loginFail(request: HttpServletRequest, response: HttpServletResponse) {
        CookieUtil.cancel(request, response, cookieName, cookieDomain)
    }

    override fun afterPropertiesSet() {
        Assert.hasLength(signingKey, "key cannot be empty or null")
        Assert.notNull(findUserAuthorityService, "A findUserAuthorityService is required")
        Assert.notNull(tokenRepository, "A tokenRepository is required")
    }

    private fun generateSeriesData(): String {
        val newSeries = ByteArray(seriesLength)
        random.nextBytes(newSeries)
        return String(Base64.encode(newSeries))
    }

    private fun generateTokenData(): String {
        val newToken = ByteArray(tokenLength)
        random.nextBytes(newToken)
        return String(Base64.encode(newToken))
    }

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        val username = authentication!!.name
        CookieUtil.cancel(request, response, cookieName, cookieDomain)
        tokenRepository.removeToken(username)
    }
}

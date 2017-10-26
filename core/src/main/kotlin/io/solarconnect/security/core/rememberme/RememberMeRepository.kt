package io.solarconnect.security.core.rememberme

import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException

interface RememberMeRepository {

    fun exists(key: String): Boolean

    fun storeToken(key: String, token: RememberMeUser)

    @Throws(RememberMeAuthenticationException::class)
    fun findToken(key: String): RememberMeUser

    fun removeToken(key: String)

    fun dumpAll()

}

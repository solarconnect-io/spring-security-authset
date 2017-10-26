package io.solarconnect.security.core.rememberme

interface CookieService {
    fun encode(dto: RememberMeUser): String
    fun decode(value: String): RememberMeUser
}
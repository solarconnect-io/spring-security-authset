package io.solarconnect.security.jwt.auth

import io.jsonwebtoken.Claims
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

interface JwtUser : Claims, UserDetails {
	companion object {
		val USER_ID = "uid"
		val USER_NICKNAME = "nnm";
		val USER_USERNAME = "unm";
		val USER_ROLE = "uro";
		val USER_PRINCIPAL = "upr";
	}

	fun getUserId(): Long
	fun getNickname(): String
	fun getRole(): String
	fun getPrincipal(): String
}
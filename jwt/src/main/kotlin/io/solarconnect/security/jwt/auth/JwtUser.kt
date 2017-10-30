package io.solarconnect.security.jwt.auth

import io.jsonwebtoken.Claims
import io.solarconnect.security.core.auth.BusinessUser

interface JwtUser : Claims, BusinessUser {
	val nickname:String
	val roles: Collection<String>
	val principals: Collection<String>
}
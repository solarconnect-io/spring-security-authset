package io.solarconnect.security.jwt.custom

import com.auth0.jwt.interfaces.Claim
import io.solarconnect.security.jwt.auth.JwtUser

/**
 * @author chaeeung.e
 * @since 2017-11-10
 */
interface JwtHandler<out USER_ID, out JWT_USER : JwtUser<USER_ID>> {
	fun generateUser(signingKey: String, jwtToken: String): JWT_USER
	fun generateToken(signingKey: String, claim: Claim): String
}
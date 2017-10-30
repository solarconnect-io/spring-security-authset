package io.solarconnect.security.jwt.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtHandler
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.solarconnect.security.jwt.auth.JwtUser

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
object JwtUtil {

	fun generateToken(signingKey: String, claims: Claims): String {
		return Jwts.builder().signWith(SignatureAlgorithm.HS256, signingKey)
				.setClaims(claims)
				.compact()
	}

	fun extractClaims(signingKey: String, token: String): Claims {
		return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).body
	}

	fun <JWT_USER : JwtUser>getBody(signingKey: String, JwtHandler: JwtHandler<JWT_USER>, token: String): JWT_USER {
		return Jwts.parser().setSigningKey(signingKey).parse(token, JwtHandler)
	}

}
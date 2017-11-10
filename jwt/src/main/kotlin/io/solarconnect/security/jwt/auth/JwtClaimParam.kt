package io.solarconnect.security.jwt.auth

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
object JwtClaimParam {
	object Header {
		const val ALGORITHM = "alg"
		const val TYPE = "typ"
	}
	object Payload {
		//global
		const val USER_UNIQUE = "usr"
		const val USER_NICKNAME = "nnm"
		const val USER_ROLES = "uro"

		//site
		const val USER_PRINCIPALS = "upr"

		//default
		const val ISSUER = "iss"
		const val SUBJECT = "sub"
		const val AUDIENCE = "aud"
		const val EXPIRATION = "exp"
		const val NOT_BEFORE = "nbf"
		const val ISSUED_AT = "iat"
		const val ID = "jti"
	}
}
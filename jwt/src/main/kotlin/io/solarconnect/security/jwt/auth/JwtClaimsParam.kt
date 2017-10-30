package io.solarconnect.security.jwt.auth

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
object JwtClaimsParam {
	object Header {
		const val ALGORITHM = "alg"
		const val TYPE = "typ"
	}
	object Payload {
		const val USER_ID = "uid"
		const val USER_NICKNAME = "nnm"
		const val USER_USERNAME = "unm"
		const val USER_ROLES = "uro"
		const val USER_PRINCIPALS = "upr"

		const val ISSUER = "iss"
		const val SUBJECT = "sub"
		const val AUDIENCE = "aud"
		const val EXPIRATION = "exp"
		const val NOT_BEFORE = "nbf"
		const val ISSUED_AT = "iat"
		const val ID = "jti"
	}
}
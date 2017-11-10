package io.solarconnect.security.jwt

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author chaeeung.e
 * @since 2017-11-09
 */
class JwtValidateTest {
	companion object {

		var secret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
		var message = "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOi0xLCJubm0iOiJNQVNURVIiLCJ1bm0iOiJNQVNURVIiLCJ1cm8iOlsiUk9MRV9SRVNPVVJDRV9NQVNURVIiXX0.gkygspj1ZMtKCydjK2f7VvS6QOO012BsgyNN8E4AeYo"
//		var message = ""

		@JvmStatic
		fun main(args: Array<String>) {
			var messageSplit = message.split(".")
			var header = Base64.getDecoder().decode(messageSplit[0])
			var payload = Base64.getDecoder().decode(messageSplit[1])
			var verifySignature = messageSplit[2]

			println(messageSplit[0])
			println(String(header))
			println(messageSplit[1])
			println(String(payload))
			println(messageSplit[0]+"."+messageSplit[1])

			println("===========================")
			println(verifySignature)
			println(String(Base64.getDecoder().decode(verifySignature)))
			println(String(Base64.getEncoder().encode(verifySignature.toByteArray())))
			println("===========================")

			val signingKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
			val sha256_HMAC = Mac.getInstance("HmacSHA256")
			sha256_HMAC.init(signingKey)

			println(toHexString(sha256_HMAC.doFinal((messageSplit[0]+"."+messageSplit[1]).toByteArray())))
//			println(Base64.getDecoder().decode(sha256_HMAC.doFinal((messageSplit[0]+"."+messageSplit[1]).toByteArray())))
			println(String(Base64.getEncoder().encode(sha256_HMAC.doFinal((messageSplit[0]+"."+messageSplit[1]).toByteArray()))))
			println(String(sha256_HMAC.doFinal((messageSplit[0]+"."+messageSplit[1]).toByteArray())))

//			Jwts.builder().signWith(SignatureAlgorithm.HS256, signingKey)
//					.setClaims(claims)
//					.compact()
//			Jwts.parser().setSigningKey(SignatureAlgorithm.HS256, secret).parseClaimsJws(message)
		}

		private fun toHexString(bytes: ByteArray): String {
			val formatter = Formatter()
			bytes.forEach { formatter.format("%02x", it) }
//			for (b in bytes) {
//				formatter.format("%02x", b)
//			}
			return formatter.toString()
		}

	}
}
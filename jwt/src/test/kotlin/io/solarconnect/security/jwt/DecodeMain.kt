package io.solarconnect.security.jwt

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class DecodeMain {
    companion object {

        var secret = "secret"
        //		var message = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ"
        var message = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"

        @JvmStatic
        fun main(args: Array<String>) {
//			println(Base64.getDecoder().decode(message))
//			message = message.split(".")[0]

            var sha256_HMAC = Mac.getInstance("HmacSHA256")
            val secret_key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            sha256_HMAC.init(secret_key)

            println(String(sha256_HMAC.doFinal()))
            println(String(Base64.getEncoder().encode(sha256_HMAC.doFinal())))
//			println(String(Base64.getDecoder().decode(sha256_HMAC.doFinal())))
            println(String(sha256_HMAC.doFinal(message.toByteArray())))
            println(String(Base64.getEncoder().encode(sha256_HMAC.doFinal(message.toByteArray()))))
            println(String(Base64.getDecoder().decode(sha256_HMAC.doFinal(message.toByteArray()))))


        }

    }
}

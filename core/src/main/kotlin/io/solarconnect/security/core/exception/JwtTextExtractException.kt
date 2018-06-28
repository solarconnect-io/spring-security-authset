package io.solarconnect.security.core.exception

import org.springframework.security.core.AuthenticationException

class JwtTextExtractException : AuthenticationException {
	constructor(msg: String):super(msg)
	constructor(msg: String, t: Throwable):super(msg, t)
}

package io.solarconnect.security.core.exception

import org.springframework.security.core.AuthenticationException

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
class NoRoleException : AuthenticationException {
	constructor(msg: String):super(msg)
	constructor(msg: String, t: Throwable):super(msg, t)
}

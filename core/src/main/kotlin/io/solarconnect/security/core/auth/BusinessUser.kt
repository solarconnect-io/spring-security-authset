package io.solarconnect.security.core.auth

import org.springframework.security.core.userdetails.UserDetails

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
interface BusinessUser : UserDetails {
	val userId: Long
}
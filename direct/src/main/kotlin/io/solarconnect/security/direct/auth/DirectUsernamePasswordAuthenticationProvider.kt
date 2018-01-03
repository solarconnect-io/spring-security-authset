package io.solarconnect.security.direct.auth

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.*
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.authentication.dao.SaltSource
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsChecker
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.util.Assert

class DirectUsernamePasswordAuthenticationProvider : DaoAuthenticationProvider {

	companion object {
		val log = LoggerFactory.getLogger(DirectUsernamePasswordAuthenticationProvider::class.java)
	}


	constructor() {
		preAuthenticationChecks = DefaultPreAuthenticationChecks()
		postAuthenticationChecks = DefaultPostAuthenticationChecks()
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider#supports(java.lang.Class)
	 */
	override fun supports(authentication: Class<out Any>): Boolean {

		return DirectUsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.authentication.dao.DaoAuthenticationProvider
	 * #
	 * additionalAuthenticationChecks(org.springframework.security.core.userdetails
	 * .UserDetails, org.springframework.security.authentication.
	 * UsernamePasswordAuthenticationToken)
	 */
	@Throws(AuthenticationException::class)
	override fun additionalAuthenticationChecks(userDetails: UserDetails,
												authentication: UsernamePasswordAuthenticationToken) {

		// super.additionalAuthenticationChecks(userDetails, authentication);

		val signedToken = authentication as DirectUsernamePasswordAuthenticationToken

		if (signedToken.requestSignature == null) {
			throw BadCredentialsException(messages.getMessage(
					"SignedUsernamePasswordAuthenticationProvider.missingSignature", "Missing request signature"))// ,
			// isIncludeDetailsObject() ? userDetails : null);
		}

		// calculate expected signature
		if (!signedToken.requestSignature.equals(
				calculateExpectedSignature(signedToken))) {
			throw BadCredentialsException(messages.getMessage(
					"SignedUsernamePasswordAuthenticationProvider.badSignature", "Invalid request signature"))// ,
			// isIncludeDetailsObject() ? userDetails : null);
		}
	}

	/**
	 * Given a signed token, calculates the signature value expected to be
	 * suppliled.
	 *
	 * @param signedToken the signed token to evaluate
	 * @return the expected signature
	 */
	private fun calculateExpectedSignature(signedToken: DirectUsernamePasswordAuthenticationToken): String {

		return signedToken.principal.toString() + "|+|" + signedToken.credentials
	}

	/**
	 * @author archmagece
	 *
	 *
	 * AbtractUserDetailsAuthenticate 어쩌고 하는거 수정
	 */
	@Throws(AuthenticationException::class)
	override fun authenticate(authentication: Authentication): Authentication {

		// return super.authenticate(authentication);

		Assert.isInstanceOf(
				UsernamePasswordAuthenticationToken::class.java, authentication, messages.getMessage(
				"AbstractUserDetailsAuthenticationProvider.onlySupports",
				"Only UsernamePasswordAuthenticationToken is supported"))

		// Determine username
		val username = if (authentication.principal == null) "NONE_PROVIDED" else authentication.name

		var cacheWasUsed = true
		var user: UserDetails? = super.getUserCache().getUserFromCache(username)

		if (user == null) {
			cacheWasUsed = false

			try {
				user = retrieveUser(username, authentication as UsernamePasswordAuthenticationToken)
			} catch (notFound: UsernameNotFoundException) {
				log.debug("User '$username' not found")

				/*
				 * if (hideUserNotFoundExceptions) { throw new
				 * BadCredentialsException(messages.getMessage(
				 * "AbstractUserDetailsAuthenticationProvider.badCredentials",
				 * "Bad credentials"));
				 */
				if (hideUserNotFoundExceptions) {
					throw BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials", "fucku"))
				} else {
					throw notFound
				}
				// throw notFound;
			}

			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract")
		}

		try {
			super.getPreAuthenticationChecks().check(user)
			additionalAuthenticationChecks(user!!, authentication as UsernamePasswordAuthenticationToken)
		} catch (exception: AuthenticationException) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false
				user = retrieveUser(username, authentication as UsernamePasswordAuthenticationToken)
				super.getPreAuthenticationChecks().check(user)
				additionalAuthenticationChecks(user!!, authentication)
			} else {
				throw exception
			}
		}

		super.getPostAuthenticationChecks().check(user)

		if (!cacheWasUsed) {
			super.getUserCache().putUserInCache(user)
		}

		var principalToReturn: Any = user!!

		if (super.isForcePrincipalAsString()) {
			principalToReturn = user.username
		}

		return createSuccessAuthentication(
				principalToReturn, authentication, user)
	}

	override fun setPasswordEncoder(passwordEncoder: Any) {

		super.setPasswordEncoder(passwordEncoder)
	}

	override fun setSaltSource(saltSource: SaltSource) {

		super.setSaltSource(saltSource)
	}

	override fun setUserDetailsService(userDetailsService: UserDetailsService) {

		super.setUserDetailsService(userDetailsService)
	}

	private inner class DefaultPreAuthenticationChecks : UserDetailsChecker {
		override fun check(user: UserDetails) {
			if (!user.isAccountNonLocked) {
				log.debug("User account is locked")

				throw LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked",
						"User account is locked") + user.toString())
			}

			if (!user.isEnabled) {
				log.debug("User account is disabled")

				throw DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled",
						"User is disabled") + user.toString())
			}

			if (!user.isAccountNonExpired) {
				log.debug("User account is expired")

				throw AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired",
						"User account has expired") + user.toString())
			}
		}
	}

	private inner class DefaultPostAuthenticationChecks : UserDetailsChecker {
		override fun check(user: UserDetails) {
			if (!user.isCredentialsNonExpired) {
				log.debug("User account credentials have expired")

				throw CredentialsExpiredException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.credentialsExpired",
						"User credentials have expired") + user.toString())
			}
		}
	}
}
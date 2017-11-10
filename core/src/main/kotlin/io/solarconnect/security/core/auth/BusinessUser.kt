package io.solarconnect.security.core.auth

import org.springframework.security.core.userdetails.UserDetails

/**
 * @author chaeeung.e
 * @since 2017-10-30
 */
interface BusinessUser<out USER_ID> : UserDetails {
	/**
	 * 사용자를 나타내는 값
	 * ex) 데이터베이스의 SEQ, 사용자의 아이디, 이메일, 전화번호.. 등 서비스에서 사용하는값에 따라
	 * Unique, Immutable
	 */
	val userUnique: USER_ID
	/**
	 * 서비스에서 사용자의 공개 이름
	 * 실명, 별명, 무관
	 */
	val userNickname: String
	/**
	 * 서비스에서 사용자 역할
	 * ex) MASTER, MANAGER, USER... 등
	 */
	val userRoles: Collection<String>
}
package io.solarconnect.security.core.auth

import org.springframework.security.core.GrantedAuthority

interface FindUserAuthorityService<in USER_ID> {
	/**
	 * SSO에 연동된 서비스 중 사용자에게 허용된 서비스 도메인
	 * 추가2차 : 이번에 요청이 들어온 서비스
	 *
	 * @param userId
	 * @return
	 */
	fun findUserComponent(userId: USER_ID): Collection<String>

	fun findGrantedAuthority(vararg roles: String): Collection<GrantedAuthority>

	fun findUserRole(userId: USER_ID): Collection<String>

	fun findUserPrincipal(userId: USER_ID): Collection<String>

	fun findPrincipalByRoleName(roleName: String): Collection<String>
}
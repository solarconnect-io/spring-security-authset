package io.solarconnect.security.core.auth

import org.springframework.security.core.GrantedAuthority

interface FindUserAuthorityService {
	/**
	 * SSO에 연동된 서비스 중 사용자에게 허용된 서비스 도메인
	 * 추가2차 : 이번에 요청이 들어온 서비스
	 *
	 * @param userId
	 * @return
	 */
	fun findUserComponent(userId: Long): Collection<String>

	fun findGrantedAuthority(vararg roles: String): Collection<GrantedAuthority>

	fun findUserRole(userId: Long): Collection<String>

	fun findUserPrincipal(userId: Long): Collection<String>

	fun findPrincipalByRoleName(roleName: String): Collection<String>
}
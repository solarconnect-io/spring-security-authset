package io.solarconnect.security.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author archmagece
 * @since 2015-12-26
 */
@UtilityClass
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityMethodUtil {

	public static Authentication getAuthentication() {
		return SecurityContextHolder
				.getContext()
				.getAuthentication();
	}

	/**
	 * PRINCIPAL java.lang.String, org.springframework.security.core.userdetails.UserDetails, java.security.Principal
	 * @param <PRINCIPAL>
	 * @return
	 */
	public static<PRINCIPAL>PRINCIPAL getPrincipal() {
		Authentication authentication = getAuthentication();
		if(authentication==null){
			return null;
		}
		Object principal = authentication.getPrincipal();
		try{
			return (PRINCIPAL)principal;
		}catch (ClassCastException e){
			return null;
		}
	}

	public static String getUsername() {
		Authentication authentication = getAuthentication();
		if(authentication==null){
			return null;
		}
		return authentication.getName();
	}

	public static void loginProcess(UserDetailsService userDetailsService, String username){
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
	}

	public static void loginProcess(UserDetails userDetails){
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
	}

	public static void logoutProcess(){
		SecurityContextHolder.clearContext();
	}

}

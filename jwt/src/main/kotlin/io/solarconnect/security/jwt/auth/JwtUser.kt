package io.solarconnect.security.jwt.auth

import io.jsonwebtoken.Claims
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

interface JwtUser : Claims, UserDetails
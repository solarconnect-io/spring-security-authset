package io.solarconnect.security.jwt.auth

import com.auth0.jwt.interfaces.Claim
import io.solarconnect.security.core.auth.BusinessUser

interface JwtUser<out USER_ID> : Claim, BusinessUser<USER_ID>
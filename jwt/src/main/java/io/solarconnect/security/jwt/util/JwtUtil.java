package io.solarconnect.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.solarconnect.security.jwt.JwtHandler;
import io.solarconnect.security.jwt.auth.JwtUser;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JwtUtil {

    public static String generateToken(String signingKey, Claims claims) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, signingKey)
                .setClaims(claims)
                .compact();
    }

    public static JwtUser getBody(String signingKey, String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return new JwtUser(claims, true, true, true, true);
    }

    public static JwtUser getBody(String signingKey, JwtHandler JwtHandler, String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parse(token, JwtHandler);
        return new JwtUser(claims, true, true, true, true);
    }

}

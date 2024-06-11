package com.example.demo.util;

import java.security.Key;

import java.time.Instant;
import java.util.Date;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.temporal.ChronoUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final int JWT_TOKEN_VALIDITY = 60;
	 public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

	public String generateToken(String userName) {
		var now = Instant.now();
		return Jwts.builder().subject(userName).issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(JWT_TOKEN_VALIDITY, ChronoUnit.MINUTES)))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(getSignKey()).build().parseSignedClaims(token).getPayload();

		} catch (Exception e) {
			throw new AccessDeniedException("Access denied: " + e.getMessage());
		}
	}

	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String userName = getUserName(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().before(new Date());
	}
	 private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
}

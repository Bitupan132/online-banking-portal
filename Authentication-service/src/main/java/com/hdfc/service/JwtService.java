package com.hdfc.service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	public static final String SECRET_KEY = generateJwtSecret(64);

	private static String generateJwtSecret(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] randomBytes = new byte[length / 2];
		secureRandom.nextBytes(randomBytes);
		return bytesToHex(randomBytes);
	}
	
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			hexStringBuilder.append(String.format("%02x", b));
		}
		return hexStringBuilder.toString();
	}

	public void validateToken(final String token) {
		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

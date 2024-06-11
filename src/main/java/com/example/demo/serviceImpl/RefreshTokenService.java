package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.RefreshToken;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	public RefreshToken createRefreshToken(String username) {
		RefreshToken refreshToken = RefreshToken.builder().userName(username).refreshToken(UUID.randomUUID().toString())
				.expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can
																// configure it application.properties file
				.build();
		return refreshTokenRepository.save(refreshToken);
	}

	public RefreshToken findByToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token);
		if (refreshToken == null)
			throw new ResourceNotFoundException("Token Not Found with " + token);
		return refreshToken;
	}

	public boolean isValid(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException(
					token.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
		}
		return true;
	}

}

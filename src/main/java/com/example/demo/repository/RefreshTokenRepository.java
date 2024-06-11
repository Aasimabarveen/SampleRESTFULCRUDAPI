package com.example.demo.repository;

import com.example.demo.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken ,Long>{
	
	public RefreshToken findByRefreshToken(String refreshToken);
}

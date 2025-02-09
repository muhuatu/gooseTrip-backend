package com.example.goosetrip.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil {
	private static final String secretKey = "f8a9f8234a32d93f823f84c1c32bff8498327df02d6a52e4f284d2a8b4f736f1";

	// 生成Token的方法，接收email作為參數
	public String generateToken(String email) {
		// 將字符串轉換為字節數組
		byte[] secretKeyBytes = secretKey.getBytes();
		// 使用HS256算法生成SecretKey對象
		SecretKeySpec secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());

		// 創建JWT
		String jwt = Jwts.builder().setSubject(email) // 設定JWT的主題sub，通常是用戶的電子郵件
				.setIssuer("GOOSETRIP") // 設定JWT的發行者iss
				.setIssuedAt(new Date()) // 設定發行時間iat
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 過期時間1小時
				.signWith(secretKey) // 使用秘鑰對JWT進行簽名
				.compact(); // 生成最終的token

		return jwt; // 返回生成的JWT
	}

	// 驗證Token並解析Email
	public String validateToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
			return claims.getSubject(); // 返回 Email
		} catch (Exception e) {
			throw new IllegalArgumentException("無效的Token", e);
		}
	}

	private SecretKey getSecretKey() {
		return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}

}

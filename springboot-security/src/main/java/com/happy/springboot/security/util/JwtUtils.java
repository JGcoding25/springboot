package com.happy.springboot.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author Happy
 * @date 2019/11/1
 */
@Log4j2
@Component
public class JwtUtils {

	@Value("${jwt.expiration}")
	private Long expiration;
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.tokenHead}")
	private String tokenHead;

	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_CREATED = "created";

	/**
	 * 根据负责生成JWT的token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * 从token中获取JWT中的负载
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			log.info("JWT格式验证失败:{}", token);
		}
		return claims;
	}

	/**
	 * 生成token的过期时间
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 从token中获取登录用户名
	 */
	public String getUserNameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 验证token是否还有效
	 *
	 * @param token 客户端传入的token
	 * @return boolean true可用，false不可用
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	/**
	 * 判断token是否已经失效
	 */
	private boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	/**
	 * 从token中获取过期时间
	 */
	private Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 根据用户信息生成token
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>(2);
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 判断token是否可以被刷新
	 */
	private boolean canRefresh(String token) {
		return !isTokenExpired(token);
	}


	/**
	 * 当原来的token没过期是可以刷新
	 *
	 * @param oldToken 带tokenHead的token
	 */
	public String refreshHeadToken(String oldToken) {
		String token = oldToken.substring(tokenHead.length());
		if (canRefresh(token)) {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			return generateToken(claims);
		}
		return null;
	}
}

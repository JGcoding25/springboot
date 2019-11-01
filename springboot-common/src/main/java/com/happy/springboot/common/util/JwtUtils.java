package com.happy.springboot.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author Happy
 * @date 2019/11/1
 */
@Component
public class JwtUtils {

	private static final Logger LOGGER = LogManager.getLogger(JwtUtils.class);

	private static final String KEY = "C2$rjNMc78$LiiUD";

	public String createJwtToken() throws Exception{
		//token 密钥
		Algorithm algorithm = Algorithm.HMAC256(KEY);
		//头部信息
		Map<String, Object> map = new HashMap<>(16);
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		Date nowDate = new Date();
		Date expireDate = DateUtils.getAfterDate(nowDate,0,0,0,0,2,0);
		return JWT.create()
				//设置头部信息 Header
				.withHeader(map)
				//签名是有谁生成 例如 服务器
				.withIssuer("SERVICE")
				//签名的主题
				.withSubject("this is token")
				//签名的观众 也可以理解谁接受签名的
				.withAudience("APP")
				//生成签名的时间
				.withIssuedAt(nowDate)
				//签名过期的时间
				.withExpiresAt(expireDate)
				//签名 Signature
				.sign(algorithm);
	}

	public void verifyToken(String token) throws Exception{
		Algorithm algorithm = Algorithm.HMAC256(KEY);
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("SERVICE")
				.build();
		DecodedJWT jwt = verifier.verify(token);
		String subject = jwt.getSubject();
		Map<String, Claim> claims = jwt.getClaims();
		Claim claim = claims.get("loginName");
		System.out.println(claim.asString());
		List<String> audience = jwt.getAudience();
		LOGGER.debug("subject={}",subject);
		LOGGER.debug("audience={}",audience);
	}
}

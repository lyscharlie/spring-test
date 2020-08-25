package com.lyscharlie.common.utils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.time.DateUtils;

import com.lyscharlie.biz.entity.UserDO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenUtils {

	public static final String AUTH_HEADER_KEY = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	private static String clientId = "spring-test";
	private static String secretKey = "secret123456";
	private static int expireHours = 12;

	/**
	 * 构建jwt
	 *
	 * @param user
	 * @return
	 */
	public static String getToken(UserDO user) {
		Date now = new Date();

		//生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

		//添加构成JWT的参数
		JwtBuilder builder = Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setHeaderParam("typ", "JWT")
				.claim("userId", user.getUserId())
				.claim("userName", user.getUserName())
				.setSubject(user.getUserId().toString())
				.setIssuer(clientId)
				.setIssuedAt(now)
				.setNotBefore(now)
				.setExpiration(DateUtils.addHours(now, expireHours))
				.setAudience(user.getUserId().toString())
				.signWith(SignatureAlgorithm.HS256, signingKey);

		//生成JWT
		return builder.compact();
	}

	/**
	 * 解析jwt
	 *
	 * @param token
	 * @return
	 */
	public static Claims parseJWT(String token) throws Exception {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
					.parseClaimsJws(token).getBody();
			return claims;
		} catch (ExpiredJwtException e) {
			log.error("Token过期", e);
			throw new Exception("Token过期");
		} catch (SignatureException e) {
			log.error("Token签名错误", e);
			throw new Exception("Token签名错误");
		} catch (Exception e) {
			log.error("token解析异常", e);
			throw new Exception("token解析异常");
		}
	}

	/**
	 * 从token中获取用户名
	 *
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) throws Exception {
		return parseJWT(token).getSubject();
	}

	/**
	 * 从token中获取用户ID
	 *
	 * @param token
	 * @return
	 */
	public static String getUserId(String token) throws Exception {
		String userId = parseJWT(token).get("userId", String.class);
		return userId;
	}

	/**
	 * 是否已过期
	 *
	 * @param token
	 * @return
	 */
	public static boolean isExpiration(String token) throws Exception {
		return parseJWT(token).getExpiration().before(new Date());
	}

	/**
	 * 验证jwt
	 *
	 * @param token
	 * @return
	 */
	public static boolean validateJWT(String token) throws Exception {
		try {
			Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
					.parseClaimsJws(token).getBody();
			return true;
		} catch (ExpiredJwtException e) {
			log.error("Token过期", e);
			throw new Exception("Token过期");
		} catch (SignatureException e) {
			log.error("Token签名错误", e);
			throw new Exception("Token签名错误");
		} catch (Exception e) {
			log.error("token解析异常", e);
			throw new Exception("token解析异常");
		}

	}
}

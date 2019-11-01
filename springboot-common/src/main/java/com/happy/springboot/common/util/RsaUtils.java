package com.happy.springboot.common.util;

import com.happy.springboot.common.constants.Constants;
import com.happy.springboot.common.enums.CodeEnums;
import com.happy.springboot.common.exception.CustomRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 *
 * @author Happy
 * @date 2019/11/1
 */
public class RsaUtils {

	private static final Logger LOGGER = LogManager.getLogger(RsaUtils.class);

	/**
	 * 加密算法
	 */
	private static final String KEY_ALGORITHM_RSA = "RSA";

	/**
	 * 签名算法
	 */
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 256;

	/**
	 * 生成密钥对,经过base64加密
	 * @param keySize 生成的秘钥长度  一般为1024或2048
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 * @author Happy
	 * @date 2019/6/14
	 */
	public static Map<String, String> generateKeyBytes(int keySize) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance(KEY_ALGORITHM_RSA);
			keyPairGenerator.initialize(keySize);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			Map<String, String> keyMap = new HashMap(2);
			keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
			keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
			return keyMap;
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("秘钥生产异常",e);
		}
		return null;
	}

	/**
	 * 使用公钥对数据进行解密
	 *
	 * @param content   使用私钥加密过的数据
	 * @param publicKey 公钥
	 * @param charset   编码默认UTF-8
	 * @return 解密后的数据
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String content, String publicKey, String charset) throws Exception {
		String decryptedData;
		try {
			PublicKey pubKey = getPublicKeyFromX509(KEY_ALGORITHM_RSA, Base64.getDecoder().decode(publicKey));
			decryptedData = decrypt(content, pubKey, charset);
		} catch (Exception e) {
			throw new Exception("RSAcontent = " + content + "; charset = " + charset + " ERROR = ", e);
		}
		return decryptedData;
	}

	/**
	 * 使用私钥对数据进行解密
	 *
	 * @param content    使用公钥加密过的数据
	 * @param privateKey 私钥
	 * @param charset    编码默认UTF-8
	 * @return 解密后的数据
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
		if(Strings.isEmpty(content) || Strings.isEmpty(privateKey)){
			throw new CustomRuntimeException(CodeEnums.MISSING_PARAMETER);
		}
		String decryptedData;
		try {
			PrivateKey priKey = getPrivateKeyFromPkcs8(KEY_ALGORITHM_RSA, Base64.getDecoder().decode(privateKey));
			decryptedData = decrypt(content, priKey, charset);
		} catch (Exception e) {
			throw new Exception("RSAcontent = " + content + "; charset = " + charset + " ERROR = ", e);
		}
		return decryptedData;
	}

	/**
	 * 公钥加密
	 *
	 * @param content   需要加密的数据
	 * @param publicKey 公钥
	 * @param charset   编码默认UTF-8
	 * @return 使用公钥加密后的数据
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String content, String publicKey, String charset) throws Exception {
		if(Strings.isEmpty(content) || Strings.isEmpty(publicKey)){
			throw new CustomRuntimeException(CodeEnums.MISSING_PARAMETER);
		}
		String encryptedData;
		try {
			PublicKey pubKey = getPublicKeyFromX509(KEY_ALGORITHM_RSA, Base64.getDecoder().decode(publicKey));
			encryptedData = encrypt(content, pubKey, charset);
		} catch (Exception e) {
			throw new Exception("RSAcontent = " + content + "; charset = " + charset + " ERROR = ", e);
		}
		return encryptedData;
	}


	/**
	 * 私钥加密
	 *
	 * @param content    待加密的数据
	 * @param privateKey 私钥
	 * @param charset    编码默认UTF-8
	 * @return 使用私钥加密后的数据
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String content, String privateKey, String charset) throws Exception {
		String encryptedData;
		try {
			PrivateKey priKey = getPrivateKeyFromPkcs8(KEY_ALGORITHM_RSA, Base64.getDecoder().decode(privateKey));
			encryptedData = encrypt(content, priKey, charset);
		} catch (Exception e) {
			throw new Exception("RSAcontent = " + content + "; charset = " + charset + " ERROR = ", e);
		}
		return encryptedData;
	}

	/**
	 * 解密
	 *
	 * @param content 需要解密的内容经过base64加密的
	 * @param key     公钥或私钥
	 * @param charset 字符集默认UTF-8
	 * @return java.lang.String 加密后的字符串
	 * @author Happy
	 * @date 2019/6/3
	 */
	private static String decrypt(String content, Key key, String charset){
		ByteArrayOutputStream out = null ;
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] encryptedData = StringUtils.isEmpty(charset)
					? Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8))
					: Base64.getDecoder().decode(content.getBytes(charset));

			int inputLen = encryptedData.length;
			out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptdData = out.toByteArray();
			return StringUtils.isEmpty(charset) ? new String(decryptdData)
					: new String(decryptdData, charset);
		}catch (Exception e){
			LOGGER.error("RSA2解密异常",e);
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 加密 base64
	 *
	 * @param content 需要加密的内容
	 * @param key     公钥或私钥
	 * @param charset 字符串默认UTF-8
	 * @return java.lang.String
	 * @author Happy
	 * @date 2019/6/3
	 */
	private static String encrypt(String content, Key key, String charset){
		ByteArrayOutputStream out = null;
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] data = StringUtils.isEmpty(charset) ? content.getBytes(StandardCharsets.UTF_8) : content.getBytes(charset);

			out = new ByteArrayOutputStream();
			int inputLen = data.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = Base64.getEncoder().encode(out.toByteArray());
			return StringUtils.isEmpty(charset) ? new String(encryptedData)
					: new String(encryptedData, charset);
		}catch (Exception e){
			LOGGER.error("RSA2加密异常",e);
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 私钥对已加密数据进行签名
	 *
	 * @param data  已加密的数据
	 * @param receivedPrivateKey 私钥
	 * @return java.lang.String 对已加密数据生成的签名
	 * @author Happy
	 * @date 2019/6/1
	 */
	public static String sign(byte[] data, String receivedPrivateKey) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(receivedPrivateKey);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);

		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
		signature.initSign(privateKey);
		signature.update(data);

		return new String(Base64.getEncoder().encode(signature.sign()));
	}

	/**
	 * 用公钥验签
	 *
	 * @param data      签名之前的数据
	 * @param receivedPublicKey 公钥
	 * @param sign      签名之后的数据
	 * @return 验签是否成功
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String receivedPublicKey, String sign) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(receivedPublicKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
		signature.initVerify(publicKey);
		signature.update(data);

		return signature.verify(Base64.getDecoder().decode(sign));
	}


	private static PrivateKey getPrivateKeyFromPkcs8(String algorithm, byte[] keyBytes) throws Exception {
		if (keyBytes == null) {
			return null;
		}

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
	}

	private static PublicKey getPublicKeyFromX509(String algorithm, byte[] keyBytes) throws Exception {
		if (keyBytes == null) {
			return null;
		}

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
	}

}

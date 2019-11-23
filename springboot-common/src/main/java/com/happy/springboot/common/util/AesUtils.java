package com.happy.springboot.common.util;

import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author Happy
 * @date 2019/11/1
 */
@Log4j2
public class AesUtils {

	/**
	 * 算法/模式/补码方式
	 */
	private static String CIPHERMODE = "AES/ECB/PKCS5Padding";

	/**
	 * AES秘钥支持128bit/192bit/256bit三种长度的秘钥，一个字节等于8bit，因此支持生成的字符串的长度应该是 16/24/32
	 */
	private static int KEY_LENGTH_16 = 16;
	private static int KEY_LENGTH_24 = 24;
	private static int KEY_LENGTH_32 = 32;

	/**
	 * 英文大小写加数字
	 */
	private static final String STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final String ENCRYPT_TYPE_AES = "AES";

	/**
	 * @param length 需要生成的字符串长度
	 * @return 随机生成的字符串
	 * @author Happy
	 * @date 2019/6/1
	 */
	public static String getRandomKey(int length) {
		if (trueKeyLength(length)) {
			return null;
		}
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			stringBuilder.append(STR.charAt(number));
		}
		return stringBuilder.toString();
	}


	/**
	 * @param data 需要加密的数据
	 * @param key  加密使用的key
	 * @return 加密后的数据(Base64编码)
	 * @throws Exception
	 */
	public static String encrypt(String data, @NotNull String key) throws Exception {
		if (trueKeyLength(key.length())) {
			return null;
		}

		byte[] raw = key.getBytes(StandardCharsets.UTF_8);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRYPT_TYPE_AES);
		Cipher cipher = Cipher.getInstance(CIPHERMODE);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

		return new String(Base64.getEncoder().encode(encrypted));
	}


	/**
	 * @param data 需要解密的数据
	 * @param key  解密用的key
	 * @return 解密后的数据
	 * @throws Exception
	 */
	public static String decrypt(String data, @NotNull String key) throws Exception {
		if (trueKeyLength(key.length())) {
			return null;
		}
		try {
			byte[] raw = key.getBytes(StandardCharsets.UTF_8);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, ENCRYPT_TYPE_AES);
			Cipher cipher = Cipher.getInstance(CIPHERMODE);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			//先用base64解密
			byte[] encrypted = Base64.getDecoder().decode(data);
			try {
				byte[] original = cipher.doFinal(encrypted);
				return new String(original, StandardCharsets.UTF_8);
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	private static boolean trueKeyLength(int length) {
		if (length == KEY_LENGTH_16 || length == KEY_LENGTH_24 || length != KEY_LENGTH_32) {
			return true;
		} else {
			log.error("长度必须为16/24/32");
			return false;
		}
	}
}

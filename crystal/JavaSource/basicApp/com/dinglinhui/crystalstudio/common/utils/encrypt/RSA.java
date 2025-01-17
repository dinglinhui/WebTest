package com.dinglinhui.crystalstudio.common.utils.encrypt;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

/* Rsa是非对称算法，
 * 他可以产生密钥对，可以用公钥加密，私钥解密，或者私钥加密，公钥解密，最大程度的保障安全性，也可以用私钥签名，公钥验证签名的正确性，防止拥有私钥的人抵赖。*/

//利用公钥加密，私钥解密：

public class RSA {

	public static void main(String[] args) throws Exception {
		String str = "hello,有中文的情况";
		System.out.println("原文：" + str);
		RSA rsa = new RSA();
		RSAPrivateKey privateKey = (RSAPrivateKey) rsa.readFromFile("key/rsa/sk.dat");
		RSAPublicKey publickKey = (RSAPublicKey) rsa.readFromFile("key/rsa/pk.dat");
		byte[] encbyte = rsa.encrypt(str, privateKey);
		System.out.println("私钥加密后：");
		String encStr = toHexString(encbyte);
		System.out.println(encStr);
		byte[] signBytes = rsa.sign(str, privateKey);
		System.out.println("签名值：");
		String signStr = toHexString(signBytes);
		System.out.println(signStr);
		byte[] decByte = rsa.decrypt(encStr, publickKey);
		System.out.println("公钥解密后：");
		String decStr = new String(decByte);
		System.out.println(decStr);
		if (rsa.verifySign(str, signStr, publickKey)) {
			System.out.println("rsa sign check success");
		} else {
			System.out.println("rsa sign check failure");
		}
	}

	/** 
	 * 加密,key可以是公钥，也可以是私钥 
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	public byte[] encrypt(String message, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(message.getBytes());
	}

	/**
	 * 解密，key可以是公钥，也可以是私钥，如果是公钥加密就用私钥解密，反之亦然 
	 * @param message 
	 * @return 
	 * @throws Exception
	 */
	public byte[] decrypt(String message, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(toBytes(message));
	}

	/** 
	 * 用私钥签名 
	 * @param message
	 * @param key
	 * @return 
	 * @throws Exception 
	 */
	public byte[] sign(String message, PrivateKey key) throws Exception {
		Signature signetcheck = Signature.getInstance("MD5withRSA");
		signetcheck.initSign(key);
		signetcheck.update(message.getBytes("ISO-8859-1"));
		return signetcheck.sign();
	}

	/**
	 * 用公钥验证签名的正确性 
	 * @param message
	 * @param signStr
	 * @return
	 * @throws Exception
	 */
	public boolean verifySign(String message, String signStr, PublicKey key)
			throws Exception {
		if (message == null || signStr == null || key == null) {
			return false;
		}
		Signature signetcheck = Signature.getInstance("MD5withRSA");
		signetcheck.initVerify(key);
		signetcheck.update(message.getBytes("ISO-8859-1"));
		return signetcheck.verify(toBytes(signStr));
	}

	/** 
	 * 从文件读取object  
	 * @param fileName 
	 * @return  
	 * @throws Exception 
	 */
	private Object readFromFile(String fileName) throws Exception {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
		Object obj = input.readObject();
		input.close();
		return obj;
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static final byte[] toBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}

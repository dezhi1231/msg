package com.mx.utils.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	public String str;

	public static String encrypt(String paramString) {

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(paramString.getBytes());
			byte[] m = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < m.length; i++) {
				sb.append(m[i]);
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}

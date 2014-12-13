package com.google.youtube.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;

public class AppUtil_i {

	public static String saveAppMD5(Context paramContext) {

		File file = new File(paramContext.getPackageResourcePath());

		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;

		FileInputStream in = null;

		byte buffer[] = new byte[1024];

		int len;

		try {

			digest = MessageDigest.getInstance("MD5");

			in = new FileInputStream(file);

			while ((len = in.read(buffer)) > 0) {
				digest.update(buffer, 0, len);
			}

			BigInteger bigInt = new BigInteger(1, digest.digest());
			
			
			return bigInt.toString(16);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		
		return null;

	}

	public static String getAppSign(Context context) {

		PackageManager localPackageManager = context.getPackageManager();

		try {
			Signature[] arrayOfSignature = localPackageManager.getPackageInfo(
					MyHelpUtil.deCrypto(getPackageName(context), MyHelpUtil.A),
					64).signatures;

			String signature = paseSignature(arrayOfSignature[0].toByteArray());

			String signatureMd5 = Md5.encrypt(signature);

			return signatureMd5;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String paseSignature(byte[] signature) {
		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");

			X509Certificate cert = (X509Certificate) certificateFactory
					.generateCertificate(new ByteArrayInputStream(signature));

			return cert.getSerialNumber().toString();

		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getAppName(Context paramContext) {
		PackageManager localPackageManager = paramContext.getPackageManager();

		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = localPackageManager.getApplicationInfo(
					paramContext.getPackageName(), 0);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return MyHelpUtil.enCrypto((String) localPackageManager
				.getApplicationLabel(applicationInfo), MyHelpUtil.A);
	}

	public static String getPackageName(Context paramContext) {

		return MyHelpUtil.enCrypto(paramContext.getPackageName(), MyHelpUtil.A);

	}

	public static String getversionName(Context context) {

		PackageManager packageManager = context.getPackageManager();

		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			return MyHelpUtil.enCrypto(packageInfo.versionName, MyHelpUtil.A);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static String getversionCode(Context context) {

		PackageManager packageManager = context.getPackageManager();

		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			return MyHelpUtil.enCrypto(String.valueOf(packageInfo.versionCode),
					MyHelpUtil.A);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return MyHelpUtil.enCrypto("", MyHelpUtil.A);
	}

	//
	public static int getPackageSizeInfo(Context context) {

		PackageManager packageManager = context.getPackageManager();
		int size = 0;
		try {
			Method getPackageSizeInfo = packageManager.getClass()
					.getDeclaredMethod("getPackageSizeInfo", String.class);
			Object o = getPackageSizeInfo.invoke(packageManager,
					context.getPackageName());
			size = (Integer) o;
			return size;
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage(), e);
			return 0;
		}
	}

	public static String getPackageLocation(Context context) {

		return MyHelpUtil.enCrypto(context.getPackageResourcePath(),
				MyHelpUtil.A);

	}

}
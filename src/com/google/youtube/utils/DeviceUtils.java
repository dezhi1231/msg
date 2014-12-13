package com.google.youtube.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeviceUtils {

	public static String getBuletoothMacAddr(Context paramContext) {
		String btMac = "";
		try {
			BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (m_BluetoothAdapter != null) {
				btMac = m_BluetoothAdapter.getAddress();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("err", e.getMessage());
		}
		return btMac;
	}

	public static String getIMEI(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");
		String res = MyHelpUtil.enCrypto(localTelephonyManager.getDeviceId(),
				MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static String getIMSI(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");

		String s = localTelephonyManager.getSubscriberId();

		String res = MyHelpUtil.enCrypto(s, MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}

	}

	public static String getNetworkOperator(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");
		String res = MyHelpUtil.enCrypto(
				localTelephonyManager.getNetworkOperator(), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}

	}

	public static String getLine1Number(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");
		String res = MyHelpUtil.enCrypto(
				localTelephonyManager.getLine1Number(), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static String getNetworkCountryIso(Context paramContext) {
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");
		String res = MyHelpUtil.enCrypto(
				localTelephonyManager.getNetworkCountryIso(), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static int getScreenHeight(Context paramContext) {
		return paramContext.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getScreenWidth(Context paramContext) {
		return paramContext.getResources().getDisplayMetrics().widthPixels;
	}

	public static int isRoot() {
		try {
			
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				return 1;
			}

		} catch (Exception e) {

		}

		return 0;
	}

	public static String getRELEASEVersion() {

		return MyHelpUtil.enCrypto(android.os.Build.VERSION.RELEASE + "",
				MyHelpUtil.A);
	}

	public static String getManufacturer() {

		String res = MyHelpUtil.enCrypto(Build.MANUFACTURER, MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static int getTelephoneType(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return telephonyManager.getPhoneType();

	}

	public static String getModel() {

		String res = MyHelpUtil.enCrypto(Build.MODEL, MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static String getResolution(Context paramContext) {
		Resources localResources = paramContext.getResources();
		int i = localResources.getDisplayMetrics().widthPixels;
		int j = localResources.getDisplayMetrics().heightPixels;
		String res = MyHelpUtil.enCrypto(i + "x" + j, MyHelpUtil.A);
		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	//
	public static boolean isMTKChip() {
		boolean bool = true;
		try {
			Class.forName("com.mediatek.featureoption.FeatureOption");
			Log.d("DeviceUtils", "isMTKChip() isMtk=" + bool);
			return bool;
		} catch (ClassNotFoundException localClassNotFoundException) {
			bool = false;
		}
		return bool;
	}

	public static String getWifiMacAddr(Context paramContext) {
		WifiManager localWifiManager = (WifiManager) paramContext
				.getSystemService("wifi");
		WifiInfo wifiInfo = localWifiManager.getConnectionInfo();

		String res = MyHelpUtil
				.enCrypto(wifiInfo.getMacAddress(), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}

	}

	public static String getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		String res = MyHelpUtil.enCrypto(
				String.valueOf(availableBlocks * blockSize), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static String getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();

		String res = MyHelpUtil.enCrypto(
				String.valueOf(totalBlocks * blockSize), MyHelpUtil.A);

		if (res == null) {
			return "CD6D40F84F547C00";
		} else {
			return res;
		}
	}

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static String getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();

			String res = MyHelpUtil.enCrypto(
					String.valueOf(availableBlocks * blockSize), MyHelpUtil.A);

			if (res == null) {
				return "CD6D40F84F547C00";
			} else {
				return res;
			}

		} else {
			return "CD6D40F84F547C00";
		}
	}

	public static String getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();

			String res = MyHelpUtil.enCrypto(
					String.valueOf(totalBlocks * blockSize), MyHelpUtil.A);

			if (res == null) {
				return "CD6D40F84F547C00";
			} else {
				return res;
			}
		} else {
			return MyHelpUtil.enCrypto("-1", MyHelpUtil.A);
		}
	}

	public static String getAndroid(Context context) {

		String m_szAndroidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		return m_szAndroidID;
	}

	public static String getDevIDShort() {
		String m_szDevIDShort = "35" + Build.BOARD.length() % 10
				+ Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
				+ Build.HOST.length() % 10 + Build.ID.length() % 10
				+ Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
				+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
				+ Build.TYPE.length() % 10 + Build.USER.length() % 10;
		return m_szDevIDShort;
	}

	//
	public static String getDeviceUtils(Context paramContext) {

		String m_szLongID = MyHelpUtil.deCrypto(getIMEI(paramContext),
				MyHelpUtil.A)
				+ getDevIDShort()
				+ getAndroid(paramContext)
				+ MyHelpUtil.deCrypto(getWifiMacAddr(paramContext),
						MyHelpUtil.A);
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
		byte p_md5Data[] = m.digest();
		String m_szUniqueID = new String();
		for (int i = 0; i < p_md5Data.length; i++) {
			int b = (0xFF & p_md5Data[i]);
			if (b <= 0xF)
				m_szUniqueID += "0";
			m_szUniqueID += Integer.toHexString(b);
		}
		m_szUniqueID = m_szUniqueID.toUpperCase();

		return m_szUniqueID;
	}

	public static String getKeyStore(Context context) {
		ApplicationInfo appInfo;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);

			String keystore = appInfo.metaData.getString("cid");

			String res = MyHelpUtil.enCrypto(keystore, MyHelpUtil.A);

			if (res == null) {
				return "CD6D40F84F547C00";
			} else {
				return res;
			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getLocation(Context context) {

		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		String result = "";

		if (l != null) {
			result = l.getLatitude() + "," + l.getLongitude();
		}

		return result;
	}

	public static String getLocalLanguage(Context context) {

		return MyHelpUtil.enCrypto(
				context.getResources().getConfiguration().locale.getLanguage(),
				MyHelpUtil.A);
	}

}
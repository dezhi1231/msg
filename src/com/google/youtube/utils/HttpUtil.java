package com.google.youtube.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import android.text.TextUtils;

public class HttpUtil {

	/*
	 * public static final String BASE_URL =
	 * "http://192.168.1.105:8080/upload/";
	 * 
	 * public static final String BASE_DOWNLOAD_URL =
	 * "http://192.168.1.105:8080/upload/appdownload.action";
	 * 
	 * public static final String BASE_IMG_URL =
	 * "http://192.168.1.105:8080/upload/img/";
	 */

	public static final String BASE_URL = "http://sj.umi-online.com:89/upload/";

	public static final String BASE_DOWNLOAD_URL = "http://xz.umi-online.com:89/upload/appdownload.action";

	public static final String BASE_IMG_URL = "http://sj.umi-online.com:89/upload/img/";

	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() {
						BufferedReader in = null;
						String result = "";
						try {

							URL localurl = new URL(url);

							URLConnection conn = localurl.openConnection();

							conn.setRequestProperty("accept", "*/*");

							conn.setRequestProperty("connection", "Keep-Alive");

							conn.setRequestProperty("user-agent",
									"Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");

							conn.connect();

							in = new BufferedReader(new InputStreamReader(conn
									.getInputStream()));

							String line = "";

							while ((line = in.readLine()) != null) {
								result += line;
							}

							return result;
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (in != null) {
									in.close();
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String getBitmap(final CharSequence ico_img) throws Exception {

		if (TextUtils.isEmpty(ico_img)) {
			return null;
		}

		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() {

						InputStream is = null;

						OutputStream os = null;

						File imgfile = new File(MyHelpUtil.getDir() + ico_img
								+ ".png");

						try {

							URL url = new URL(BASE_IMG_URL + ico_img + ".png");

							is = url.openStream();

							if (is != null && !"".equals(MyHelpUtil.getDir())) {

								File dirfile = new File(MyHelpUtil.getDir());

								if (!dirfile.exists()) {
									dirfile.mkdir();
								}

								if (!imgfile.exists()) {

									os = new FileOutputStream(imgfile);

									byte[] buff = new byte[1024];

									int hasRead = 0;

									while ((hasRead = is.read(buff)) > 0) {
										os.write(buff, 0, hasRead);
									}
								}

							}

						} catch (Exception e) {
							e.printStackTrace();

							if (imgfile.exists()) {

								imgfile.delete();
							}

						} finally {
							try {
								if (is != null) {
									is.close();
								}
								if (os != null) {

									os.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String postRequest(final String url, final String rawParams)
			throws Exception {

		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() {

						PrintWriter out = null;

						BufferedReader in = null;

						String result = "";

						try {

							URL localurl = new URL(url);

							URLConnection conn = localurl.openConnection();

							conn.setRequestProperty("accept", "*/*");

							conn.setRequestProperty("connection", "Keep-Alive");

							conn.setRequestProperty("user-agent",
									"Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");

							conn.setDoOutput(true);

							conn.setDoInput(true);

							out = new PrintWriter(conn.getOutputStream());

							out.print(rawParams);

							out.flush();

							in = new BufferedReader(new InputStreamReader(conn
									.getInputStream()));

							String line;

							while ((line = in.readLine()) != null) {
								result += line;
							}

							return result;

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (out != null) {
									out.close();
								}
								if (in != null) {

									in.close();
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						return null;

					}
				});

		new Thread(task).start();
		return task.get();
	}

}

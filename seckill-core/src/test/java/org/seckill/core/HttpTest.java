package org.seckill.core;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpTest {

	private static ExecutorService executorService = Executors.newFixedThreadPool(150);

	public static void main(String[] args) throws Exception {
		OkHttpClient okHttpClient = new OkHttpClient();
		for (int i = 0; i < 1; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					Request request = new Request.Builder().url("http://127.0.0.1:8080/seckill/user/seck?uid="
							+ UUID.randomUUID() + "&itemId=itemcode1&activityCode=t001").addHeader("cc", "cc").build();
					try {
						Response response = okHttpClient.newCall(request).execute();
						response.isSuccessful();
						System.out.println(response.body().string());

						/*List<Cookie> cookies = okHttpClient.cookieJar().loadForRequest(request.url());
						if (!cookies.isEmpty()) {
							response.header("Cookie", cookieHeader(cookies));
						}
*/
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
					}
				}
			});
		}
		executorService.shutdown();
	}

	public static String cookieHeader(List<Cookie> cookies) {
		StringBuilder cookieHeader = new StringBuilder();
		for (int i = 0, size = cookies.size(); i < size; i++) {
			if (i > 0) {
				cookieHeader.append("; ");
			}
			Cookie cookie = cookies.get(i);
			cookieHeader.append(cookie.name()).append('=').append(cookie.value());
		}
		return cookieHeader.toString();
	}
}

package org.seckill.core;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookieTest {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static void main(String[] args) {
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON, "{\"username\":\"hejian\",\"password\":\"pingan@123\"}");
		Request request = new Request.Builder().url("https://sso.jk.cn/auth/login?redirect_url=http://www.test.pajkdc.com/stepbg/index.html").post(body).addHeader("Cookie", "_tk=12312").build();
		// Request.Builder().url("https://sso.jk.cn/auth/login?redirect_url=http://www.test.pajkdc.com/stepbg/index.html").build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			response.isSuccessful();
			System.out.println(response.body().string());
			//获取cookie
			List<Cookie> cookies = okHttpClient.cookieJar().loadForRequest(request.url());
			okHttpClient.cookieJar().saveFromResponse(request.url(), cookies);
			if (!cookies.isEmpty()) {
				cookies.stream().filter(c -> c.name().equalsIgnoreCase("_tk")).collect(Collectors.toList());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
}

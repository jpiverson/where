package site.penn.where.utils;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkUtils {

	public static String sendGet(String url) {
		StringBuffer result = new StringBuffer();
		// 得到HttpClient对象
		HttpClient getClient = new DefaultHttpClient();
		// 得到HttpGet对象
		HttpGet request = new HttpGet(url);
		try {
			// 客户端使用GET方式执行请教，获得服务器端的回应response
			HttpResponse response = getClient.execute(request);
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity entity = response.getEntity();
				// 获得输入流
				InputStream is = entity.getContent();

				int c = is.read();
				while (c != -1) {
					System.out.println((char) c);
					result.append((char) c);
					c = is.read();
				}
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String sendPost() {

		return null;
	}

}

package site.penn.where.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.GsonBuilder;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import site.penn.common.http.ReqGson;
import site.penn.common.security.TrippleDesUtils;

public class HttpUtils {

	private Context context;
	private int statusCode;
	private int timeout = 5000;
	private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";

	private String url;
	private String content;
	private String error;

	/**
	 * 
	 * @param url
	 */
	public HttpUtils(Context context, String url) {
		this.context = context;
		this.url = url;
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param timeout
	 *            超时时间
	 */
	public HttpUtils(Context context, String url, int timeout) {
		this.context = context;
		this.url = url;
		this.timeout = timeout;
	}

	/**
	 * 发送GET请求
	 * 
	 * @return http status code
	 */
	public int sendGet() {
		HttpGet httpGet = new HttpGet(this.url);
		return sendRequest(httpGet);
	}

	/**
	 * post请求
	 * 
	 * @return
	 */
	public int sendPost() {
		HttpPost httpPost = new HttpPost(this.url);
		return sendRequest(httpPost);
	}

	/**
	 * post请求 发送key-value
	 * 
	 * @param params
	 * @return
	 */
	public int sendPost(Map<String, String> params) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		HttpPost httpPost = new HttpPost(this.url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sendRequest(httpPost);
	}

	/**
	 * post请求 发送字符串
	 * 
	 * @param params
	 * @return
	 */
	public int sendPost(String params) {
		HttpPost httpPost = new HttpPost(this.url);
		try {
			ReqGson req = new ReqGson();
			req.setTimestamp(System.currentTimeMillis());
			req.setParams(params);
			req.createSign();
			String json = new GsonBuilder().create().toJson(req);
			String reqParam = TrippleDesUtils.encrypt(json);
			httpPost.setEntity(new StringEntity(reqParam));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendRequest(httpPost);
	}

	/**
	 * 发送http请求
	 * 
	 * @param request
	 * @return
	 */
	private int sendRequest(HttpUriRequest request) {
		HttpResponse response = null;
		try {
			AndroidHttpClient client = AndroidHttpClient.newInstance(userAgent, context);
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, timeout);
			HttpConnectionParams.setSoTimeout(params, timeout);
			HttpClientParams.setRedirecting(params, true);
			HttpClientParams.setAuthenticating(params, false);
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			this.statusCode = response.getStatusLine().getStatusCode();
			this.content = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			if (response != null) {
				this.statusCode = response.getStatusLine().getStatusCode();
			}
			this.error = e.getMessage();
		}
		return this.statusCode;
	}

	/**
	 * 请求是否有错误
	 * 
	 * @return 错误true/没错误false
	 */
	public boolean hasError() {
		if (null == this.error || "".equals(this.error)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获得错误信息
	 * 
	 * @return
	 */
	public String getError() {
		return this.error;
	}

	/**
	 * 获得响应代码
	 * 
	 * @return
	 */
	public int getStatusCode() {
		return this.statusCode;
	}

	/**
	 * 获得相应内容
	 * 
	 * @return
	 */
	public String getContent() {
		return this.content;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}

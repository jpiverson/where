package site.penn.where.activities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import site.penn.where.R;
import site.penn.where.common.SysCons;
import site.penn.where.entity.bo.SoftwareVersion;
import site.penn.where.utils.PackageUtils;

public class MainActivity extends Activity {

	private static String TAG = "MainActivity";

	private static final int OK = 1;

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		checkServer();
	}

	// 向服务器发送数据
	private void checkServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient httpCient = new DefaultHttpClient();
				Log.i(TAG, SysCons.SERVER_URL + "interfaces/app/checkVersion");
				HttpGet httpGet = new HttpGet(SysCons.SERVER_URL + "interfaces/app/checkVersion");
				try {
					HttpResponse httpResponse = httpCient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity);
						Log.i(TAG, response);
						Message message = new Message();
						message.what = OK;
						message.obj = response;
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case OK:
				try {
					dealContent(msg.obj.toString());
				} catch (Exception je) {
					textView.setText("服务器错误(解析错误)");
				}
				break;
			default:
				break;
			}
		}
	};

	private void dealContent(String content) throws Exception {
		PackageUtils pkgUtils = new PackageUtils(MainActivity.this);
		JSONObject json = new JSONObject(content).getJSONObject("content");
		SoftwareVersion svServer = new SoftwareVersion(json.get("versionNumber").toString());
		SoftwareVersion svLocal = new SoftwareVersion(pkgUtils.getAppVersionName());
		if (svServer.compare(svServer, svLocal) > 0 && json.getBoolean("forcedUpdate")) {
			// 如果版本号终于大于本地,且需要强制更新
			updateApp();
		} else {
			textView.setText("服务器良好");
			Thread.sleep(1000);
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			intent.putExtra("content", content);
			startActivity(intent);
		}
	}

	private void updateApp() {
		Log.i(TAG, "进行APP升级");
	}
}

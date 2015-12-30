package site.penn.where.activities;

import com.isnc.facesdk.SuperID;
import com.isnc.facesdk.common.Cache;
import com.isnc.facesdk.common.SDKConfig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import site.penn.where.R;
import site.penn.where.common.SysCons;
import site.penn.where.utils.HttpUtils;

public class MainActivity extends Activity {

	private static String TAG = "MainActivity";
	private String userInfo;
	private String openId;

	ImageButton button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickEvent(View view) {
		SuperID.faceLogin(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case SDKConfig.AUTH_SUCCESS:
			this.openId = Cache.getCached(MainActivity.this, SDKConfig.KEY_OPENID);
			this.userInfo = Cache.getCached(MainActivity.this, SDKConfig.KEY_APPINFO);
			sendUserInfo();
			Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
			break;
		case SDKConfig.LOGINSUCCESS:
			this.openId = Cache.getCached(MainActivity.this, SDKConfig.KEY_OPENID);
			userInfo = Cache.getCached(MainActivity.this, SDKConfig.KEY_APPINFO);
			sendUserInfo();
			Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private void sendUserInfo() {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String url = SysCons.SERVER_URL + "/login/superid";
					HttpUtils http = new HttpUtils(MainActivity.this, url);
					http.sendPost(userInfo);
					Log.i(TAG, "----->>" + http.getContent());
					Log.i(TAG, "----->>" + http.getError());
					Log.i(TAG, "----->>" + http.getStatusCode());
					if (http.getStatusCode() == 200) {
						Intent intent = new Intent(MainActivity.this, LocationActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
					}
				}
			}).start();
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}
}

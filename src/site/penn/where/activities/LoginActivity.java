package site.penn.where.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import site.penn.where.R;
import site.penn.where.WhereApplication;
import site.penn.where.common.SysCons;
import site.penn.where.utils.HttpUtils;

public class LoginActivity extends Activity {

	private static String TAG = "MainActivity";
	private String userInfo;
	private String country;
	private String phone;
	private String openId;

	ImageButton button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	public void onClickEvent(View view) {
		switch (view.getId()) {
		case R.id.superid_login_button:
			SuperID.faceLogin(this);
			break;
		case R.id.mobsms_login_button:
			SMSSDK.initSDK(this, "d395cf1942e8", "f1524680594c0d561c0a87dd6b968edd");
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					Log.i(TAG, String.valueOf(result));
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
						country = (String) phoneMap.get("country");
						phone = (String) phoneMap.get("phone");
						// 提交用户信息
						registerUser();
					}
				}
			});
			registerPage.show(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case SDKConfig.AUTH_SUCCESS:
			this.openId = Cache.getCached(LoginActivity.this, SDKConfig.KEY_OPENID);
			this.userInfo = Cache.getCached(LoginActivity.this, SDKConfig.KEY_APPINFO);
			sendUserInfo();
			Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
			break;
		case SDKConfig.LOGINSUCCESS:
			this.openId = Cache.getCached(LoginActivity.this, SDKConfig.KEY_OPENID);
			userInfo = Cache.getCached(LoginActivity.this, SDKConfig.KEY_APPINFO);
			sendUserInfo();
			Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private void sendUserInfo() {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String url = SysCons.SERVER_URL + "/login/superid";
					HttpUtils http = new HttpUtils(LoginActivity.this, url);
					http.sendPost(userInfo);
					Log.i(TAG, "----->>" + http.getContent());
					Log.i(TAG, "----->>" + http.getError());
					Log.i(TAG, "----->>" + http.getStatusCode());
					if (http.getStatusCode() == 200) {
						Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
					}
				}
			}).start();
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}

	// 提交用户信息
	private void registerUser() {
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String url = SysCons.SERVER_URL + "/login/mobsms";
					HttpUtils http = new HttpUtils(WhereApplication.getInstance(), url);
					http.sendPost("{\"phone\":\""+phone+"\",\"country\":\""+country+"\"}");
					if (http.getStatusCode() == 200) {
						Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
					}
				}
			}).start();
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}
}

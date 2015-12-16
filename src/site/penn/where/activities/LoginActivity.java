package site.penn.where.activities;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import site.penn.where.R;

public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SMSSDK.initSDK(this, "d395cf1942e8", "f1524680594c0d561c0a87dd6b968edd");
		RegisterPage registerPage = new RegisterPage();
		registerPage.setRegisterCallback(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				// 解析注册结果
				if (result == SMSSDK.RESULT_COMPLETE) {
					@SuppressWarnings("unchecked")
					HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
					String country = (String) phoneMap.get("country");
					String phone = (String) phoneMap.get("phone");
					// 提交用户信息
					registerUser(country, phone);
				}
			}
		});
		registerPage.show(this);
	}

	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "SmsSDK_User_" + uid;
		String avatar = "http://7nj2vs.com1.z0.glb.clouddn.com/head-portrait.jpg";
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
	}

}

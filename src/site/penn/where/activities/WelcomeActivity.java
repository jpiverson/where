package site.penn.where.activities;

import com.isnc.facesdk.SuperID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import site.penn.where.R;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SuperID.setDebugMode(true);
		SuperID.initFaceSDK(this);
		// 获取activity_splash_page.xml布局
		View view = View.inflate(this, R.layout.activity_welcome, null);
		// 加载布局
		setContentView(view);

		// 设置动画，渐显渐隐可用AlphaAnimation,变形动画用RotateAnimation,位移动画用TranslateAnimation
		// 第一个参数值0.3f为开始的透明度为30%，第二个参数值1.0f为结束的透明度为100%，即不透明。
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);

		// 给动画设置持续时间，如果不设置，则时间为0，动画就看不到效果
		alphaAnimation.setDuration(5000);

		// 给我们的背景运行动画
		view.startAnimation(alphaAnimation);
		// 设置动画监听
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override // 动画一开始就执行以下方法
			public void onAnimationStart(Animation animation) {
			}

			@Override // 动画重复时执行以下方法
			public void onAnimationRepeat(Animation animation) {
			}

			@Override // 动画结束时执行以下方法
			public void onAnimationEnd(Animation animation) {
				// 在此填写执行跳转到其他页面的语句
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}
}

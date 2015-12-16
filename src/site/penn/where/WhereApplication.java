package site.penn.where;

import android.app.Application;
import android.util.Log;
import cn.smssdk.SMSSDK;

public class WhereApplication extends Application {

	private static final String TAG = "WhereApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "WhereApplication onCreate()......");
	}
}

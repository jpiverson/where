package site.penn.where;

import android.app.Application;

public class WhereApplication extends Application {

	private static WhereApplication instance;

	public static WhereApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
}

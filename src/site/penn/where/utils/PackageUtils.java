package site.penn.where.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils {

	private PackageInfo packageInfo;

	public PackageUtils(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			this.packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getAppVersionName() {
		return packageInfo.versionName;
	}

	public int getAppVersionCode() {
		return packageInfo.versionCode;
	}

}

package site.penn.where.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import site.penn.where.entity.enums.CallStatus;
import site.penn.where.entity.enums.TelecomProvider;

public class TelephonyUtils {

	private TelephonyManager telephonyManager;

	public TelephonyUtils(Context context) {
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * 获得电话号码
	 * 
	 * @return
	 */
	public String getNativePhoneNumber() {
		String nativePhoneNumber = telephonyManager.getLine1Number();
		return nativePhoneNumber;
	}

	/**
	 * GSM网络的IMSI编号
	 * 
	 * @return
	 */
	public String getImsi() {
		String imsi = telephonyManager.getSubscriberId();
		return imsi;
	}

	/**
	 * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
	 * 
	 * @return
	 */
	public String getImei() {
		String imsi = telephonyManager.getDeviceId();
		return imsi;
	}

	/**
	 * 电话状态
	 * 
	 * @return
	 */
	public CallStatus getCallStatus() {
		int status = telephonyManager.getCallState();
		return CallStatus.getCallStatus(status);
	}

	/**
	 * 获得电信供应商
	 * 
	 * @return
	 */
	public TelecomProvider getTelecomProvider() {
		TelecomProvider provider = TelecomProvider.UNKNOWN;
		String imsi = getImsi();
		if (null == imsi || "".equals(imsi)) {
			return provider;
		}
		if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
			provider = TelecomProvider.CHINA_MOBILE;
		} else if (imsi.startsWith("46001")) {
			provider = TelecomProvider.CHINA_UNICOM;
		} else if (imsi.startsWith("46003")) {
			provider = TelecomProvider.CHINA_TELECOM;
		}
		return provider;
	}

}

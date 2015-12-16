package site.penn.where.entity.enums;

public enum TelecomProvider {
	UNKNOWN, // 未知
	CHINA_MOBILE, // 中国移动
	CHINA_UNICOM, // 中国联通
	CHINA_TELECOM;// 中国电信

	public static TelecomProvider getTelecomProvider(int type) {
		if (type > 3) {
			type = 0;
		}
		return TelecomProvider.values()[type];
	}
}

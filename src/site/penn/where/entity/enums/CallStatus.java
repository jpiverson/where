package site.penn.where.entity.enums;

public enum CallStatus {

	CALL_STATE_IDLE, // 无任何状态时
	CALL_STATE_RINGING, // 电话进来时
	CALL_STATE_OFFHOOK; // 接起电话时

	public static CallStatus getCallStatus(int status) {
		return CallStatus.values()[status];
	}
}

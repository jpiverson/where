package site.penn.where.entity.bo;

import java.io.Serializable;

public class LocationBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mobile;// 手机号码

	private String longitude;// 纬度坐标

	private String latitude;// 经度坐标

	private String altitude;// 高度信息，目前没有实现

	private String speed;// 速度,仅gps定位结果时有速度信息,单位:公里每小时

	private String radius;// 定位精度

	private String coorType;// 获取所用坐标系，目前没有实现，以locationClientOption里设定的坐标系为准

	private Integer locType; // 定位类型

	private Integer satelliteNumber;// gps定位结果时，获取gps锁定用的卫星数

	private String direction;// 获取手机当前的方向

	private String address;// 获取详细地址信息
	
	private String locationDescribe; //

	private String province;// 地址

	private String city;// 城市

	private String district;// 获取区/县信息

	private String street;// 获取街道信息

	private String streetNumber;// 获取街道号码

	private String floor;// 获取楼层信息,仅室内定位时有效

	private String networkLocationType; 

	private Boolean cellChangeFlag;

	private Integer operators;

	private String reportTime;// 上报时间

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getCoorType() {
		return coorType;
	}

	public void setCoorType(String coorType) {
		this.coorType = coorType;
	}

	public Integer getLocType() {
		return locType;
	}

	public void setLocType(Integer locType) {
		this.locType = locType;
	}

	public Integer getSatelliteNumber() {
		return satelliteNumber;
	}

	public void setSatelliteNumber(Integer satelliteNumber) {
		this.satelliteNumber = satelliteNumber;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLocationDescribe() {
		return locationDescribe;
	}

	public void setLocationDescribe(String locationDescribe) {
		this.locationDescribe = locationDescribe;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getNetworkLocationType() {
		return networkLocationType;
	}

	public void setNetworkLocationType(String networkLocationType) {
		this.networkLocationType = networkLocationType;
	}

	public Boolean getCellChangeFlag() {
		return cellChangeFlag;
	}

	public void setCellChangeFlag(String cellChangeFlag) {
		if ("true".equalsIgnoreCase(cellChangeFlag) || "yes".equalsIgnoreCase(cellChangeFlag)
				|| "y".equalsIgnoreCase(cellChangeFlag) || "1".equals(cellChangeFlag)) {
			this.cellChangeFlag = Boolean.TRUE;
		} else {
			this.cellChangeFlag = Boolean.FALSE;
		}
	}

	public Integer getOperators() {
		return operators;
	}

	public void setOperators(Integer operators) {
		this.operators = operators;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

}

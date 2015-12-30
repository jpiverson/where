package site.penn.where.entity.bo;

import java.util.Comparator;

/**
 * 软件版本号的封装类
 * 
 * @author penn
 *
 */
public class SoftwareVersionBo implements Comparator<SoftwareVersionBo> {

	private int majorVersion;// 主版本
	private int minorVersion;// 子版本
	private int revisionVersion;// 修正版本

	/**
	 * 
	 * @param versionString
	 *            格式为:x.x.x
	 * @throws Exception
	 *             版本格式错误
	 */
	public SoftwareVersionBo(String versionString) throws Exception {
		try {
			String[] versions = versionString.split("\\.");
			this.majorVersion = Integer.parseInt(versions[0]);
			this.minorVersion = Integer.parseInt(versions[1]);
			this.revisionVersion = Integer.parseInt(versions[2]);
		} catch (Exception e) {
			throw new Exception("版本号格式错误,应为x.x.x");
		}

	}

	@Override
	public int compare(SoftwareVersionBo lhs, SoftwareVersionBo rhs) {
		if (lhs.getMajorVersion() > rhs.getMajorVersion()) {
			// 主版本大
			return 1;
		} else if ((lhs.getMajorVersion() == rhs.getMajorVersion())
				&& (lhs.getMinorVersion() > rhs.getMinorVersion())) {
			// 主版本相同,子版本大
			return 1;
		} else if ((lhs.getMajorVersion() == rhs.getMajorVersion()) && (lhs.getMinorVersion() == rhs.getMinorVersion())
				&& (lhs.getRevisionVersion() > rhs.getRevisionVersion())) {
			// 主版本相同,子版本相同,修正版本大
			return 1;
		} else if ((lhs.getMajorVersion() == rhs.getMajorVersion()) && (lhs.getMinorVersion() == rhs.getMinorVersion())
				&& (lhs.getRevisionVersion() == rhs.getRevisionVersion())) {
			// 主版本,子版本,修正版本全部相同
			return 0;
		} else {
			return -1;
		}
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getRevisionVersion() {
		return revisionVersion;
	}

	public void setRevisionVersion(int revisionVersion) {
		this.revisionVersion = revisionVersion;
	}
}

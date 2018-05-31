package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

public class ErrorInfo {

	private String reqNo;// 工单编号

	private String cName; // 客户名称

	private String cTel;// 联系方式

	private String cErrType;// 故障类型

	private String repTime;// 上报时间

	private String cID;// 客户号

	private String cVersion;// 客户端版本号

	private String sysVersion;// Android系统版本号

	private String sysLog;// 系统日志索引

	private String optLog;// 操作日志索引

	private String errDepict;// 问题描述

	public ErrorInfo getTestErrorInfo() {
		cName = "小测";
		cTel = "13936490309";
		cErrType = "1";
		repTime = TimeUtil.getTime();
		cID = "PB123456";
		cVersion = "2.0";
		sysVersion = "Android";

		return this;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcTel() {
		return cTel;
	}

	public void setcTel(String cTel) {
		this.cTel = cTel;
	}

	public String getcErrType() {
		return cErrType;
	}

	public void setcErrType(String cErrType) {
		this.cErrType = cErrType;
	}

	public String getRepTime() {
		return repTime;
	}

	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}

	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getcVersion() {
		return cVersion;
	}

	public void setcVersion(String cVersion) {
		this.cVersion = cVersion;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getSysLog() {
		return sysLog;
	}

	public void setSysLog(String sysLog) {
		this.sysLog = sysLog;
	}

	public String getOptLog() {
		return optLog;
	}

	public void setOptLog(String optLog) {
		this.optLog = optLog;
	}

	public String getErrDepict() {
		return errDepict;
	}

	public void setErrDepict(String errDepict) {
		this.errDepict = errDepict;
	}

}

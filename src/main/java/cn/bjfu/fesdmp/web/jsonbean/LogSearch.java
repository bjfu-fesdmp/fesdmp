/** 
 * Project Name:fesdmp 
 * File Name:LogSearch.java 
 * Package Name:cn.bjfu.fesdmp.web.bean 
 * Date:2014年7月11日 下午3:13:23 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.web.jsonbean;  

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import cn.bjfu.fesdmp.frame.CustomDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/** 
 * ClassName:LogSearch <br/> 
 * Function: 日志搜索的对应转换Bean. <br/> 
 * Reason:   日志搜索的对应转换Bean. <br/> 
 * Date:     2014年7月11日 下午3:13:23 <br/> 
 * @author   zhangzhaoyu 
 * @version   1.0
 * @since    JDK 1.7 
 * @see       
 */
public class LogSearch implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 6698224941625587845L;

	@Pattern(regexp = "^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$")
	private String userSourceIp;
	private String operateContent;
	private String userName;
	
	private String operationType;
	private String businessType;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@Past
	private Date startTime;
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date endTime;
	
	public LogSearch() {}

	public String getUserSourceIp() {
		return userSourceIp;
	}

	public void setUserSourceIp(String userSourceIp) {
		this.userSourceIp = userSourceIp;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	@Override
	public String toString() {
		return "LogSearch [userSourceIp=" + userSourceIp + ", operateContent="
				+ operateContent + ", userName=" + userName
				+ ", operationType=" + operationType + ", businessType="
				+ businessType + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

}
 
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

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.CustomDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class UserSearch implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 6698224941625587845L;



	private String userName;
	private String userLoginName;
	private String email;
	private String userPhone;
	

	
	public UserSearch() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}


	@Override
	public String toString() {
		return "UserSearch [userName=" + userName + ", userLoginName="
				+ userLoginName + ", email=" + email
				+ ", userPhone=" + userPhone + "]";
	}

}
 
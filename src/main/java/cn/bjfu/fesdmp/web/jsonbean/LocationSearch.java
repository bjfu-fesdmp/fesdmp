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


public class LocationSearch implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 6698224941625587845L;
private String locationName;

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	
	@Override
	public String toString() {
		return "LocationSearch [locationName=" + locationName + "]";
	}

}
 
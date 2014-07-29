
  
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


public class UserGroupSearch implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 6698224941625587845L;



	private String userGroupName;
	

	
	public UserGroupSearch() {}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}


	@Override
	public String toString() {
		return "UserSearch [userGroupName=" + userGroupName + "]";
	}

}
 
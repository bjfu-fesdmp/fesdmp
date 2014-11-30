package cn.bjfu.fesdmp.json;

import java.util.Date;

public class AddUserJson {

	private Integer id;
	private String userName;
	private String userLoginName;
	private String email;
	private String userPhone;
	private String password;
	private String checkPwd;
	private int userGroup;
	private byte isAdmin;
	
	public AddUserJson() {}
	


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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckPwd() {
		return checkPwd;
	}

	public void setCheckPwd(String checkPwd) {
		this.checkPwd = checkPwd;
	}
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

}

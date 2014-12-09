package cn.bjfu.fesdmp.json;

import java.util.Date;



public class UserJson {
	private Integer id;
	private String userName;
	private String userLoginName;
	private String email;
	private String userPhone;
	private String creater;
	private int creater_id;
	private Date createTime;
	private Byte userStatus;
	private Byte isAdmin;
	public UserJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getCreaterId() {
		return creater_id;
	}

	public void setCreaterId(int creater_id) {
		this.creater_id = creater_id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}

	public Byte getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Byte isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}

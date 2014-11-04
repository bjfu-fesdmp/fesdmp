package cn.bjfu.fesdmp.json;

import java.util.Date;



public class UserGroupJson {
	private Integer id;
	private Integer userGroupId;
	private String userGroupName;
	private int creater_id;
	private Date createTime;
//	private int role;
//	private String roleName;
	public UserGroupJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

//	public String getRoleName() {
//		return roleName;
//	}
//
//	public void setRoleName(String roleName) {
//		this.roleName = roleName;
//	}
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
//	public Integer getRole() {
//		return role;
//	}
//
//	public void setRole(Integer role) {
//		this.role = role;
//	}
	
}
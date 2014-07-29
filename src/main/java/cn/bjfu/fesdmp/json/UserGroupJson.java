package cn.bjfu.fesdmp.json;

import java.util.Date;



public class UserGroupJson {
	private Integer id;
	private String userGroupName;
	private int creater_id;
	private Date createTime;
	public UserGroupJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
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

	
}
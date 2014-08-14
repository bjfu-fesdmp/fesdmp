package cn.bjfu.fesdmp.json;

import java.util.Date;



public class RoleJson {
	private Integer id;
	private String roleName;
	private String roleDiscription;
	private int creater_id;
	private Date createTime;
	public RoleJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDiscription() {
		return roleDiscription;
	}

	public void setRoleDiscription(String roleDiscription) {
		this.roleDiscription = roleDiscription;
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
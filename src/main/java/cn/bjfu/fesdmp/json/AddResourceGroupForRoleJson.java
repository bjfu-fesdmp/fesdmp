package cn.bjfu.fesdmp.json;


public class AddResourceGroupForRoleJson {

	private String roleId;
	private String resourceGroupId;
	
	public AddResourceGroupForRoleJson() {}
	


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourceGroupId() {
		return resourceGroupId;
	}

	public void setResourceGroupId(String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}

}

package cn.bjfu.fesdmp.json;


public class AddIndexResourceForUserJson {

	private String userId;
	private String indexResourceId;
	private String resourceGroupId;
	public AddIndexResourceForUserJson() {}
	


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIndexResourceId() {
		return indexResourceId;
	}

	public void setIndexResourceId(String indexResourceId) {
		this.indexResourceId = indexResourceId;
	}
	public String getResourceGroupId() {
		return resourceGroupId;
	}

	public void setResourceGroupId(String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}
}

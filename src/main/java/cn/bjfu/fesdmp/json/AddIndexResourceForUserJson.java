package cn.bjfu.fesdmp.json;


public class AddIndexResourceForUserJson {

	private String userId;
	private String indexResourceId;
	private String resourceGroupId;
	private String locationId;
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



	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}



	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
}

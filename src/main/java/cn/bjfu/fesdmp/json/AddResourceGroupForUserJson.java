package cn.bjfu.fesdmp.json;


public class AddResourceGroupForUserJson {

	private String locationId;


	private String userId;
	private String resourceGroupId;
	
	public AddResourceGroupForUserJson() {}
	


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

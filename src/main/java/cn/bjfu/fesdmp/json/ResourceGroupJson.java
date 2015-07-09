package cn.bjfu.fesdmp.json;

public class ResourceGroupJson {
	private Integer id;
	private String groupParentId;
	private String groupName;
	private String memo;
	private Integer locationId;
	public ResourceGroupJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}

	public String getGroupName () {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getMemo () {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}

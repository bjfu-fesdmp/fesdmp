package cn.bjfu.fesdmp.json;

public class ResourceGroupJson {
	private Integer id;
	private Integer groupParentId;
	private String groupName;
	private String memo;
	
	public ResourceGroupJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(Integer groupParentId) {
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

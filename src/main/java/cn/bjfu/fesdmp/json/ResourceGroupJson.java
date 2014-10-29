package cn.bjfu.fesdmp.json;

public class ResourceGroupJson {
	private Integer id;
	private String groupParentId;
	private String groupName;
	private String memo;
	
	public ResourceGroupJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

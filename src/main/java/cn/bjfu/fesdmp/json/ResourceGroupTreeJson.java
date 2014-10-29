package cn.bjfu.fesdmp.json;



public class ResourceGroupTreeJson {
	private Integer id;
	private Integer groupParentId;
	private String groupName;
	private boolean leaf;
	private String memo;
	public ResourceGroupTreeJson() {}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer  getId() {
		return id;
	}
	public void setId(Integer  id) {
		this.id = id;
	}
	public Integer getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(Integer groupParentId) {
		this.groupParentId = groupParentId;
	}
	public boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
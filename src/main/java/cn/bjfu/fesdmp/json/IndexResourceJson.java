package cn.bjfu.fesdmp.json;

import java.util.Date;

public class IndexResourceJson {
	private Integer id;
	private Integer resourceGroupId;
	private String indexName;
	private String resourceGroupName;
	private String indexEnName;
	private String indexUnit;
	private String indexMemo;
	private int creater_id;
	private Date createTime;
	private int modifier_id;
	private Date modifyTime;
	public IndexResourceJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getResourceGroupId() {
		return resourceGroupId;
	}

	public void setResourceGroupId(Integer resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}
	
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getResourceGroupName() {
		return resourceGroupName;
	}

	public void setResourceGroupName(String resourceGroupName) {
		this.resourceGroupName = resourceGroupName;
	}
	public String getIndexEnName() {
		return indexEnName;
	}

	public void setIndexEnName(String indexEnName) {
		this.indexEnName = indexEnName;
	}


	public String getIndexUnit() {
		return indexUnit;
	}

	public void setIndexUnit(String indexUnit) {
		this.indexUnit = indexUnit;
	}

	public String getIndexMemo() {
		return indexMemo;
	}

	public void setIndexMemo(String indexMemo) {
		this.indexMemo = indexMemo;
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

	public int getModifierId() {
		return modifier_id;
	}

	public void setModifierId(int modifier_id) {
		this.modifier_id = modifier_id;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}

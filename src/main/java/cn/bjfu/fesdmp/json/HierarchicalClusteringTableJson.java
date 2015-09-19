package cn.bjfu.fesdmp.json;


public class HierarchicalClusteringTableJson {
	private String tableName;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	private String id;
	public HierarchicalClusteringTableJson() {}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
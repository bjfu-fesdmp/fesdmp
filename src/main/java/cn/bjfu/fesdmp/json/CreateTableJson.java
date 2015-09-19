package cn.bjfu.fesdmp.json;


public class CreateTableJson {
	private String indexEnName;
	private String id;
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

	private String year;
	public CreateTableJson() {}

	public String getIndexEnName() {
		return indexEnName;
	}

	public void setIndexEnName(String indexEnName) {
		this.indexEnName = indexEnName;
	}
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}
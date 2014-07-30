package cn.bjfu.fesdmp.web.jsonbean;

import java.io.Serializable;

public class IndexResourceSearch implements Serializable {

	private static final long serialVersionUID = -7038065932395943279L;

	private String indexName;
	private String indexEnName;
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
	public String getIndexName () {
		return indexName;
	}
	
	public void setIndexEnName(String indexEnName) {
		this.indexEnName = indexEnName;
	}
	
	public String getIndexEnName() {
		return indexEnName;
	}
	
	@Override
	public String toString() {
		return "IndexResourceSearch [indexName=" + indexName + ", indexEnName="
				+ indexEnName + "]";
	}
}

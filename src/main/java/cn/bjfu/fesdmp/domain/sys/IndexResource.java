package cn.bjfu.fesdmp.domain.sys;  

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class IndexResource implements Serializable{

	private static final long serialVersionUID = 4563893285739204858L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer indexResourceId;
	@Column (nullable = false)
	private String indexName;
	@Column (nullable = false)
	private String indexEnName;
	@Column (nullable = false)
	private String indexUnit;
	@Column 
	private String indexMemo;
	@OneToOne
	@JoinColumn(name = "creater_id")
	private User creater;
	@Column(nullable = false)
	private Date createTime;
	@OneToOne
	@JoinColumn(name = "modifier_id")
	private User modifier;
	@Column(nullable = false)
	private Date modifyTime;
	
	public IndexResource() {}
	
	public Integer getIndexResouceId() {
		return indexResourceId;
	}
	
	public void setIndexResouceId(Integer indexResourceId) {
		this.indexResourceId = indexResourceId;
	}
	
	public String getIndexName() {
		return indexName;
	}
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
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
	
	public User getCreater() {
		return creater;
	}
	
	public void setCreater(User creater) {
		this.creater = creater;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public User getModifier() {
		return modifier;
	}
	
	public void setModifier(User modifier) {
		this.modifier = modifier;
	}
	
	public Date getModifyTime(Date modifyTime) {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((indexResourceId == null) ? 0 : indexResourceId.hashCode());
		result = prime * result + ((indexName == null) ? 0 : indexName.hashCode());
		result = prime * result + ((indexEnName == null) ? 0 : indexEnName.hashCode());
		result = prime * result + ((indexUnit == null) ? 0 : indexUnit.hashCode());
		result = prime * result
				+ ((indexMemo == null) ? 0 : indexMemo.hashCode());
		result = prime * result
				+ ((creater == null) ? 0 : creater.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result
				+ ((modifyTime == null) ? 0 : modifyTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndexResource other = (IndexResource) obj;
		if (indexResourceId == null) {
			if (other.indexResourceId != null)
				return false;
		} else if (!indexResourceId.equals(other.indexResourceId))
			return false;
		if (indexName == null) {
			if (other.indexName != null)
				return false;
		} else if (!indexName.equals(other.indexName))
			return false;
		if (indexEnName == null) {
			if (other.indexEnName != null)
				return false;
		} else if (!indexEnName.equals(other.indexEnName))
			return false;
		if (indexUnit== null) {
			if (other.indexUnit != null)
				return false;
		} else if (!indexUnit.equals(other.indexUnit))
			return false;
		if (indexMemo == null) {
			if (other.indexMemo != null)
				return false;
		} else if (!indexMemo.equals(other.indexMemo))
			return false;
		if (creater == null) {
			if (other.creater != null)
				return false;
		} else if (!creater.equals(other.creater))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (modifyTime == null) {
			if (other.modifyTime != null)
				return false;
		} else if (!modifyTime.equals(other.modifyTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [indexResourceId=" + indexResourceId + ", indexName=" + indexName + ", indexEnName="
				+ indexEnName + ", indexUnit=" + indexUnit + ", indexMemo=" + indexMemo
				+ ", creater=" + creater + ", createTime=" + createTime
				+ ", modifier=" + modifier + ", modifyTime=" + modifyTime + "]";
	}
}

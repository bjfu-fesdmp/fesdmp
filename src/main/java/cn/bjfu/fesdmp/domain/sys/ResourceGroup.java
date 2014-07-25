package cn.bjfu.fesdmp.domain.sys;  

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_resource_group")
public class ResourceGroup implements Serializable{

	private static final long serialVersionUID = -3859481618494568413L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer groupId;
	@Column(nullable = false)
	private Integer groupParentId;
	@Column(nullable = false)
	private String groupName;
	private String memo;
	
	public ResourceGroup() {}
	
	public Integer getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public Integer getGroupParentId() {
		return groupParentId;
	}
	
	public void setGroupParentId(Integer groupParentId) {
		this.groupParentId = groupParentId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupParentId == null) ? 0 : groupParentId.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
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
		ResourceGroup other = (ResourceGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupParentId == null) {
			if (other.groupParentId != null)
				return false;
		} else if (!groupParentId.equals(other.groupParentId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "ResouceGroup [groupId=" + groupId + ", groupParentId=" + groupParentId + ", groupName="
				+ groupName + ", memo=" + memo +  "]";
	}
}
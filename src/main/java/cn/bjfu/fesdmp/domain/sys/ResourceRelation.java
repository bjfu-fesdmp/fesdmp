package cn.bjfu.fesdmp.domain.sys;  

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_resource_relation")
public class ResourceRelation implements Serializable {

	private static final long serialVersionUID = -8664489501861618119L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "group_id")
	private Integer resourceGroupId;
	@OneToOne
	@JoinColumn(name = "index_resource_id")
	private Integer indexResourceId;
	
	public ResourceRelation() {}
	
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
	 
	public Integer getIndexResourceId() {
		return indexResourceId;
	}
	
	public void setIndexResourceId(Integer indexResourceId) {
		this.indexResourceId = indexResourceId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resourceGroupId == null) ? 0 : resourceGroupId.hashCode());
		result = prime * result + ((indexResourceId == null) ? 0 : indexResourceId.hashCode());
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
		ResourceRelation other = (ResourceRelation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resourceGroupId == null) {
			if (other.resourceGroupId != null)
				return false;
		} else if (!resourceGroupId.equals(other.resourceGroupId))
			return false;
		if (indexResourceId == null) {
			if (other.indexResourceId != null)
				return false;
		} else if (!indexResourceId.equals(other.indexResourceId))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "ResouceGroup [id=" + id + ", resourceGroupId=" + resourceGroupId + ", indexResourceId="
				+ indexResourceId + "]";
	}
}
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
	@JoinColumn(name = "resource_group_id")
	private ResourceGroup resourceGroup;
	@OneToOne
	@JoinColumn(name = "index_resource_id")
	private IndexResource indexResource;
	
	public ResourceRelation() {}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ResourceGroup getResourceGroupId() {
		return resourceGroup;
	}
	
	public void setResourceGroupId(ResourceGroup resourceGroup) {
		this.resourceGroup = resourceGroup;
	}
	 
	public IndexResource getIndexResourceId() {
		return indexResource;
	}
	
	public void setIndexResourceId(IndexResource indexResource) {
		this.indexResource = indexResource;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resourceGroup == null) ? 0 : resourceGroup.hashCode());
		result = prime * result + ((indexResource == null) ? 0 : indexResource.hashCode());
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
		if (resourceGroup == null) {
			if (other.resourceGroup != null)
				return false;
		} else if (!resourceGroup.equals(other.resourceGroup))
			return false;
		if (indexResource == null) {
			if (other.indexResource != null)
				return false;
		} else if (!indexResource.equals(other.indexResource))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "ResouceGroup [id=" + id + ", resourceGroup=" + resourceGroup + ", indexResource="
				+ indexResource + "]";
	}
}
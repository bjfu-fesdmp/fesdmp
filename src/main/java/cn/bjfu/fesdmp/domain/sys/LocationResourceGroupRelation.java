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
@Table(name = "t_location_resource_relation")
public class LocationResourceGroupRelation implements Serializable {

	private static final long serialVersionUID = -8664489501861618119L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "resource_group_id")
	private ResourceGroup resourceGroup;
	@OneToOne
	@JoinColumn(name = "location_id")
	private Location location;
	
	public LocationResourceGroupRelation() {}
	
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
	 
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resourceGroup == null) ? 0 : resourceGroup.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		LocationResourceGroupRelation other = (LocationResourceGroupRelation) obj;
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
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "ResouceGroup [id=" + id + ", resourceGroup=" + resourceGroup + ", location="
				+ location + "]";
	}
}
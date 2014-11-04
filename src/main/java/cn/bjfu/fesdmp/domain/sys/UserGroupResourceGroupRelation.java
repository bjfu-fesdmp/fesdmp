/** 
 * Project Name:fesdmp 
 * File Name:RoleFunctionRelation.java 
 * Package Name:cn.bjfu.fesdmp.domain.sys 
 * Date:2014年7月24日 下午9:55:35 
 * Copyright (c) 2014, 1153405224@qq.com All Rights Reserved. 
 * 
*/  
  
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

/** 
 * ClassName:UserGroupRelation <br/> 
 * Function: 用户组资源组映射表. <br/> 
 * Reason:  用户组资源组映射表. <br/> 
 * Date:     2014年7月24日 下午9:55:35 <br/> 
 * @author   LuoYangBjfu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Entity
@Table(name = "t_user_group_resource_group_relation")
public class UserGroupResourceGroupRelation implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 4240247701595830143L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "userGroup_id")
	private UserGroup userGroup;
	@OneToOne
	@JoinColumn(name = "resourceGroup_id")
	private ResourceGroup resourceGroup;
	public UserGroupResourceGroupRelation() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public ResourceGroup getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(ResourceGroup resourceGroup) {
		this.resourceGroup = resourceGroup;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceGroup == null) ? 0 : resourceGroup.hashCode());
		result = prime * result + ((userGroup == null) ? 0 : userGroup.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		
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
		UserGroupResourceGroupRelation other = (UserGroupResourceGroupRelation) obj;
		if (resourceGroup == null) {
			if (other.resourceGroup != null)
				return false;
		} else if (!resourceGroup.equals(other.resourceGroup))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userGroup == null) {
			if (other.userGroup != null)
				return false;
		} else if (!userGroup.equals(other.userGroup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoleResourceGroupRelation [id=" + id + ", resourceGroup=" + resourceGroup + ", userGroup=" + userGroup+ "]";
	}
}
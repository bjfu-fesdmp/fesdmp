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
 * Function: 用户资源组映射表. <br/> 
 * Reason:  用户资源组映射表. <br/> 
 * Date:     2014年7月24日 下午9:55:35 <br/> 
 * @author   LuoYangBjfu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Entity
@Table(name = "t_user_resource_group_relation")
public class UserResourceGroupRelation implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 4240247701595830143L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	@OneToOne
	@JoinColumn(name = "resourceGroup_id")
	private ResourceGroup resourceGroup;
	public UserResourceGroupRelation() {}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceGroup == null) ? 0 : resourceGroup.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserResourceGroupRelation other = (UserResourceGroupRelation) obj;
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserResourceGroupRelation [id=" + id + ", resourceGroup=" + resourceGroup + ", user=" + user+ "]";
	}
}
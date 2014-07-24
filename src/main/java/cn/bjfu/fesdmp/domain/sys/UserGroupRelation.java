/** 
 * Project Name:fesdmp 
 * File Name:UserGroupRelation.java 
 * Package Name:cn.bjfu.fesdmp.domain.sys 
 * Date:2014年7月24日 下午9:25:36 
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
 * Function: 用户组表. <br/> 
 * Reason:   用户组表. <br/> 
 * Date:     2014年7月24日 下午9:25:36 <br/> 
 * @author   LuoYangBjfu 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Entity
@Table(name = "t_user_group_relation")
public class UserGroupRelation implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 4240247701595830143L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "user_group_id")
	private UserGroup userGroup;
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;
	public UserGroupRelation() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userGroup == null) ? 0 : userGroup.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		UserGroupRelation other = (UserGroupRelation) obj;
		if (userGroup == null) {
			if (other.userGroup != null)
				return false;
		} else if (!userGroup.equals(other.userGroup))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserGroupRelation [id=" + id + ", userGroup=" + userGroup + ", role=" + role+ "]";
	}
}
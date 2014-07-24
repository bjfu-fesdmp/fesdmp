/** 
 * Project Name:fesdmp 
 * File Name:Function.java 
 * Package Name:cn.bjfu.fesdmp.domain.sys 
 * Date:2014年7月24日 下午9:40:40 
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
 * ClassName:User <br/> 
 * Function: 功能权限表. <br/> 
 * Reason:   功能权限表. <br/> 
 * Date:     2014年7月24日 下午9:40:40 <br/> 
 * @author   LuoYangBjfu  
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Entity
@Table(name = "t_function")
public class Function implements Serializable {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 4240247701595830143L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Function parent;
	@Column(nullable = false)
	private String functionName;
	private String functionUrl;
	private String functionImage;
	@Column(columnDefinition = " tinyint default 1 ", nullable = false)
	private Byte functionFlag;
	public Function() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Function getParent() {
		return parent;
	}

	public void setParent(Function parent) {
		this.parent = parent;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getFunctionImage() {
		return functionImage;
	}

	public void setFunctionImage(String functionImage) {
		this.functionImage = functionImage;
	}
	
	public Byte getFunctionFlag() {
		return functionFlag;
	}

	public void setFunctionFlag(Byte functionFlag) {
		this.functionFlag = functionFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((functionName == null) ? 0 : functionName.hashCode());
		result = prime * result
				+ ((functionUrl == null) ? 0 : functionUrl.hashCode());
		result = prime * result
				+ ((functionImage == null) ? 0 : functionImage.hashCode());
		result = prime * result
				+ ((functionFlag == null) ? 0 : functionFlag.hashCode());
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
		Function other = (Function) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (functionName == null) {
			if (other.functionName != null)
				return false;
		} else if (!functionName.equals(other.functionName))
			return false;
		if (functionUrl == null) {
			if (other.functionUrl != null)
				return false;
		} else if (!functionUrl.equals(other.functionUrl))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (functionImage == null) {
			if (other.functionImage != null)
				return false;
		} else if (!functionImage.equals(other.functionImage))
			return false;
		if (functionFlag == null) {
			if (other.functionFlag != null)
				return false;
		} else if (!functionFlag.equals(other.functionFlag))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Function [id=" + id +  ", functionName="
				+ functionName + ", functionUrl=" + functionUrl +
				", functionImage=" + functionImage
				+ ", functionFlag=" + functionFlag + "]";
	}
}
 
package cn.bjfu.fesdmp.web.jsonbean;

import java.io.Serializable;

public class ResourceGroupSearch implements Serializable{
	
	private static final long serialVersionUID = 5294740389041862756L;
	
	private String groupName;
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getGroupName () {
		return groupName;
	}
	
	@Override
	public String toString() {
		return "ResourceGroupSearch [groupName=" + groupName + "]";
	}
}

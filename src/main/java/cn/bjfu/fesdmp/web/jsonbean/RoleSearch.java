
  
package cn.bjfu.fesdmp.web.jsonbean;  

import java.io.Serializable;

public class RoleSearch implements Serializable {

	private static final long serialVersionUID = -5119945914193864888L;
	private String roleName;	
	
	public RoleSearch() {}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "RoleSearch [roleName=" + roleName + "]";
	}

}
 
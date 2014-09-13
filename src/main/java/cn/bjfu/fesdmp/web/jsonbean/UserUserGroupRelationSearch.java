package cn.bjfu.fesdmp.web.jsonbean;

import java.io.Serializable;

public class UserUserGroupRelationSearch implements Serializable{
	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 6698224941625587845L;



	private int user_id;
	

	
	public UserUserGroupRelationSearch() {}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}


	@Override
	public String toString() {
		return "UserUserGroupRelationSearch [user_Id=" + user_id + "]";
	}

}

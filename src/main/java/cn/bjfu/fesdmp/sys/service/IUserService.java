
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


public interface IUserService {

	public abstract void addUser(User user,int userGroupId);
	public abstract void modifyUser(AddUserJson addUserJson);
	public abstract void deleteUser(int id);
	public abstract List<User> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<User> page);
	public abstract List<User> queryByCondition(final Object condition, IOrder order,
			Pagination<User> page, JoinMode joinMode);
	public abstract User findByKey(int id);
	public abstract User findByUserLoginName(String userLoginName);
	public abstract boolean checkUserName(String userName);
	public abstract boolean checkUserLoginName(String userLoginName);
}
 
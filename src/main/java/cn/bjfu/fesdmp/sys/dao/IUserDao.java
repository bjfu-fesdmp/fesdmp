
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


public interface IUserDao extends IGenericDao<User> {

	public abstract User findByUserLoginName(String userLoginName);
	public abstract boolean checkUserName(String userName);
	public abstract boolean checkUserLoginName(String userLoginName);
	public abstract boolean checkIfHaveAuthority(int userId,int indexResourceId);
	public abstract boolean checkIfIsTemporaryManager(int userId,int resourceId);
	public abstract boolean checkIfIsTemporaryManager(int userId);
}
 
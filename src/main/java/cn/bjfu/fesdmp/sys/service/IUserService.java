
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


public interface IUserService {

	public abstract void addUser(User User);
	public abstract void deleteUser(User User);
	public abstract List<User> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<User> page);
	public abstract List<User> queryByCondition(final Object condition, IOrder order,
			Pagination<User> page, JoinMode joinMode);
	public List<User> findByCondtinGetCreater(UserSearch condition,
			IOrder order, Pagination<User> page, JoinMode joinMode);
}
 
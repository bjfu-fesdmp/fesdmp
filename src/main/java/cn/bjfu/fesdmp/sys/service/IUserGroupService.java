
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;


import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;

import cn.bjfu.fesdmp.utils.Pagination;



public interface IUserGroupService {

	public abstract void addUserGroup(UserGroup userGroup);
	public abstract void deleteUserGroup(UserGroup userGroup);
	public abstract List<UserGroup> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<UserGroup> page);
	public abstract List<UserGroup> queryByCondition(final Object condition, IOrder order,
			Pagination<UserGroup> page, JoinMode joinMode);
}
 
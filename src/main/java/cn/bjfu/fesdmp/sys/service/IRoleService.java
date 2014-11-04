
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddResourceGroupForRoleJson;
import cn.bjfu.fesdmp.utils.Pagination;


public interface IRoleService {

	public abstract void addRole(Role role);
	public abstract void addResourceGroupForRole(AddResourceGroupForRoleJson addResourceGroupForRoleJson);
	public abstract void deleteRole(int id);
	public abstract List<Role> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<Role> page);
	public abstract List<Role> queryByCondition(final Object condition, IOrder order,
			Pagination<Role> page, JoinMode joinMode);

}
 
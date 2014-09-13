
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;

public interface IResourceGroupService {

	public abstract void addResourceGroup(ResourceGroup resourceGroup);
	public abstract void deleteResourceGroup(int id);
	public abstract void modifyResourceGroup(ResourceGroup resourceGroup);
	public abstract List<ResourceGroup> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<ResourceGroup> page);
	public abstract List<ResourceGroup> queryByCondition(final Object condition, IOrder order,
			Pagination<ResourceGroup> page, JoinMode joinMode);

}
 
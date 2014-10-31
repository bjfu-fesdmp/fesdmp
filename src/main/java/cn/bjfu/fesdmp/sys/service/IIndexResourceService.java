 
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.CreateTableJson;
import cn.bjfu.fesdmp.utils.Pagination;
//import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

public interface IIndexResourceService{

	public abstract void addIndexResource(IndexResource indexResource,int resourceGroupId);
	public abstract void modifyIndexResource(IndexResource indexResource);
	public abstract void deleteIndexResource(int id);
	public abstract void addTableByYear(CreateTableJson createTableJson);
	public abstract List<IndexResource> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<IndexResource> page);
	public abstract List<IndexResource> queryByCondition(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode);
	public abstract List<IndexResource> queryByConditionAndResourceGroupId(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String resourceGroupId);
	public abstract IndexResource findByKey(int id);
	public abstract List<IndexResource> queryByResourceGroupId(int resourceGroupId);
}
 
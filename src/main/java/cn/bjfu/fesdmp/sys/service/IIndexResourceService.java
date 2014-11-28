 
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddIndexResourceForUserJson;
import cn.bjfu.fesdmp.json.CreateTableJson;
import cn.bjfu.fesdmp.utils.Pagination;
//import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

public interface IIndexResourceService{

	public abstract void addIndexResource(IndexResource indexResource,int resourceGroupId);
	public abstract void addIndexResourceForUser(AddIndexResourceForUserJson addIndexResourceForUserJson);
	public abstract void modifyIndexResource(IndexResource indexResource);
	public abstract void deleteIndexResource(int id);
	public abstract void addTableByYear(CreateTableJson createTableJson);
	public abstract List<IndexResource> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<IndexResource> page);
	public abstract List<IndexResource> queryByCondition(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode);
	public abstract List<IndexResource> queryByConditionAndResourceGroupId(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String resourceGroupId);
	public abstract List<IndexResource> queryByConditionAndUserId(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String userId);
	public abstract IndexResource findByKey(int id);
	public abstract List<IndexResource> queryByResourceGroupId(int resourceGroupId);
	public abstract List<IndexResource> getIndexResourceListNotInThisUser(String userId,String resourceGroupId);
	public abstract void deleteIndexResourceForUser(String id,String userId);
	public abstract boolean checkIndexResourceEnName(String indexResourceEnName);
	public abstract boolean checkIndexResourceName(String indexResourceName);
	public abstract boolean checkYear(String year,String indexResoure);
	public abstract boolean checkIfHaveTable(String ids);
}
 
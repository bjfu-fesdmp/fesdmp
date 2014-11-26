 package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;

public interface IIndexResourceDao extends IGenericDao<IndexResource> {
/*
	public abstract List<IndexResource> findByCondtinWithOperationTime(final LogSearch condition, 
			IOrder order, Pagination<IndexResource> page, JoinMode joinMode);*/
	public abstract void createResourceListByTime(String resource,String year);
	public abstract String findUnitByIndex(String index);
	public abstract List<IndexResource> queryByConditionAndResourceGroupId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String resourceGroupId);
	public abstract List<IndexResource> queryByConditionAndUserId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String userId);
	public abstract List<IndexResource> queryByResourceGroupId(int resourceGroupId);
	public abstract List<IndexResource> getIndexResourceListNotInThisUser(String userId,String resourceGroupId);
	public abstract boolean checkIndexResourceName(String indexResourceName);
	public abstract boolean checkIndexResourceEnName(String indexResourceEnName);
	public abstract IndexResource findByEnName(String indexResourceEnName);
	public abstract boolean checkYear(String year,String indexResoure);
}
 
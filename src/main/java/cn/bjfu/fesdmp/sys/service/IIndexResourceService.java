 
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;
//import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

public interface IIndexResourceService{

	public abstract void addIndResource(IndexResource indexResource);
//	public abstract void deleteIndResource(IndexResource indexResource);
	public abstract void deleteIndResource(int id);
	public abstract List<IndexResource> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<IndexResource> page);
	public abstract List<IndexResource> queryByCondition(final Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode);
	public abstract IndexResource findByKey(int id);
/*	public List<IndexResource> queryByCondtinWithOperationTime(LogSearch condition,
			IOrder order, Pagination<IndexResource> page, JoinMode joinMode);*/
}
 
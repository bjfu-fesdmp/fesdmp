 package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

public interface IIndexResourceDao extends IGenericDao<IndexResource> {
/*
	public abstract List<IndexResource> findByCondtinWithOperationTime(final LogSearch condition, 
			IOrder order, Pagination<IndexResource> page, JoinMode joinMode);*/
	public abstract void createResourceListByTime(String resource,String year);
}
 
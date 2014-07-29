
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.utils.Pagination;
//import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Service
@Transactional
public class IndexResourceService implements IIndexResourceService {

	@Autowired
	private IIndexResourceDao indexDao;
	
	@Override
	public void addIndResource(IndexResource indexResource) {
		this.indexDao.insert(indexResource);
	}

	@Override
	public void deleteIndResource(IndexResource indexResource) {
		this.indexDao.delete(indexResource);
	}

	@Transactional(readOnly = true)
	@Override
	public List<IndexResource> queryAll(IOrder order) {
		return this.indexDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<IndexResource> page) {
		this.indexDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<IndexResource> queryByCondition(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode) {
		return this.indexDao.findByCondition(condition, order, page, joinMode);
	}

/*	@Override
	public List<IndexResource> queryByCondtinWithOperationTime(LogSearch condition,
			IOrder order, Pagination<IndexResource> page, JoinMode joinMode) {
		return this.indexDao.findByCondtinWithOperationTime(condition, order, page, joinMode);
	}*/

}
 
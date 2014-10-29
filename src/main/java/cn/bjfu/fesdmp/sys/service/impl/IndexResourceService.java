
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.Date;
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
	private IIndexResourceDao indexResourceDao;
	
	@Override
	public void addIndResource(IndexResource indexResource) {
		this.indexResourceDao.insert(indexResource);
		this.indexResourceDao.createResourceListByTime(indexResource.getIndexEnName(), "2014");
	}

	@Override
	public void deleteIndResource(int id) {
		this.indexResourceDao.delete(this.indexResourceDao.findByKey(id));
	}

	@Transactional(readOnly = true)
	@Override
	public List<IndexResource> queryAll(IOrder order) {
		return this.indexResourceDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<IndexResource> page) {
		this.indexResourceDao.findByCondition(condition, order, page);
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public List<IndexResource> queryByCondition(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode) {
		return this.indexResourceDao.findByCondition(condition, order, page, joinMode);
	}

	public  IndexResource findByKey(int id){
		return this.indexResourceDao.findByKey(id);
	}

	@Override
	public void modifyIndResource(IndexResource indexResource) {
		IndexResource indexResourceNew = this.indexResourceDao.findByKey(indexResource.getId());
		indexResourceNew.setIndexName(indexResource.getIndexName());
		indexResourceNew.setIndexEnName(indexResource.getIndexEnName());
		indexResourceNew.setIndexUnit(indexResource.getIndexUnit());
		indexResourceNew.setIndexMemo(indexResource.getIndexMemo());
		indexResourceNew.setModifier(indexResource.getModifier());
		indexResourceNew.setModifyTime(new Date());
		this.indexResourceDao.update(indexResourceNew);
		
	}

}
 
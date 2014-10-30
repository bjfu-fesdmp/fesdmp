
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IResourceRelationDao;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.utils.Pagination;
//import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Service
@Transactional
public class IndexResourceService implements IIndexResourceService {

	@Autowired
	private IIndexResourceDao indexResourceDao;
	@Autowired
	private IResourceGroupDao resourceGroupDao;
	@Autowired
	private IResourceRelationDao resourceRelationDao;
	@Override
	public void addIndResource(IndexResource indexResource,int resourceGroupId) {
		ResourceGroup resourceGroup=this.resourceGroupDao.findByKey(resourceGroupId);
		ResourceRelation resourceRelation=new ResourceRelation();
		this.indexResourceDao.insert(indexResource);
		this.indexResourceDao.createResourceListByTime(indexResource.getIndexEnName(), "2014");
		resourceRelation.setIndexResourceId(indexResource);
		resourceRelation.setResourceGroupId(resourceGroup);
		this.resourceRelationDao.insert(resourceRelation);
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
	@Override
	public List<IndexResource> queryByConditionAndResourceGroupId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String resourceGroupId) {
		return this.indexResourceDao.queryByConditionAndResourceGroupId(condition, order, page, joinMode,resourceGroupId);
	}
}
 

package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.domain.sys.ResourceTable;
import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddIndexResourceForUserJson;
import cn.bjfu.fesdmp.json.CreateTableJson;
import cn.bjfu.fesdmp.sys.dao.IDataDao;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IResourceRelationDao;
import cn.bjfu.fesdmp.sys.dao.IResourceTableDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserIndexRelationDao;
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
	@Autowired
	private IDataDao dataDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserIndexRelationDao userIndexRelationDao;
	@Autowired
	private IResourceTableDao resourceTableDao;
	@Override
	public void addIndexResource(IndexResource indexResource,int resourceGroupId) {
		ResourceGroup resourceGroup=this.resourceGroupDao.findByKey(resourceGroupId);
		ResourceRelation resourceRelation=new ResourceRelation();
		this.indexResourceDao.insert(indexResource);
		resourceRelation.setIndexResourceId(indexResource);
		resourceRelation.setResourceGroupId(resourceGroup);
		this.resourceRelationDao.insert(resourceRelation);
	}
	@Override
	public void addTableByYear(CreateTableJson createTableJson){
		ResourceTable resourceTable=new ResourceTable();
		IndexResource indexResource=this.indexResourceDao.findByEnName(createTableJson.getIndexEnName());
		resourceTable.setIndexResource(indexResource);
		resourceTable.setYear(Integer.valueOf(createTableJson.getYear()));
		this.indexResourceDao.createResourceListByTime(createTableJson.getIndexEnName(), createTableJson.getYear());
		this.resourceTableDao.insert(resourceTable);
	}

	@Override
	public void deleteIndexResource(int id) {
		ResourceRelation resourceRelation=new ResourceRelation();
		resourceRelation=this.resourceRelationDao.findByIndexResourceId(id);
		List<UserIndexRelation> list=this.userIndexRelationDao.findUserIndexRelationByIndexResourceId(String.valueOf(id));
		for(int i=0;i<list.size();i++)
			this.userIndexRelationDao.delete(list.get(i));
		this.resourceRelationDao.delete(resourceRelation);
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
	public void modifyIndexResource(IndexResource indexResource) {
		IndexResource indexResourceNew = this.indexResourceDao.findByKey(indexResource.getId());
		if(!indexResourceNew.getIndexEnName().equalsIgnoreCase(indexResource.getIndexEnName()))
		this.dataDao.modifyTableName(indexResourceNew.getIndexEnName(), indexResource.getIndexEnName());
		indexResourceNew.setIndexName(indexResource.getIndexName());
		indexResourceNew.setIndexEnName(indexResource.getIndexEnName());
		indexResourceNew.setIndexUnit(indexResource.getIndexUnit());
		if(indexResource.getIndexMemo()!=null)
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
	@Override
	public List<IndexResource> queryByConditionAndUserId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String userId) {
		return this.indexResourceDao.queryByConditionAndUserId(condition, order, page, joinMode,userId);
	}
	@Override
	public List<IndexResource> queryByResourceGroupId(int resourceGroupId) {
		return this.indexResourceDao.queryByResourceGroupId(resourceGroupId);
	}
	@Transactional(readOnly = true)
	@Override
	public List<IndexResource> getIndexResourceListNotInThisUser(String userId,String resourceGroupId) {
		return this.indexResourceDao.getIndexResourceListNotInThisUser(userId,resourceGroupId);
	}
	@Override
	public void addIndexResourceForUser(AddIndexResourceForUserJson addIndexResourceForUserJson){
		UserIndexRelation userIndexRelation=new UserIndexRelation();
		userIndexRelation.setUser(this.userDao.findByKey(Integer.parseInt(addIndexResourceForUserJson.getUserId())));
		userIndexRelation.setIndexResource(this.indexResourceDao.findByKey(Integer.parseInt(addIndexResourceForUserJson.getIndexResourceId())));
		
		this.userIndexRelationDao.insert(userIndexRelation);
	}
	@Override
	public void deleteIndexResourceForUser(String id,String userId) {
		UserIndexRelation userIndexRelation=this.userIndexRelationDao.findUserIndexRelationByBothId(id,userId);
		this.userIndexRelationDao.delete(userIndexRelation);
	}
	@Override
	public boolean checkIndexResourceName(String indexResourceName){
		return this.indexResourceDao.checkIndexResourceName(indexResourceName);
	}
	@Override
	public boolean checkIndexResourceEnName(String indexResourceEnName){
		return this.indexResourceDao.checkIndexResourceEnName(indexResourceEnName);
	}
	@Override
	public boolean checkYear(String year,String indexResoure){
		return this.indexResourceDao.checkYear(year,indexResoure);
	}
	@Override
	public boolean checkIfHaveTable(String ids){
		return this.indexResourceDao.checkIfHaveTable(ids);
	}
}
 
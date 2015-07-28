
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.LocationResourceGroupRelation;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;
import cn.bjfu.fesdmp.domain.sys.UserResourceGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddResourceGroupForUserJson;
import cn.bjfu.fesdmp.json.ResourceGroupJson;
import cn.bjfu.fesdmp.sys.dao.ILocationDao;
import cn.bjfu.fesdmp.sys.dao.ILocationResourceRelationDao;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserResourceGroupRelationDao;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.Pagination;


@Service
@Transactional
public class ResourceGroupService implements IResourceGroupService {

	@Autowired
	private IResourceGroupDao resourceGroupDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserResourceGroupRelationDao userResourceGroupRelationDao;
	@Autowired
	private ILocationDao locationDao;
	@Autowired
	private ILocationResourceRelationDao locationResourceRelationDao;
	@Override
	public void addResourceGroup(ResourceGroupJson resourceGroupJson) {
		ResourceGroup resourceGroup=new ResourceGroup();
		LocationResourceGroupRelation locationResourceGroupRelation=new LocationResourceGroupRelation();
		resourceGroup.setGroupName(resourceGroupJson.getGroupName());
		resourceGroup.setGroupParentId(Integer.parseInt(resourceGroupJson.getGroupParentId()));
		if(resourceGroupJson.getMemo()!=null)
		resourceGroup.setMemo(resourceGroupJson.getMemo());
		this.resourceGroupDao.insert(resourceGroup);
		locationResourceGroupRelation.setResourceGroupId(resourceGroup);;
		locationResourceGroupRelation.setLocation(this.locationDao.findByKey(resourceGroupJson.getLocationId()));
		
		this.locationResourceRelationDao.insert(locationResourceGroupRelation);

	}

	@Override
	public void deleteResourceGroup(int id) {
		
		List<UserResourceGroupRelation> list=this.userResourceGroupRelationDao.findUserResourceGroupByResourceGroupId(String.valueOf(id));
		for(int i=0;i<list.size();i++)
			this.userResourceGroupRelationDao.delete(list.get(i));
		this.resourceGroupDao.delete(this.resourceGroupDao.findByKey(id));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ResourceGroup> queryAll(IOrder order) {
		return this.resourceGroupDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<ResourceGroup> page) {
		this.resourceGroupDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ResourceGroup> queryByCondition(Object condition, IOrder order,
			Pagination<ResourceGroup> page, JoinMode joinMode) {
		return this.resourceGroupDao.findByCondition(condition, order, page, joinMode);
	}

	@Override
	public void modifyResourceGroup(ResourceGroup resourceGroup) {
		ResourceGroup resourceGroupNew = this.resourceGroupDao.findByKey(resourceGroup.getId());
		resourceGroupNew.setGroupName(resourceGroup.getGroupName());
		resourceGroupNew.setMemo(resourceGroup.getMemo());
		this.resourceGroupDao.update(resourceGroupNew);
	}
	
	
	public List<ResourceGroup> findResourceGroupByParentIdAndUserId(int parentId,int userId){
		return this.resourceGroupDao.findResourceGroupByParentIdAndUserId(parentId,userId);	
	}
	public List<ResourceGroup> findResourceGroupByUserId(String userId){
		return this.resourceGroupDao.findResourceGroupByUserId(userId);	
	}
	public List<ResourceGroup> findResourceGroupByUserIdAndLocation(String userId,int locationId){
		return this.resourceGroupDao.findResourceGroupByUserIdAndLocation(userId,locationId);	
	}
	public List<ResourceGroup> findResourceGroupInThisLocation(int locationId){
		return this.resourceGroupDao.findResourceGroupInThisLocation(locationId);	
	}
	public boolean ifHaveChild(int id){
		return this.resourceGroupDao.ifHaveChild(id);	
	}
	
	public  boolean checkIfHaveIndexResource(int id){
		return this.resourceGroupDao.checkIfHaveIndexResource(id);
	}
	@Transactional(readOnly = true)
	@Override
	public List<ResourceGroup> findResourceGroupNotInThisUser(String userId) {
		return this.resourceGroupDao.findResourceGroupNotInThisUser(userId);
	}
	
	@Override
	public void addResourceGroupForUser(AddResourceGroupForUserJson addResourceGroupForUserJson) {
		UserResourceGroupRelation userResourceGroupRelation=new UserResourceGroupRelation();
		userResourceGroupRelation.setUser(this.userDao.findByKey(Integer.parseInt(addResourceGroupForUserJson.getUserId())));
		userResourceGroupRelation.setResourceGroup(this.resourceGroupDao.findByKey(Integer.parseInt(addResourceGroupForUserJson.getResourceGroupId())));
		
		this.userResourceGroupRelationDao.insert(userResourceGroupRelation);
	}
	@Override
	public void deleteResourceGroupForUser(String id,String userId) {
		UserResourceGroupRelation userResourceGroupRelation=this.userResourceGroupRelationDao.findUserResourceGroupRelationByBothId(id,userId);
		this.userResourceGroupRelationDao.delete(userResourceGroupRelation);
	}
	@Override
	public boolean checkResourceGroupName(String resourceGroupName,int locationId){
		return this.resourceGroupDao.checkResourceGroupName(resourceGroupName,locationId);
	}
	@Override
	public String findResourceGroupNameByIndexResourceId(int indexResourceId){
		return this.resourceGroupDao.findResourceGroupNameByIndexResourceId(indexResourceId);
	}
	@Override
	public int findResourceGroupIdByIndexResourceId(int indexResourceId){
		return this.resourceGroupDao.findResourceGroupIdByIndexResourceId(indexResourceId);
	}
}
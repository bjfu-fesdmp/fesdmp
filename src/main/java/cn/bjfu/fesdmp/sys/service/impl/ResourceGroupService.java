
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroupResourceGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddResourceGroupForUserGroupJson;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupResourceGroupRelationDao;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.Pagination;


@Service
@Transactional
public class ResourceGroupService implements IResourceGroupService {

	@Autowired
	private IResourceGroupDao resourceGroupDao;
	@Autowired
	private IUserGroupDao userGroupDao;
	@Autowired
	private IUserGroupResourceGroupRelationDao userGroupResourceGroupRelationDao;
	@Override
	public void addResourceGroup(ResourceGroup resourceGroup) {
		this.resourceGroupDao.insert(resourceGroup);
	}

	@Override
	public void deleteResourceGroup(int id) {
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
	
	
	public List<ResourceGroup> findResourceGroupById(int parentId){
		return this.resourceGroupDao.findResourceGroupById(parentId);	
	}
	public List<ResourceGroup> findResourceGroupByUserGroupId(String userGroupId){
		return this.resourceGroupDao.findResourceGroupByUserGroupId(userGroupId);	
	}
	public boolean ifHaveChild(int id){
		return this.resourceGroupDao.ifHaveChild(id);	
	}
	
	public  boolean checkIfHaveIndexResource(int id){
		return this.resourceGroupDao.checkIfHaveIndexResource(id);
	}
	@Transactional(readOnly = true)
	@Override
	public List<ResourceGroup> findResourceGroupNotInThisUserGroup(String userGroupId) {
		return this.resourceGroupDao.findResourceGroupNotInThisUserGroup(userGroupId);
	}
	
	@Override
	public void addResourceGroupForUserGroup(AddResourceGroupForUserGroupJson addResourceGroupForUserGroupJson) {
		UserGroupResourceGroupRelation userGroupResourceGroupRelation=new UserGroupResourceGroupRelation();
		userGroupResourceGroupRelation.setUserGroup(this.userGroupDao.findByKey(Integer.parseInt(addResourceGroupForUserGroupJson.getUserGroupId())));
		userGroupResourceGroupRelation.setResourceGroup(this.resourceGroupDao.findByKey(Integer.parseInt(addResourceGroupForUserGroupJson.getResourceGroupId())));
		
		this.userGroupResourceGroupRelationDao.insert(userGroupResourceGroupRelation);
	}
	@Override
	public void deleteResourceGroupForUserGroup(String id,String userGroupId) {
		UserGroupResourceGroupRelation userGroupResourceGroupRelation=this.userGroupResourceGroupRelationDao.findUserGroupResourceGroupRelationByBothId(id,userGroupId);
		this.userGroupResourceGroupRelationDao.delete(userGroupResourceGroupRelation);
	}
	@Override
	public boolean checkResourceGroupName(String resourceGroupName){
		return this.resourceGroupDao.checkResourceGroupName(resourceGroupName);
	}
}
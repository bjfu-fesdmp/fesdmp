
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.Pagination;


@Service
@Transactional
public class ResourceGroupService implements IResourceGroupService {

	@Autowired
	private IResourceGroupDao resourceGroupDao;
	
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
	public List<ResourceGroup> findResourceGroupByroleId(String roleId){
		return this.resourceGroupDao.findResourceGroupByroleId(roleId);	
	}
	public boolean ifHaveChild(int id){
		return this.resourceGroupDao.ifHaveChild(id);	
	}
	
	public  boolean checkIfHaveIndexResource(int id){
		return this.resourceGroupDao.checkIfHaveIndexResource(id);
	}
	@Transactional(readOnly = true)
	@Override
	public List<ResourceGroup> findResourceGroupNotInThisRole(String roleId) {
		return this.resourceGroupDao.findResourceGroupNotInThisRole(roleId);
	}
}
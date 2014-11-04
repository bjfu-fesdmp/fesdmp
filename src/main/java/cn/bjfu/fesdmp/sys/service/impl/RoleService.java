
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.domain.sys.RoleResourceGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddResourceGroupForRoleJson;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IRoleDao;
import cn.bjfu.fesdmp.sys.dao.IRoleResourceGroupRelationDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupRoleRelationDao;
import cn.bjfu.fesdmp.sys.service.IRoleService;
import cn.bjfu.fesdmp.utils.Pagination;


@Service
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IResourceGroupDao resourceGroupDao;
	@Autowired
	private IRoleResourceGroupRelationDao roleResourceGroupRelationDao;
	@Override
	public void addRole(Role role) {
		this.roleDao.insert(role);
	}

	@Override
	public void deleteRole(int id) {
		this.roleDao.delete(this.roleDao.findByKey(id));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Role> queryAll(IOrder order) {
		return this.roleDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<Role> page) {
		this.roleDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Role> queryByCondition(Object condition, IOrder order,
			Pagination<Role> page, JoinMode joinMode) {
		return this.roleDao.findByCondition(condition, order, page, joinMode);
	}
	@Override
	public void addResourceGroupForRole(AddResourceGroupForRoleJson addResourceGroupForRoleJson) {
		RoleResourceGroupRelation roleResourceGroupRelation=new RoleResourceGroupRelation();
		roleResourceGroupRelation.setRole(this.roleDao.findByKey(Integer.parseInt(addResourceGroupForRoleJson.getRoleId())));
		roleResourceGroupRelation.setResourceGroup(this.resourceGroupDao.findByKey(Integer.parseInt(addResourceGroupForRoleJson.getResourceGroupId())));
		
		this.roleResourceGroupRelationDao.insert(roleResourceGroupRelation);
	}

}
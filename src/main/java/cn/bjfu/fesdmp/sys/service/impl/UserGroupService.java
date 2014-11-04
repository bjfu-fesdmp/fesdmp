
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroupRoleRelation;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserGroupJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IRoleDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupRoleRelationDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class UserGroupService implements IUserGroupService {

	@Autowired
	private IUserGroupDao userGroupDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IUserGroupRoleRelationDao userGroupRoleRelationDao;
	@Override
	public void addUserGroup(UserGroupJson userGroupJson) {
		UserGroup userGroup=new UserGroup();
		UserGroupRoleRelation userGroupRoleRelation=new UserGroupRoleRelation();
		userGroup.setUserGroupName(userGroupJson.getUserGroupName());
		userGroup.setCreateTime(userGroupJson.getCreateTime());
		this.userGroupDao.insert(userGroup);
		userGroupRoleRelation.setUserGroup(userGroup);
		userGroupRoleRelation.setRole(this.roleDao.findByKey(userGroupJson.getRole()));
		this.userGroupRoleRelationDao.insert(userGroupRoleRelation);
	}
	
	@Override
	public void modifyUserGroup(UserGroup userGroup) {
		UserGroup userGroupNew = this.userGroupDao.findByKey(userGroup.getId());
		userGroupNew.setUserGroupName(userGroup.getUserGroupName());
		this.userGroupDao.update(userGroupNew);
	}
	@Override
	public void deleteUserGroup(int id) {
		this.userGroupDao.delete(this.userGroupDao.findByKey(id));
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public List<UserGroup> queryAll(IOrder order) {
		return this.userGroupDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<UserGroup> page) {
		this.userGroupDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserGroup> queryByCondition(Object condition, IOrder order,
			Pagination<UserGroup> page, JoinMode joinMode) {
		return this.userGroupDao.findByCondition(condition, order, page, joinMode);
	}
	public  UserGroup findByKey(int id){
		
		return this.userGroupDao.findByKey(id);
	}
	
}
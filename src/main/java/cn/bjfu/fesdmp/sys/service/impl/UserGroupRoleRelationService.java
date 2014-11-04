
  
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
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupRoleRelationDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.sys.service.IUserGroupRoleRelationService;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.sys.service.IUserUserGroupRelationService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class UserGroupRoleRelationService implements IUserGroupRoleRelationService {


	@Autowired
	private IUserGroupRoleRelationDao userGroupRoleRelationDao;

	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<UserGroupRoleRelation> page) {
		this.userGroupRoleRelationDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserGroupRoleRelation> queryByCondition(Object condition, IOrder order,
			Pagination<UserGroupRoleRelation> page, JoinMode joinMode) {
		return this.userGroupRoleRelationDao.findByCondition(condition, order, page, joinMode);
	}
	public  UserGroupRoleRelation findByKey(int id){	
		return this.userGroupRoleRelationDao.findByKey(id);
	}

	public UserGroupRoleRelation findUserGroupRoleRelationByUserGroupId(String userGroupId) {
		return this.userGroupRoleRelationDao.findUserGroupRoleRelationByUserGroupId(userGroupId);	
	}
}
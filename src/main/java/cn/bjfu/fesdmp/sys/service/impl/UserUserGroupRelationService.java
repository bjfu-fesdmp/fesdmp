
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserGroupJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.sys.service.IUserUserGroupRelationService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class UserUserGroupRelationService implements IUserUserGroupRelationService {


	@Autowired
	private IUserUserGroupRelationDao userUserGroupRelationDao;

	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<UserUserGroupRelation> page) {
		this.userUserGroupRelationDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserUserGroupRelation> queryByCondition(Object condition, IOrder order,
			Pagination<UserUserGroupRelation> page, JoinMode joinMode) {
		return this.userUserGroupRelationDao.findByCondition(condition, order, page, joinMode);
	}
	public  UserUserGroupRelation findByKey(int id){	
		return this.userUserGroupRelationDao.findByKey(id);
	}

	
	public UserUserGroupRelation findUserUserGroupRelationByUserId(String userId) {
		return this.userUserGroupRelationDao.findUserUserGroupRelationByUserId(userId);	
	}
	
	public UserUserGroupRelation findUserUserGroupRelationByUserGroupId(String userGroupId) {
		return this.userUserGroupRelationDao.findUserUserGroupRelationByUserGroupId(userGroupId);	
	}
}
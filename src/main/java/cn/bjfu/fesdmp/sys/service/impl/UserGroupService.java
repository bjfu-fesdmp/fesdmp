
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
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
	
	@Override
	public void addUserGroup(UserGroup userGroup) {
		this.userGroupDao.insert(userGroup);
	}

	@Override
	public void deleteUserGroup(UserGroup userGroup) {
		this.userGroupDao.delete(userGroup);
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

}
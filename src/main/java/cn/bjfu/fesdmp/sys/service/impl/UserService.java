
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public void addUser(User user) {
		this.userDao.insert(user);
	}

	@Override
	public void deleteUser(User user) {
		this.userDao.delete(user);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> queryAll(IOrder order) {
		return this.userDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<User> page) {
		this.userDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<User> queryByCondition(Object condition, IOrder order,
			Pagination<User> page, JoinMode joinMode) {
		return this.userDao.findByCondition(condition, order, page, joinMode);
	}

	@Override
	public List<User> findByCondtinGetCreater(UserSearch condition,
			IOrder order, Pagination<User> page, JoinMode joinMode) {
		return this.userDao.findByCondtinGetCreater(condition, order, page,joinMode);
	}
}
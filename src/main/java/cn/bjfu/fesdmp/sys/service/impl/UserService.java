
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.DESTools;
import cn.bjfu.fesdmp.utils.Pagination;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IIndexResourceDao indexResourceDao;
	
	@Override
	public void addUser(User user) {
		this.userDao.insert(user);
	}

	@Override
	public void modifyUser(AddUserJson addUserJson){
		User user = this.userDao.findByKey(addUserJson.getId());
		user.setUserName(addUserJson.getUserName());
		if (addUserJson.getUserLoginName()!="")
			user.setUserLoginName(addUserJson.getUserLoginName());
		else
			user.setUserLoginName(null);
		if (addUserJson.getUserPhone()!="")
		user.setUserPhone((addUserJson.getUserPhone()));
		else
			user.setUserPhone(null);
		if (addUserJson.getEmail()!="")
		user.setEmail(addUserJson.getEmail());
		else
			user.setEmail(null);
		this.userDao.update(user);
	}
	@Override
	public void modifyPassword(AddUserJson addUserJson){
		User user = this.userDao.findByKey(addUserJson.getId());
		user.setPassword(addUserJson.getPassword());
		this.userDao.update(user);
	}
	@Override
	public void deleteUser(int id) {
		User user=this.userDao.findByKey(id);
		user.setUserStatus((byte) 3);
		this.userDao.update(user);
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
	public  User findByKey(int id){
		return this.userDao.findByKey(id);
	}
	@Override
	public User findByUserLoginName(String userLoginName){
		return this.userDao.findByUserLoginName(userLoginName);
	}
	@Override
	public boolean checkUserName(String userName){
		return this.userDao.checkUserName(userName);
	}
	@Override
	public boolean checkUserLoginName(String userLoginName){
		return this.userDao.checkUserLoginName(userLoginName);
	}
	@Override
	public boolean checkIfHaveAuthority(int userId,String indexResourceEnName){
		if(this.userDao.checkIfIsTemporaryManager(userId,this.indexResourceDao.findByEnName(indexResourceEnName).getId()))
			return true;
		else
			return this.userDao.checkIfHaveAuthority(userId,this.indexResourceDao.findByEnName(indexResourceEnName).getId());
	}
	@Override
	public boolean checkIfIsTemporaryManager(int userId){
		return this.userDao.checkIfIsTemporaryManager(userId);
	}
}
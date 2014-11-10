
  
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
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.sys.dao.impl.UserGroupDaoImpl;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserUserGroupRelationDao userUserGroupRelationDao;
	@Autowired
	private IUserGroupDao userGroupDao;
	@Autowired
	private IIndexResourceDao indexResourceDao;
	
	@Override
	public void addUser(User user,int userGroupId) {
		UserUserGroupRelation userUserGroupRelation=new UserUserGroupRelation();

		this.userDao.insert(user);
		UserGroup userGroup = this.userGroupDao.findByKey(userGroupId);	
		
		userUserGroupRelation.setUser(user);
		userUserGroupRelation.setUserGroup(userGroup);
		this.userUserGroupRelationDao.insert(userUserGroupRelation);
	}

	@Override
	public void modifyUser(AddUserJson addUserJson){
		User user = this.userDao.findByKey(addUserJson.getId());
		UserUserGroupRelation userUserGroupRelation=this.userUserGroupRelationDao.findUserUserGroupRelationByUserId(Integer.toString(addUserJson.getId()));
		UserGroup userGroup=this.userGroupDao.findByKey(addUserJson.getUserGroup());
		userUserGroupRelation.setUserGroup(userGroup);
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
		this.userUserGroupRelationDao.update(userUserGroupRelation);
	}
	@Override
	public void deleteUser(int id) {
		this.userUserGroupRelationDao.delete(this.userUserGroupRelationDao.findUserUserGroupRelationByUserId(Integer.toString(id)));
		this.userDao.delete(this.userDao.findByKey(id));
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
		
		return this.userDao.checkIfHaveAuthority(userId,this.indexResourceDao.findByEnName(indexResourceEnName).getId());
	}
}
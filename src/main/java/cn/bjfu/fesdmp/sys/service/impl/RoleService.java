
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IRoleDao;
import cn.bjfu.fesdmp.sys.service.IRoleService;
import cn.bjfu.fesdmp.utils.Pagination;


@Service
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
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


}
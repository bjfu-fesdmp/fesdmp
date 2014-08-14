package cn.bjfu.fesdmp.sys.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.sys.dao.IRoleDao;

@Repository
public class RoleDaoImpl extends AbstractGenericDao<Role> implements IRoleDao {
	
	private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);
	
	public RoleDaoImpl() {
		super(Role.class);
	}

}
 

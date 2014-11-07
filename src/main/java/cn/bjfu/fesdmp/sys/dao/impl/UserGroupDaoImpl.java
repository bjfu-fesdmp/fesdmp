
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Repository
public class UserGroupDaoImpl extends AbstractGenericDao<UserGroup> implements IUserGroupDao {

	private static final Logger logger = Logger.getLogger(UserGroupDaoImpl.class);
	
	public UserGroupDaoImpl() {
		super(UserGroup.class);
	}
	@Override
	public boolean checkUserGroupName(String userGroupName){
		String jpal = " SELECT p FROM UserGroup p where p.userGroupName='"+userGroupName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
}
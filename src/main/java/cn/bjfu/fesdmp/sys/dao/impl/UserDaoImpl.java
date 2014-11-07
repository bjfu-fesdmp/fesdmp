
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Repository
public class UserDaoImpl extends AbstractGenericDao<User> implements IUserDao {
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User findByUserLoginName(String userLoginName){
		String jpal = " SELECT p FROM User p where p.userLoginName='"+userLoginName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		User user=new User();
		if(!list.isEmpty())
			user=(User)list.get(0);
		return user;
	}
	@Override
	public boolean checkUserName(String userName){
		String jpal = " SELECT p FROM User p where p.userName='"+userName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
	@Override
	public boolean checkUserLoginName(String userLoginName){
		String jpal = " SELECT p FROM User p where p.userLoginName='"+userLoginName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
}
 
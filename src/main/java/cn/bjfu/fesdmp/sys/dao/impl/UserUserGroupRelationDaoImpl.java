
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Repository
public class UserUserGroupRelationDaoImpl extends AbstractGenericDao<UserUserGroupRelation> implements IUserUserGroupRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	public UserUserGroupRelationDaoImpl() {
		super(UserUserGroupRelation.class);
	}
	@Override
	public UserUserGroupRelation findUserUserGroupRelationByUserId(String userId){
		String jpal = " SELECT p FROM UserUserGroupRelation p where p.user.id="+userId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserUserGroupRelation userUserGroupRelation=(UserUserGroupRelation)list.get(0);
		return userUserGroupRelation;
	}
	
	@Override
	public UserUserGroupRelation findUserUserGroupRelationByUserGroupId(String userGroupId){
		String jpal = " SELECT p FROM UserUserGroupRelation p where p.userGroup.id="+userGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserUserGroupRelation userUserGroupRelation=new UserUserGroupRelation();
		if(!list.isEmpty())
			userUserGroupRelation=(UserUserGroupRelation)list.get(0);
		return userUserGroupRelation;
	}
}
 
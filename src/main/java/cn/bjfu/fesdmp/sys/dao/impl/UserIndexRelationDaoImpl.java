
package cn.bjfu.fesdmp.sys.dao.impl;  


import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;
import cn.bjfu.fesdmp.sys.dao.IUserIndexRelationDao;


@Repository
public class UserIndexRelationDaoImpl extends AbstractGenericDao<UserIndexRelation> implements IUserIndexRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	public UserIndexRelationDaoImpl() {
		super(UserIndexRelation.class);
	}
	public UserIndexRelation findUserIndexRelationByBothId(String id,String userId){
		String jpal = " SELECT p FROM UserIndexRelation p where p.indexResource.id="+id+" and p.user.id="+userId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserIndexRelation userIndexRelation=(UserIndexRelation)list.get(0);
		return userIndexRelation;
	}
}
 
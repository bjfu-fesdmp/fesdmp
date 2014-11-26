
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.UserResourceGroupRelation;
import cn.bjfu.fesdmp.sys.dao.IUserResourceGroupRelationDao;


@Repository
public class UserResourceGroupRelationDaoImpl extends AbstractGenericDao<UserResourceGroupRelation> implements IUserResourceGroupRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserResourceGroupRelationDaoImpl.class);

	public UserResourceGroupRelationDaoImpl() {
		super(UserResourceGroupRelation.class);
	}
	
	public UserResourceGroupRelation findUserResourceGroupRelationByBothId(String id,String userId){
		String jpal = " SELECT p FROM UserResourceGroupRelation p where p.resourceGroup.id="+id+" and p.user.id="+userId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserResourceGroupRelation userResourceGroupRelation=(UserResourceGroupRelation)list.get(0);
		return userResourceGroupRelation;
	}
	
	
}
 

package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.UserGroupResourceGroupRelation;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.sys.dao.IUserGroupResourceGroupRelationDao;


@Repository
public class UserGroupResourceGroupRelationDaoImpl extends AbstractGenericDao<UserGroupResourceGroupRelation> implements IUserGroupResourceGroupRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserGroupResourceGroupRelationDaoImpl.class);

	public UserGroupResourceGroupRelationDaoImpl() {
		super(UserGroupResourceGroupRelation.class);
	}
	
	public UserGroupResourceGroupRelation findUserGroupResourceGroupRelationByBothId(String id,String userGroupId){
		String jpal = " SELECT p FROM UserGroupResourceGroupRelation p where p.resourceGroup.id="+id+" and p.userGroup.id="+userGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserGroupResourceGroupRelation userGroupResourceGroupRelation=(UserGroupResourceGroupRelation)list.get(0);
		return userGroupResourceGroupRelation;
	}
	
	
}
 
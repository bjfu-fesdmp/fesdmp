
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import cn.bjfu.fesdmp.domain.sys.UserGroupRoleRelation;
import cn.bjfu.fesdmp.sys.dao.IUserGroupRoleRelationDao;


@Repository
public class UserGroupRoleRelationDaoImpl extends AbstractGenericDao<UserGroupRoleRelation> implements IUserGroupRoleRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserGroupRoleRelationDaoImpl.class);

	public UserGroupRoleRelationDaoImpl() {
		super(UserGroupRoleRelation.class);
	}
	
	@Override
	public UserGroupRoleRelation findUserGroupRoleRelationByUserGroupId(String userGroupId){
		String jpal = " SELECT p FROM UserGroupRoleRelation p where p.userGroup.id="+userGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		UserGroupRoleRelation userGroupRoleRelation=new UserGroupRoleRelation();
		if(!list.isEmpty())
			userGroupRoleRelation=(UserGroupRoleRelation)list.get(0);
		return userGroupRoleRelation;
	}
	
	
}
 
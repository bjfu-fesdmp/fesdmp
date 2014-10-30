
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IResourceRelationDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.dao.IUserUserGroupRelationDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Repository
public class ResourceRelationDaoImpl extends AbstractGenericDao<ResourceRelation> implements IResourceRelationDao {
	
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	public ResourceRelationDaoImpl() {
		super(ResourceRelation.class);
	}
	
	@Override
	public List<ResourceRelation> findResourceRelationByResourceGroupId(String resourceGroupId){
		String jpal = " SELECT p FROM ResourceRelation p where p.resourceGroup.id="+resourceGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<ResourceRelation> list=query.getResultList();
		ResourceRelation resourceRelation=new ResourceRelation();
		return list;
	}
}
 
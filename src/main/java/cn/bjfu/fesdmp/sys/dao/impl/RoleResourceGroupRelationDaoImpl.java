
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.RoleResourceGroupRelation;
import cn.bjfu.fesdmp.sys.dao.IRoleResourceGroupRelationDao;


@Repository
public class RoleResourceGroupRelationDaoImpl extends AbstractGenericDao<RoleResourceGroupRelation> implements IRoleResourceGroupRelationDao {
	
	private static final Logger logger = Logger.getLogger(RoleResourceGroupRelationDaoImpl.class);

	public RoleResourceGroupRelationDaoImpl() {
		super(RoleResourceGroupRelation.class);
	}
	

	
	
}
 

  
package cn.bjfu.fesdmp.sys.dao;  


import java.util.List;

import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;
import cn.bjfu.fesdmp.domain.sys.UserResourceGroupRelation;


public interface IUserResourceGroupRelationDao extends IGenericDao<UserResourceGroupRelation> {
	
	public abstract UserResourceGroupRelation findUserResourceGroupRelationByBothId(String id,String userId);
	public abstract List<UserResourceGroupRelation> findUserResourceGroupByResourceGroupId(String resourceGroupId);
}
 

  
package cn.bjfu.fesdmp.sys.dao;  


import cn.bjfu.fesdmp.domain.sys.UserResourceGroupRelation;


public interface IUserResourceGroupRelationDao extends IGenericDao<UserResourceGroupRelation> {
	
	public abstract UserResourceGroupRelation findUserResourceGroupRelationByBothId(String id,String userId);
}
 
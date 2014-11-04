
  
package cn.bjfu.fesdmp.sys.dao;  


import cn.bjfu.fesdmp.domain.sys.UserGroupResourceGroupRelation;


public interface IUserGroupResourceGroupRelationDao extends IGenericDao<UserGroupResourceGroupRelation> {
	
	public abstract UserGroupResourceGroupRelation findUserGroupResourceGroupRelationByBothId(String id,String userGroupId);
}
 
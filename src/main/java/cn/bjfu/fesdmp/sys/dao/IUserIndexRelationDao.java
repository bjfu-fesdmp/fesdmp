
  
package cn.bjfu.fesdmp.sys.dao;  


import cn.bjfu.fesdmp.domain.sys.UserGroupResourceGroupRelation;
import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;


public interface IUserIndexRelationDao extends IGenericDao<UserIndexRelation> {
	
	public abstract UserIndexRelation findUserIndexRelationByBothId(String id,String userId);
}
 
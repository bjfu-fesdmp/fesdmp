
  
package cn.bjfu.fesdmp.sys.dao;  


import java.util.List;

import cn.bjfu.fesdmp.domain.sys.UserIndexRelation;


public interface IUserIndexRelationDao extends IGenericDao<UserIndexRelation> {
	
	public abstract UserIndexRelation findUserIndexRelationByBothId(String id,String userId);
	public abstract List<UserIndexRelation> findUserIndexRelationByIndexResourceId(String indexResourceid);
}
 
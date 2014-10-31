
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceRelation;


public interface IResourceRelationDao extends IGenericDao<ResourceRelation> {
	public abstract List<ResourceRelation> findResourceRelationByResourceGroupId(String resourceGroupId);
	public abstract ResourceRelation findByIndexResourceId(int indexResourceId);
}
 
package cn.bjfu.fesdmp.sys.dao;

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;

public interface IResourceGroupDao extends IGenericDao<ResourceGroup> {
	public abstract List<ResourceGroup> findResourceGroupByParentIdAndUserId(int parentId,int userId);
	public abstract List<ResourceGroup> findResourceGroupByUserId(String userId);
	public abstract List<ResourceGroup> findResourceGroupByUserIdAndLocation(String userId,int locationId);
	public abstract boolean ifHaveChild(int id);
	public abstract boolean checkIfHaveIndexResource(int id);
	public abstract List<ResourceGroup> findResourceGroupNotInThisUser(String userId);
	public abstract List<ResourceGroup> findResourceGroupInThisLocation(int locationId);
	public abstract boolean checkResourceGroupName(String resourceGroupName,int locationId);
	public abstract String findResourceGroupNameByIndexResourceId(int indexResourceId);
	public abstract int findResourceGroupIdByIndexResourceId(int indexResourceId);
}

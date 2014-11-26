package cn.bjfu.fesdmp.sys.dao;

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;

public interface IResourceGroupDao extends IGenericDao<ResourceGroup> {
	public abstract List<ResourceGroup> findResourceGroupById(int parentId);
	public abstract List<ResourceGroup> findResourceGroupByUserId(String userId);
	public abstract boolean ifHaveChild(int id);
	public abstract boolean checkIfHaveIndexResource(int id);
	public abstract List<ResourceGroup> findResourceGroupNotInThisUser(String userId);
	public abstract boolean checkResourceGroupName(String resourceGroupName);
}

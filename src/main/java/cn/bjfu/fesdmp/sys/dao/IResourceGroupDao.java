package cn.bjfu.fesdmp.sys.dao;

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.Role;

public interface IResourceGroupDao extends IGenericDao<ResourceGroup> {
	public abstract List<ResourceGroup> findResourceGroupById(int parentId);
	public abstract List<ResourceGroup> findResourceGroupByroleId(String roleId);
	public abstract boolean ifHaveChild(int id);
	public abstract boolean checkIfHaveIndexResource(int id);
	public abstract List<ResourceGroup> findResourceGroupNotInThisRole(String roleId);
}


package cn.bjfu.fesdmp.sys.service;  

import java.util.List;







import cn.bjfu.fesdmp.domain.sys.UserGroupRoleRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;



public interface IUserGroupRoleRelationService {


	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<UserGroupRoleRelation> page);
	public abstract List<UserGroupRoleRelation> queryByCondition(final Object condition, IOrder order,
			Pagination<UserGroupRoleRelation> page, JoinMode joinMode);
	public abstract UserGroupRoleRelation findUserGroupRoleRelationByUserGroupId(String userGroupId);
}
 
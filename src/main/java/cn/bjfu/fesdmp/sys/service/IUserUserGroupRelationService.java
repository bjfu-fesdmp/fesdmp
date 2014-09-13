
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;






import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserGroupJson;
import cn.bjfu.fesdmp.utils.Pagination;



public interface IUserUserGroupRelationService {


	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<UserUserGroupRelation> page);
	public abstract List<UserUserGroupRelation> queryByCondition(final Object condition, IOrder order,
			Pagination<UserUserGroupRelation> page, JoinMode joinMode);
	public abstract UserUserGroupRelation findUserUserGroupRelationByUserId(String userId);
	public abstract UserUserGroupRelation findUserUserGroupRelationByUserGroupId(String userGroupId);
}
 
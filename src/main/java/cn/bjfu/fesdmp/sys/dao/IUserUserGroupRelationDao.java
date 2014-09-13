
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


public interface IUserUserGroupRelationDao extends IGenericDao<UserUserGroupRelation> {
	public abstract UserUserGroupRelation findUserUserGroupRelationByUserId(String userId);
	public abstract UserUserGroupRelation findUserUserGroupRelationByUserGroupId(String userGroupId);
}
 
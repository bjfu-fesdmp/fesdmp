
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import javax.persistence.Query;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroupRoleRelation;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


public interface IUserGroupRoleRelationDao extends IGenericDao<UserGroupRoleRelation> {
	
	
	public abstract UserGroupRoleRelation findUserGroupRoleRelationByUserGroupId(String userGroupId);

}
 
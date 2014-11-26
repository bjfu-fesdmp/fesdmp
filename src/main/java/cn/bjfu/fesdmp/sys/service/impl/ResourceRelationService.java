
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IResourceRelationDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;
import cn.bjfu.fesdmp.sys.service.IResourceRelationService;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Service
@Transactional
public class ResourceRelationService implements IResourceRelationService {


	@Autowired
	private IResourceRelationDao resourceRelationDao;

	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<ResourceRelation> page) {
		this.resourceRelationDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ResourceRelation> queryByCondition(Object condition, IOrder order,
			Pagination<ResourceRelation> page, JoinMode joinMode) {
		return this.resourceRelationDao.findByCondition(condition, order, page, joinMode);
	}
	public  ResourceRelation findByKey(int id){	
		return this.resourceRelationDao.findByKey(id);
	}
	
	public List<ResourceRelation> findResourceRelationByResourceGroupId(String resourceGroupId) {
		return this.resourceRelationDao.findResourceRelationByResourceGroupId(resourceGroupId);	
	}
}
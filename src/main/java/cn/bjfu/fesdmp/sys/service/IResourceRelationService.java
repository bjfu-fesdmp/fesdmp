
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;

public interface IResourceRelationService {


	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<ResourceRelation> page);
	public abstract List<ResourceRelation> queryByCondition(final Object condition, IOrder order,
			Pagination<ResourceRelation> page, JoinMode joinMode);
	public abstract List<ResourceRelation> findResourceRelationByResourceGroupId(String resourceGroupId);
}
 
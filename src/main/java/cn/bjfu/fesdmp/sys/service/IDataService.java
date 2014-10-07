 
  
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;

public interface IDataService {

	public abstract void addData(DataJson data);
	public abstract void deleteData(DataJson data);
	public abstract List<DataJson> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<DataJson> page);
	public abstract List<DataJson> queryByCondition(final Object condition, IOrder order,
			Pagination<DataJson> page, JoinMode joinMode);
	public List<DataJson> queryByCondtinWithOperationTime(DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode);
}
 
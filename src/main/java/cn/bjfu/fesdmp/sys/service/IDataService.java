 
  
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;

public interface IDataService {

	public abstract void addData(String table,List<DataJson> list);
	public abstract boolean checkIfHasTable(String tableName);
	public abstract void modifyData(DataJson data,String tableName);
	public abstract DataJson findDataById(String id,String tableName);
	public abstract void deleteData(String tableName, String[] id);
	public abstract void deleteTable(String tableName);
	public abstract List<DataJson> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<DataJson> page);
	public abstract List<DataJson> queryByCondition(final Object condition, IOrder order,
			Pagination<DataJson> page, JoinMode joinMode);
	public List<DataJson> queryByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode);
	public List<DataJson> queryUnionByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode);
	public List<TableJson> findTable();
}
 
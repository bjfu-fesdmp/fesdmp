/** 
 * Project Name:fesdmp 
 * File Name:ISystemLogDao.java 
 * Package Name:cn.bjfu.fesdmp.sys.dao 
 * Date:2014年7月9日 上午12:37:30 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.Date;
import java.util.List;

import cn.bjfu.fesdmp.domain.sys.ResourceTable;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


public interface IDataDao extends IGenericDao<DataJson> {


	public abstract List<DataJson> findByCondtinWithOperationTime(String tableName,final DataSearch condition, 
			IOrder order, Pagination<DataJson> page, JoinMode joinMode);
	public abstract List<DataJson> findUnionByCondtinWithOperationTime(String tableName,final DataSearch condition, 
			IOrder order, Pagination<DataJson> page, JoinMode joinMode);
	public abstract List<TableJson> findTable();
	public abstract TableJson findTableByYearAndIndexResource(String year,int id);
	public abstract void dataInsert(String table,List<DataJson> list);
	public abstract void modifyData(DataJson data,String tableName);
	public abstract void deleteDataById(String tableName, String id);
	public abstract void deleteTable(String tableName);
	public abstract void modifyTableName(String oldName,String newName);
	public abstract DataJson findDataById(String id,String tableName);
	public abstract List<DataJson>  findAllData(String tableName);
	public abstract boolean checkIfHasTable(String tableName);
	public abstract List<ResourceTable> findTableByEnName(String tableName);
	public abstract DataJson[] findData(String tableName,Date startTime,Date endTime);
	public abstract DataJson[] findData(String tableName);
}
 
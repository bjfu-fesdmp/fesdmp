/** 
 * Project Name:fesdmp 
 * File Name:ISystemLogDao.java 
 * Package Name:cn.bjfu.fesdmp.sys.dao 
 * Date:2014年7月9日 上午12:37:30 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

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
	public abstract List<TableJson> findTable();
	public abstract void dataInsert(String table,List<DataJson> list);
	public abstract void modifyData(DataJson data,String tableName);
	public abstract void deleteTable(String tableName);
	public abstract void modifyTableName(String oldName,String newName);
	public abstract DataJson findDataById(String id,String tableName);
}
 
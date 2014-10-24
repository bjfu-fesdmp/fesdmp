/** 
 * Project Name:fesdmp 
 * File Name:SystemLogService.java 
 * Package Name:cn.bjfu.fesdmp.sys.service.impl 
 * Date:2014年7月9日 上午10:29:49 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.sys.dao.IDataDao;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;

@Service
@Transactional
public class DataService implements IDataService {

	@Autowired
	private IDataDao dataDao;
	
	@Override
	public void addData(String table,List<DataJson> list) {
		this.dataDao.dataInsert(table,list);
	}

	@Override
	public void deleteData(DataJson data) {
		this.dataDao.delete(data);
	}

	@Override
	public void modifyData(DataJson data,String tableName) {
		this.dataDao.modifyData(data,tableName);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DataJson> queryAll(IOrder order) {
		return this.dataDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<DataJson> page) {
		this.dataDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DataJson> queryByCondition(Object condition, IOrder order,
			Pagination<DataJson> page, JoinMode joinMode) {
		return this.dataDao.findByCondition(condition, order, page, joinMode);
	}

	@Override
	public List<DataJson> queryByCondtinWithOperationTime(String tableName,DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		return this.dataDao.findByCondtinWithOperationTime(tableName,condition, order, page, joinMode);
	}
	
	@Override
	public List<TableJson> findTable(){
		return this.dataDao.findTable();
	};
}
 
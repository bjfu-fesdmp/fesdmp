/** 
 * Project Name:fesdmp 
 * File Name:SystemLogDaoImpl.java 
 * Package Name:cn.bjfu.fesdmp.sys.dao.impl 
 * Date:2014年7月9日 上午12:38:25 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.sys.dao.IDataDao;
import cn.bjfu.fesdmp.sys.dao.ISystemLogDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Repository
public class DataDaoImpl extends AbstractGenericDao<DataJson> implements IDataDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = Logger.getLogger(DataDaoImpl.class);
	
	public DataDaoImpl() {
		super(DataJson.class);
	}

	@Override
	public List<DataJson> findByCondtinWithOperationTime(DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		DataJson dataJson=new DataJson();
		String sql = "select * from 2014_sized order by time asc";
		List<Map<String, Object>> result0 =jdbcTemplate.queryForList(sql);
		List<DataJson> result=new ArrayList();
		for(int i=0;i<result0.size();i++){
			DataJson datajson=new DataJson();
			datajson.setId(Integer.valueOf(result0.get(i).get("id").toString()));
			datajson.setTime(result0.get(i).get("time").toString());
			datajson.setData(result0.get(i).get("data").toString());
			result.add(datajson);
		}	
		logger.info(sql);
		if (page != null) {
			page.setTotalRecord(result.size());
			page.setDatas(result);
			return result;
		}else{
			return result;
		}

	}

}
 
/** 
 * Project Name:fesdmp 
 * File Name:SystemLogDaoImpl.java 
 * Package Name:cn.bjfu.fesdmp.sys.dao.impl 
 * Date:2014年7月9日 上午12:38:25 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
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
	
	private static final Logger logger = Logger.getLogger(DataDaoImpl.class);
	
	public DataDaoImpl() {
		super(DataJson.class);
	}

	@Override
	public List<DataJson> findByCondtinWithOperationTime(DataSearch condition,
			IOrder order, Pagination<DataJson> page, JoinMode joinMode) {
		String jpal = " SELECT p FROM SystemLog p ";
		if (condition != null) {
			jpal += convertBeanToJPAL(condition, joinMode);
			if (condition.getStartTime() != null && condition.getEndTime() != null) {
				jpal += " AND ( p.operateTime >= '" + DateFormat.getShortDate(condition.getStartTime()) +
						"' AND  p.operateTime <= '" + DateFormat.getShortDate(condition.getEndTime()) + "' ) ";
			}
			if (condition.getStartTime() != null && condition.getEndTime() == null) {
				jpal += " AND ( p.operateTime >=  '" + DateFormat.getShortDate(condition.getStartTime()) + 
						"' AND p.operateTime <= '" + DateFormat.getShortDate(new Date()) + "' ) ";
			}
		} 
		if (order != null) {
			jpal += convertToSQL(order);
		}
		
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		if (page != null) {
			page.setTotalRecord(query.getResultList().size());
			List<DataJson> result =  query.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
			page.setDatas(result);
			return result;
		}else{
			return query.getResultList();
		}
		
	}

}
 
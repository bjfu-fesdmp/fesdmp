 
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.ISystemLogDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Repository
public class IndexResourceDaoImpl extends AbstractGenericDao<IndexResource> implements IIndexResourceDao {
	
	private static final Logger logger = Logger.getLogger(SystemLogDaoImpl.class);
	
	public IndexResourceDaoImpl() {
		super(IndexResource.class);
	}

/*	@Override
	public List<IndexResource> findByCondtinWithOperationTime(LogSearch condition,
			IOrder order, Pagination<IndexResource> page, JoinMode joinMode) {
		String jpal = " SELECT p FROM IndexResource p ";
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
			List<IndexResource> result =  query.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
			page.setDatas(result);
			return result;
		}else{
			return query.getResultList();
		}
		
	}*/

}
 
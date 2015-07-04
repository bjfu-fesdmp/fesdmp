
  
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.ILocationDao;
import cn.bjfu.fesdmp.sys.dao.ISystemLogDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LocationSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


@Repository
public class LocationDaoImpl extends AbstractGenericDao<Location> implements ILocationDao {
	
	private static final Logger logger = Logger.getLogger(LocationDaoImpl.class);
	
	public LocationDaoImpl() {
		super(Location.class);
	}

	@Override
	public List<Location> findByCondtinWithName(LocationSearch condition,
			IOrder order, Pagination<Location> page, JoinMode joinMode) {
		String jpal = " SELECT p FROM Location p ";
		if (condition != null) {
			jpal += convertBeanToJPAL(condition, joinMode);

				jpal += " AND ( p.locationName >=  '" + condition.getLocationName() + "' ) ";
			
		} 
		if (order != null) {
			jpal += convertToSQL(order);
		}
		
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		if (page != null) {
			page.setTotalRecord(query.getResultList().size());
			List<Location> result =  query.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize()).getResultList();
			page.setDatas(result);
			return result;
		}else{
			return query.getResultList();
		}
		
	}
	@Override
	public boolean checkLocationName(String locationName){
		String jpal = " SELECT p FROM Location p where p.locationName='"+locationName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
}
 
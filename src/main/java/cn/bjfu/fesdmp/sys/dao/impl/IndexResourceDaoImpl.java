 
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.IIndexResourceDao;
import cn.bjfu.fesdmp.sys.dao.ISystemLogDao;
import cn.bjfu.fesdmp.utils.DateFormat;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Repository
public class IndexResourceDaoImpl extends AbstractGenericDao<IndexResource> implements IIndexResourceDao {
	
	private static final Logger logger = Logger.getLogger(IndexResourceDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public IndexResourceDaoImpl() {
		super(IndexResource.class);
	}
	public void createResourceListByTime(String resource,String year){
		String sql = "CREATE TABLE "+year+"_"+resource+"(id INT AUTO_INCREMENT PRIMARY KEY,time datetime,data VARCHAR(50))";
		jdbcTemplate.execute(sql);
	}
	

	public String findUnitByIndex(String index){
		IndexResource indexResource=new IndexResource();
		String jpal = " SELECT p FROM IndexResource p where p.indexEnName='"+index+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		String unit="";
		if(!list.isEmpty())
			indexResource=(IndexResource)list.get(0);
		unit=indexResource.getIndexUnit();
		return unit;
	}
	
	

	
}
 
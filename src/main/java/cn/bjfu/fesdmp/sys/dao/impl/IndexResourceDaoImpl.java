 
package cn.bjfu.fesdmp.sys.dao.impl;  

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
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
		String sql = "CREATE TABLE "+year+"_"+resource+"(id INT AUTO_INCREMENT PRIMARY KEY,time datetime,station VARCHAR(100),data VARCHAR(50))";
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
	public List<IndexResource> queryByConditionAndResourceGroupId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String resourceGroupId){
		String jpal = " SELECT p FROM ResourceRelation m,IndexResource p ";
		if (condition != null) {
			jpal +=convertBeanToJPAL(condition, joinMode);
			jpal +=" and m.resourceGroup.id="+resourceGroupId+" and m.indexResource.id=p.id ";
		} 
		else
			jpal +="where m.resourceGroup.id="+resourceGroupId+" and m.indexResource.id=p.id ";
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
	}
	
	public List<IndexResource> queryByConditionAndUserId(Object condition, IOrder order,
			Pagination<IndexResource> page, JoinMode joinMode,String userId){
		String jpal = " SELECT p FROM UserIndexRelation m,IndexResource p ";
		if (condition != null) {
			jpal +=convertBeanToJPAL(condition, joinMode);
			jpal +=" and m.user.id="+userId+" and m.indexResource.id=p.id ";
		} 
		else
			jpal +="where m.user.id="+userId+" and m.indexResource.id=p.id ";
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
	}
	public List<IndexResource> queryByResourceGroupId(int resourceGroupId){
		String jpal = " SELECT p FROM ResourceRelation m,IndexResource p where m.resourceGroup.id="+resourceGroupId+" and m.indexResource.id=p.id ";

		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);

		return query.getResultList();

	}
	@Override
	public List<IndexResource> getIndexResourceListNotInThisUser(String userId,String resourceGroupId){
		String jpal = " SELECT p FROM IndexResource p,UserIndexRelation m,ResourceRelation n where p.id=n.indexResource.id and p.id=m.indexResource.id and m.user.id="+userId+" and n.resourceGroup.id="+resourceGroupId;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List<IndexResource> list1=query.getResultList();
		List<IndexResource> list2=this.queryByResourceGroupId(Integer.parseInt(resourceGroupId));
		List<IndexResource> list=new ArrayList();
		for(int i=0;i<list2.size();i++){
			boolean check=false;
			for(int j=0;j<list1.size();j++){
				if(list1.get(j).getId()==list2.get(i).getId())
					check=true;
			}
			if(check==false)
				list.add(list2.get(i));
		}
		return list;	
	}
	@Override
	public boolean checkIndexResourceEnName(String indexResourceEnName,int resourceGroupId){
		String jpal = " SELECT p FROM IndexResource p,ResourceRelation m where m.indexResource.id=p.id and m.resourceGroup.id="+resourceGroupId+" and p.indexEnName='"+indexResourceEnName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
	@Override
	public boolean checkIndexResourceName(String indexResourceName,int resourceGroupId){
		String jpal = " SELECT p FROM IndexResource p,ResourceRelation m where m.indexResource.id=p.id and m.resourceGroup.id="+resourceGroupId+" and p.indexName='"+indexResourceName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
	@Override
	public IndexResource findByEnName(String indexResourceEnName){
		IndexResource indexResource=new IndexResource();
		String jpal = " SELECT p FROM IndexResource p where p.indexEnName='"+indexResourceEnName+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(!list.isEmpty())
			indexResource=(IndexResource) list.get(0);
		return indexResource;
	}
	@Override
	public boolean checkYear(String year,String indexResoure){
		String jpal = " SELECT p FROM ResourceTable p where p.year='"+year+"'"+" and p.indexResource.indexEnName='"+indexResoure+"'";
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
	@Override
	public boolean checkIfHaveTable(String ids){
		String jpal = " SELECT p FROM ResourceTable p where p.indexResource.id="+ids;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		if(list.isEmpty())
			return false;
		else
			return true;
		
	}
}
 
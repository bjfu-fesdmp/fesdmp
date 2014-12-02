
package cn.bjfu.fesdmp.sys.dao.impl;  


import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceTable;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.sys.dao.IResourceTableDao;


@Repository
public class ResourceTableDaoImpl extends AbstractGenericDao<ResourceTable> implements IResourceTableDao {
	
	private static final Logger logger = Logger.getLogger(ResourceTableDaoImpl.class);

	public ResourceTableDaoImpl() {
		super(ResourceTable.class);
	}
	@Override
	public ResourceTable findTableByIndexEnNameAndYear(String indexEnName,String year){
		String jpal = " SELECT p FROM ResourceTable p where p.indexResource.indexEnName='"+indexEnName+"' and p.year="+year;
		logger.info(jpal);
		Query query = super.getEntityManager().createQuery(jpal);
		List list=query.getResultList();
		ResourceTable resourceTable=new ResourceTable();
		if(!list.isEmpty())
			resourceTable=(ResourceTable)list.get(0);
		return resourceTable;
	}
}
 
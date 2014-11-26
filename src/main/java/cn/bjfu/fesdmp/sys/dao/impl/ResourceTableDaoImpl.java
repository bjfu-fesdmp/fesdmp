
package cn.bjfu.fesdmp.sys.dao.impl;  


import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceTable;
import cn.bjfu.fesdmp.sys.dao.IResourceTableDao;


@Repository
public class ResourceTableDaoImpl extends AbstractGenericDao<ResourceTable> implements IResourceTableDao {
	
	private static final Logger logger = Logger.getLogger(ResourceTableDaoImpl.class);

	public ResourceTableDaoImpl() {
		super(ResourceTable.class);
	}
	
	
}
 
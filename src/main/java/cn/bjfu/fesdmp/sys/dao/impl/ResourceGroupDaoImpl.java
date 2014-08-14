package cn.bjfu.fesdmp.sys.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;

@Repository
public class ResourceGroupDaoImpl extends AbstractGenericDao<ResourceGroup> implements IResourceGroupDao{

	private static final Logger logger = Logger.getLogger(ResourceGroupDaoImpl.class);
	
	public ResourceGroupDaoImpl() {
		super(ResourceGroup.class);
	}

}


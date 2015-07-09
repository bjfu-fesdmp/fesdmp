package cn.bjfu.fesdmp.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bjfu.fesdmp.domain.sys.LocationResourceGroupRelation;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.sys.dao.ILocationResourceRelationDao;
import cn.bjfu.fesdmp.sys.dao.IResourceGroupDao;
import cn.bjfu.fesdmp.sys.dao.IUserDao;

@Repository
public class LocationResourceGroupDaoImpl extends AbstractGenericDao<LocationResourceGroupRelation> implements ILocationResourceRelationDao{

	private static final Logger logger = Logger.getLogger(LocationResourceGroupDaoImpl.class);
	
	public LocationResourceGroupDaoImpl() {
		super(LocationResourceGroupRelation.class);
	}
}

  
package cn.bjfu.fesdmp.sys.service.impl;  

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.sys.dao.ILocationDao;
import cn.bjfu.fesdmp.sys.dao.ISystemLogDao;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.ISystemLogService;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LocationSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


@Service
@Transactional
public class LocationService implements ILocationService {

	@Autowired
	private ILocationDao locationDao;
	
	@Override
	public void addLocation(Location location) {
		this.locationDao.insert(location);
	}

	@Override
	public void deleteLocation(int id) {
	
		Location location=this.locationDao.findByKey(id);
			this.locationDao.delete(location);
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> queryAll(IOrder order) {
		return this.locationDao.findAll(order);
	}
	
	@Transactional(readOnly = true)
	@Override
	public void queryByCondition(Object condition, IOrder order,
			Pagination<Location> page) {
		this.locationDao.findByCondition(condition, order, page);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Location> queryByCondition(Object condition, IOrder order,
			Pagination<Location> page, JoinMode joinMode) {
		return this.locationDao.findByCondition(condition, order, page, joinMode);
	}

	@Override
	public List<Location> queryByCondtinWithName(LocationSearch condition,
			IOrder order, Pagination<Location> page, JoinMode joinMode) {
		return this.locationDao.findByCondtinWithName(condition, order, page, joinMode);
	}
	@Override
	public boolean checkLocationName(String locationName){
		return this.locationDao.checkLocationName(locationName);
	}
	@Override
	public void modifyLocation(Location location) {
		Location locationnew = this.locationDao.findByKey(location.getId());
		locationnew.setLocationName(location.getLocationName());

		if(location.getMemo()!=null)
			locationnew.setMemo(location.getMemo());

		this.locationDao.update(locationnew);
		
	}
	@Override
	public int findLocationIdByResourceGroupId(int resourceGroupId){
		return this.locationDao.findLocationIdByResourceGroupId(resourceGroupId);
	}
}
 
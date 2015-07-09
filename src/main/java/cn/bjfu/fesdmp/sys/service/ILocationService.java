
  
package cn.bjfu.fesdmp.sys.service;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LocationSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


public interface ILocationService {

	public abstract void addLocation(Location location);
	public abstract void deleteLocation(int id);
	public abstract int findLocationIdByResourceGroupId(int resourceGroupId);
	public abstract void modifyLocation(Location location);
	public abstract List<Location> queryAll(IOrder order);
	public abstract void queryByCondition(final Object condition, IOrder order, Pagination<Location> page);
	public abstract List<Location> queryByCondition(final Object condition, IOrder order,
			Pagination<Location> page, JoinMode joinMode);
	public List<Location> queryByCondtinWithName(LocationSearch condition,
			IOrder order, Pagination<Location> page, JoinMode joinMode);
	public abstract boolean checkLocationName(String locationName);
}
 
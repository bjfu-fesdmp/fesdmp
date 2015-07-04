
package cn.bjfu.fesdmp.sys.dao;  

import java.util.List;

import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.jsonbean.LocationSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


public interface ILocationDao extends IGenericDao<Location> {


	public abstract List<Location> findByCondtinWithName(final LocationSearch condition, 
			IOrder order, Pagination<Location> page, JoinMode joinMode);
	public abstract boolean checkLocationName(String locationName);
}
 
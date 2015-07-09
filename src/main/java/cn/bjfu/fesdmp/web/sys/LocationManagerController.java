/** 
 * Project Name:fesdmp 
 * File Name:LogManagerController.java 
 * Package Name:cn.bjfu.fesdmp.web.sys 
 * Date:2014年7月9日 上午10:43:24 
 * Copyright (c) 2014, zhangzhaoyu0524@163.com All Rights Reserved. 
 * 
*/  
  
package cn.bjfu.fesdmp.web.sys;  

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.ISystemLogService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.jsonbean.LocationSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;


@Controller
@RequestMapping(value = "/location")
public class LocationManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(LocationManagerController.class);
	private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ILocationService locationService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String syslogPage() {
		logger.info("locationPage method.");
		return "location/locationView";
	}
	
	@RequestMapping(value = "/locationList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> locationList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("locationList method.");
		logger.info(pageInfo);
		LocationSearch locationSearch = null;
		
		Pagination<Location> page = new Pagination<Location>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			locationSearch = mapper.readValue(pageInfo.getSearchJson(), LocationSearch.class);
		}
		
		logger.info(locationSearch);
		
		this.locationService.queryByCondtinWithName(locationSearch, order, page, JoinMode.AND);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="区域管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="添加区域") 
	@ResponseBody
	public Map<String, Object> addLocation(HttpServletRequest request,String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addLocation method.");
		Location location = new Location();
		if (!StringUtils.isEmpty(formData)) {
			location = mapper.readValue(formData,Location.class);
		}

		logger.info(location);
	 	this.locationService.addLocation(location);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/checkLocationName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLocationName(String locationName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkLocationName method.");
		logger.info(locationName);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult=this.locationService.checkLocationName(locationName);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	
	@RequestMapping(value = "/deleteLocation", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="区域管理", bussinessType="SYS_OPERATE", operateType = "DELETE", desc="删除区域") 
	@ResponseBody
	public Map<String, Object> deleteLocation(String ids) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteLocation method.");
		System.out.println(ids);
		this.locationService.deleteLocation(Integer.parseInt(ids));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/modifyLocation", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="区域管理", bussinessType="SYS_OPERATE", operateType = "UPDATE", desc="修改区域") 
	@ResponseBody
	public Map<String, Object> modifyLocation(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("modifyLocation method.");
		Location location = mapper.readValue(formData,Location.class);		
		this.locationService.modifyLocation(location);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/getAllLocationList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllLocationList()
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("getAllLocationList method.");
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		List<Location> locationList = this.locationService.queryAll(order);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, locationList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
}
 
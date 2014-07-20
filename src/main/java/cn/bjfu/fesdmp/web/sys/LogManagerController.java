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
import java.util.Map;

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

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.sys.service.ISystemLogService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

/** 
 * ClassName:LogManagerController <br/> 
 * Function: LogManagerController 系统的日志管理控制器. <br/> 
 * Reason:   LogManagerController. <br/> 
 * Date:     2014年7月9日 上午10:43:24 <br/> 
 * @author   zhangzhaoyu 
 * @version   1.0
 * @since    JDK 1.7 
 * @see       
 */
@Controller
@RequestMapping(value = "/syslog")
public class LogManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(LogManagerController.class);
	private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private ISystemLogService systemLogService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String syslogPage() {
		logger.info("syslogPage method.");
		return "log/logListView";
	}
	
	@RequestMapping(value = "/systemlogList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> systemlogList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("systemlogList method.");
		logger.info(pageInfo);
		LogSearch logSearch = null;
		
		Pagination<SystemLog> page = new Pagination<SystemLog>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			logSearch = mapper.readValue(pageInfo.getSearchJson(), LogSearch.class);
		}
		
		logger.info(logSearch);
		
		this.systemLogService.queryByCondtinWithOperationTime(logSearch, order, page, JoinMode.AND);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageCount", page.getTotalRecord());
		result.put("result", page.getDatas());
		result.put("success", Boolean.TRUE);
		return result;
	}
	
	
}
 
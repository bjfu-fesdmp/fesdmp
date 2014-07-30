
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

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.IndexResourceSearch;
import cn.bjfu.fesdmp.web.jsonbean.LogSearch;

@Controller
@RequestMapping(value = "/indexresource")
public class IndexManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(IndexManagerController.class);
	private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IIndexResourceService indexService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String indexResourcePage() {
		logger.info("indexResourcePage method.");
		return "indexResource/indexResourceView";
	}
	
	@RequestMapping(value = "/indexResourceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("indexResourceList method.");
		logger.info(pageInfo);
//		LogSearch logSearch = null;
		IndexResourceSearch indResourceSearch = null;
		
		Pagination<IndexResource> page = new Pagination<IndexResource>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
//		order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");
		
/*		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			logSearch = mapper.readValue(pageInfo.getSearchJson(), LogSearch.class);
		}
		
		logger.info(logSearch);*/
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			indResourceSearch = mapper.readValue(pageInfo.getSearchJson(), IndexResourceSearch.class);
		}
		
		logger.info(indResourceSearch);
		
//		this.indexService.queryByCondtinWithOperationTime(logSearch, order, page, JoinMode.AND);
//		this.indexService.queryAll(order);
		this.indexService.queryByCondition(indResourceSearch, order, page, JoinMode.OR );
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	
}
 
 
  
package cn.bjfu.fesdmp.web.sys;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.UserGroupJson;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.UserGroupSearch;


@Controller
@RequestMapping(value = "/sysuserGroup")
public class UserGroupManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserManagerController.class);
	//private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IUserGroupService userGroupService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String userGroupPage() {
		logger.info("sysuserPage method.");
		return "userGroup/userGroupView";
	}
	
	@RequestMapping(value = "/userGroupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userGroupList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("userGroupList method.");
		logger.info(pageInfo);
		UserGroupSearch userGroupSearch = null;
		
		Pagination<UserGroup> page = new Pagination<UserGroup>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		//order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			userGroupSearch = mapper.readValue(pageInfo.getSearchJson(), UserGroupSearch.class);
		}
		
		logger.info(userGroupSearch);
		
		
		this.userGroupService.queryByCondition(userGroupSearch,order,page, JoinMode.AND);;
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<UserGroupJson> userGroupJsonList = new ArrayList<UserGroupJson>();
		for (int i=0; i<page.getDatas().size(); i++) {
			UserGroup userGroup = page.getDatas().get(i);
			UserGroupJson userGroupJson = new UserGroupJson();
			userGroupJson.setId(userGroup.getId());
			userGroupJson.setCreaterId(userGroup.getCreater().getId());
			userGroupJson.setCreateTime(userGroup.getCreateTime());
			userGroupJson.setUserGroupName(userGroup.getUserGroupName());
			userGroupJsonList.add(userGroupJson);
		}

		result.put(RESULT, userGroupJsonList);
		//result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	
}
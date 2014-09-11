 
  
package cn.bjfu.fesdmp.web.sys;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.dao.IUserGroupDao;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.sys.service.impl.UserGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Controller
@RequestMapping(value = "/sysuser")
public class UserManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserManagerController.class);
	//private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IUserService userService;
	private IUserGroupDao userGroupDao;
	private UserGroupService userGroupService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String userPage() {
		logger.info("sysuserPage method.");
		return "user/userView";
	}
	
	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("userList method.");
		logger.info(pageInfo);
		UserSearch userSearch = null;
		
		Pagination<User> page = new Pagination<User>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		//order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			userSearch = mapper.readValue(pageInfo.getSearchJson(), UserSearch.class);
		}
		
		logger.info(userSearch);
		
		
		this.userService.queryByCondition(userSearch,order,page, JoinMode.AND);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<UserJson> userJsonList = new ArrayList<UserJson>();
		for (int i=0; i<page.getDatas().size(); i++) {
			User user = page.getDatas().get(i);
			UserJson userJson = new UserJson();
			userJson.setId(user.getId());
			if(user.getEmail()!=null)
			userJson.setEmail(user.getEmail());
			if(user.getCreater()!=null)
			userJson.setCreaterId(user.getCreater().getId());
			userJson.setCreateTime(user.getCreateTime());
			userJson.setIsAdmin(user.getIsAdmin());
			userJson.setUserLoginName(user.getUserLoginName());
			if(user.getUserName()!=null)
			userJson.setUserName(user.getUserName());
			if(user.getUserPhone()!=null)
			userJson.setUserPhone(user.getUserPhone());
			userJson.setUserStatus(user.getUserStatus());
			userJsonList.add(userJson);
		}

		result.put(RESULT, userJsonList);
//		result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUser(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("userList method.");
		AddUserJson addUserJson = new AddUserJson();
		User user = new User();
		UserUserGroupRelation userUserGroupRelation =new UserUserGroupRelation();
		if (!StringUtils.isEmpty(formData)) {
			addUserJson = mapper.readValue(formData,AddUserJson.class);
		}
		logger.info(addUserJson);
		Date dt=new Date();
		user.setCreateTime(dt);
		user.setEmail(addUserJson.getEmail());
		user.setPassword(addUserJson.getPassword());
		user.setUserLoginName(addUserJson.getUserLoginName());
		user.setUserName(addUserJson.getUserName());
		user.setUserPhone(addUserJson.getUserPhone());
		user.setIsAdmin((byte)0);
		user.setUserStatus((byte)1);
		this.userService.addUser(user,addUserJson.getUserGroup());

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
}
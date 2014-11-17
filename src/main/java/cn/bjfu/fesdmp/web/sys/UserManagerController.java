 
  
package cn.bjfu.fesdmp.web.sys;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.UserJson;
import cn.bjfu.fesdmp.sys.service.IUserService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.jsonbean.UserSearch;


@Controller
@RequestMapping(value = "/sysuser")
public class UserManagerController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IUserService userService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String userPage() {
		logger.info("sysuserPage method.");
		return "user/userView";
	}
	
	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userList(HttpServletRequest request,PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		User user09= (User) request.getSession().getAttribute("user");
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
	@MethodRecordLog(moduleName="用户管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="添加用户") 
	@ResponseBody
	public Map<String, Object> addUser(HttpServletRequest request,@RequestParam String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("userList method.");
		AddUserJson addUserJson = new AddUserJson();
		User user = new User();
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
		User buildUser=(User) request.getSession().getAttribute("user");
		user.setCreater(buildUser);

		this.userService.addUser(user,addUserJson.getUserGroup());

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUser(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("modifyUserGroup method.");
		AddUserJson addUserJson = mapper.readValue(formData,AddUserJson.class);		
		this.userService.modifyUser(addUserJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(String ids) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteUserGroup method.");
		logger.info(ids);
		Map<String, Object> result = new HashMap<String, Object>();
		User user=this.userService.findByKey(Integer.parseInt(ids));
		if (user.getIsAdmin()==0){
		this.userService.deleteUser(Integer.parseInt(ids));
		result.put(SUCCESS, Boolean.TRUE);
		}
		else
			result.put(SUCCESS, Boolean.FALSE);	
			return result;	
	}
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUserName(String userName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkUserName method.");
		logger.info(userName);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult=this.userService.checkUserName(userName);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkUserLoginName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUserLoginName(String userLoginName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkUserLoginName method.");
		logger.info(userLoginName);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult=this.userService.checkUserLoginName(userLoginName);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkIfHidden", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkIfHidden(HttpServletRequest request) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIfHidden method.");
		User user=(User) request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		if(user.getIsAdmin().equals((byte)1))
			result.put(SUCCESS, Boolean.FALSE);	
		else 
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkFunctionIfForbid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkFunctionIfForbid(HttpServletRequest request,String tableName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkFunctionIfHidden method.");
		User user=(User) request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
			if(tableName.length()>4){
				if(tableName.charAt(4)=='_'){
					if(user.getIsAdmin().equals((byte)1))
						result.put(SUCCESS, Boolean.FALSE);	
					else if(this.userService.checkIfHaveAuthority(user.getId(),tableName.substring(5)))
						result.put(SUCCESS, Boolean.FALSE);
					else
						result.put(SUCCESS, Boolean.TRUE);
				}
				else
					result.put(SUCCESS, Boolean.TRUE);
			}
			else
				result.put(SUCCESS, Boolean.TRUE);	
		}
		else
			result.put(SUCCESS, Boolean.TRUE);	
					return result;	
	}
}
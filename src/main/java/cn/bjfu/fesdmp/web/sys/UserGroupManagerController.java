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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;








import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.domain.sys.UserUserGroupRelation;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.UserGroupJson;
import cn.bjfu.fesdmp.sys.service.IUserGroupRoleRelationService;
import cn.bjfu.fesdmp.sys.service.IUserGroupService;
import cn.bjfu.fesdmp.sys.service.IUserUserGroupRelationService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.UserGroupSearch;
import cn.bjfu.fesdmp.web.jsonbean.UserUserGroupRelationSearch;

@Controller
@RequestMapping(value = "/sysuserGroup")
public class UserGroupManagerController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(UserManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private IUserUserGroupRelationService userUserGroupRelationService;
//	@Autowired
//	private IUserGroupRoleRelationService userGroupRoleRelationService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String userGroupPage() {
		logger.info("sysuserGeoupPage method.");
		return "userGroup/userGroupView";
	}

	@RequestMapping(value = "/userGroupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userGroupList(PageInfoBean pageInfo)
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		logger.info("userGroupList method.");
		logger.info(pageInfo);
		UserGroupSearch userGroupSearch = null;

		Pagination<UserGroup> page = new Pagination<UserGroup>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());

		IOrder order = new Order();
		// order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");

		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			userGroupSearch = mapper.readValue(pageInfo.getSearchJson(),
					UserGroupSearch.class);
		}

		logger.info(userGroupSearch);

		this.userGroupService.queryByCondition(userGroupSearch, order, page,
				JoinMode.AND);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<UserGroupJson> userGroupJsonList = new ArrayList<UserGroupJson>();
		for (int i = 0; i < page.getDatas().size(); i++) {
			UserGroup userGroup = page.getDatas().get(i);
			UserGroupJson userGroupJson = new UserGroupJson();
			userGroupJson.setId(userGroup.getId());
			userGroupJson.setCreateTime(userGroup.getCreateTime());
			userGroupJson.setUserGroupName(userGroup.getUserGroupName());
			if(userGroup.getCreater()!=null)
			userGroupJson.setCreaterId(userGroup.getCreater().getId());
//			userGroupJson.setRoleName(this.userGroupRoleRelationService.findUserGroupRoleRelationByUserGroupId(Integer.toString(userGroup.getId())).getRole().getRoleName());
//			userGroupJson.setRole(this.userGroupRoleRelationService.findUserGroupRoleRelationByUserGroupId(Integer.toString(userGroup.getId())).getRole().getId());
			userGroupJsonList.add(userGroupJson);
		}
		result.put(RESULT, userGroupJsonList);
		// result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/addUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUserGroup(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("userGroupList method.");
		UserGroupJson userGroupJson = null;
		if (!StringUtils.isEmpty(formData)) {
			userGroupJson = mapper.readValue(formData,UserGroupJson.class);
		}
		logger.info(userGroupJson);
		Date dt=new Date();
		userGroupJson.setCreateTime(dt);
		this.userGroupService.addUserGroup(userGroupJson);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

@RequestMapping(value = "/getUserGroupList", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getUserGroupList()
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	UserGroupSearch userGroupSearch = null;

	Pagination<UserGroup> page = new Pagination<UserGroup>();
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");

	logger.info(userGroupSearch);

	this.userGroupService.queryByCondition(userGroupSearch, order, page,
			JoinMode.AND);
	

	Map<String, Object> result = new HashMap<String, Object>();
	List<UserGroupJson> userGroupJsonList = new ArrayList<UserGroupJson>();
	for (int i = 0; i < page.getDatas().size(); i++) {
		UserGroup userGroup = page.getDatas().get(i);
		UserGroupJson userGroupJson = new UserGroupJson();
		userGroupJson.setId(userGroup.getId());
		userGroupJson.setUserGroupId(userGroup.getId());;
		userGroupJson.setCreateTime(userGroup.getCreateTime());
		userGroupJson.setUserGroupName(userGroup.getUserGroupName());
		if(userGroup.getCreater()!=null)
		userGroupJson.setCreaterId(userGroup.getCreater().getId());
		userGroupJsonList.add(userGroupJson);
	}
	result.put(RESULT, userGroupJsonList);
	return result;
}

@RequestMapping(value = "/deleteUserGroup", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> deleteUserGroup(String ids) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("deleteUserGroup method.");
	logger.info(ids);
	Map<String, Object> result = new HashMap<String, Object>();
	if(this.userUserGroupRelationService.findUserUserGroupRelationByUserGroupId(ids).getId()!=null){
		result.put(SUCCESS, Boolean.FALSE);	
	}
	else{
	this.userGroupService.deleteUserGroup(Integer.parseInt(ids));
	result.put(SUCCESS, Boolean.TRUE);
	}
	return result;
}
@RequestMapping(value = "/modifyUserGroup", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> modifyUserGroup(String formData) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("modifyUserGroup method.");
	UserGroup userGroup = mapper.readValue(formData,UserGroup.class);
	this.userGroupService.modifyUserGroup(userGroup);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/findUserGroupIdAndName", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> findUserGroupIdAndName(String id) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("findUserGroupIdAndName method.");
	UserUserGroupRelation userUserGroupRelation=userUserGroupRelationService.findUserUserGroupRelationByUserId(id);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, userUserGroupRelation.getUserGroup());
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
}

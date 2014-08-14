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


import cn.bjfu.fesdmp.domain.sys.Role;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.RoleJson;
import cn.bjfu.fesdmp.sys.service.IRoleService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.RoleSearch;

@Controller
@RequestMapping(value = "/role")
public class RoleManagerController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(RoleManagerController.class);
	// private Gson gson = new
	// GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String rolePage() {
		logger.info("rolePage method.");
		return "role/roleView";
	}

	@RequestMapping(value = "/roleList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> roleList(PageInfoBean pageInfo)
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		logger.info("roleList method.");
		logger.info(pageInfo);
		RoleSearch roleSearch = null;

		Pagination<Role> page = new Pagination<Role>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());

		IOrder order = new Order();
		// order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");

		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			roleSearch = mapper.readValue(pageInfo.getSearchJson(),
					RoleSearch.class);
		}

		logger.info(roleSearch);

		this.roleService.queryByCondition(roleSearch, order, page,
				JoinMode.AND);


		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<RoleJson> roleJsonList = new ArrayList<RoleJson>();
		for (int i = 0; i < page.getDatas().size(); i++) {
			Role role = page.getDatas().get(i);
			RoleJson roleJson = new RoleJson();
			roleJson.setId(role.getId());
			roleJson.setCreateTime(role.getCreateTime());
			roleJson.setRoleName(role.getRoleName());
			roleJson.setRoleDiscription(role.getRoleDescription());
			if(role.getCreater()!=null)
			roleJson.setCreaterId(role.getCreater().getId());
			roleJsonList.add(roleJson);
		}
		result.put(RESULT, roleJsonList);
		// result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("roleList method.");
		Role role = null;
		if (!StringUtils.isEmpty(formData)) {
			logger.info("readvalue role.");
			role = mapper.readValue(formData,Role.class);
		}
		logger.info(role);
		Date dt=new Date();
		role.setCreateTime(dt);
		this.roleService.addRole(role);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
@ResponseBody
public String getRolepList(PageInfoBean pageInfo)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	RoleSearch roleSearch = null;

	Pagination<Role> page = new Pagination<Role>();
	page.setPageSize(pageInfo.getLimit());
	page.setCurrentPage(pageInfo.getPage());
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");

	logger.info(roleSearch);

	this.roleService.queryByCondition(roleSearch, order, page,
			JoinMode.AND);
	

	String result=page.getDatas().toString();
	System.out.println(result+"0101010101001");
	return result;
}

@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> deleteRole(String ids) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("deleteRole method.");
	System.out.println(ids);
	this.roleService.deleteRole(Integer.parseInt(ids));
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
}

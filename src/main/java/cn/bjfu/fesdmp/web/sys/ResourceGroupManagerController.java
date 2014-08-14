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



import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.ResourceGroupJson;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.ResourceGroupSearch;

@Controller
@RequestMapping(value = "/resourceGroup")
public class ResourceGroupManagerController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ResourceGroupManagerController.class);
	// private Gson gson = new
	// GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IResourceGroupService resourceGroupService;

	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String resourceGroupPage() {
		logger.info("resourceGroupPage method.");
		return "resourceGroup/resourceGroupView";
	}

	@RequestMapping(value = "/resourceGroupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resourceGroupList(PageInfoBean pageInfo)
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		logger.info("resourceGroupList method.");
		logger.info(pageInfo);
		ResourceGroupSearch resourceGroupSearch = null;

		Pagination<ResourceGroup> page = new Pagination<ResourceGroup>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());

		IOrder order = new Order();
		// order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");

		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			resourceGroupSearch = mapper.readValue(pageInfo.getSearchJson(),
					ResourceGroupSearch.class);
		}

		logger.info(resourceGroupSearch);

		this.resourceGroupService.queryByCondition(resourceGroupSearch, order, page,
				JoinMode.AND);


		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<ResourceGroupJson> resourceGroupJsonList = new ArrayList<ResourceGroupJson>();
		for (int i = 0; i < page.getDatas().size(); i++) {
			ResourceGroup resourceGroup = page.getDatas().get(i);
			ResourceGroupJson resourceGroupJson = new ResourceGroupJson();
			resourceGroupJson.setId(resourceGroup.getId());
			resourceGroupJson.setGroupParentId(resourceGroup.getGroupParentId());
			resourceGroupJson.setGroupName(resourceGroup.getGroupName());
			resourceGroupJson.setMemo(resourceGroup.getMemo());
			resourceGroupJsonList.add(resourceGroupJson);
		}
		result.put(RESULT, resourceGroupJsonList);
		// result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/addResourceGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResourceGroup(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("userGroupList method.");
		ResourceGroup resourceGroup = null;
		if (!StringUtils.isEmpty(formData)) {
			resourceGroup = mapper.readValue(formData,ResourceGroup.class);
		}
		logger.info(resourceGroup);
//		Date dt=new Date();
//		resourceGroup.setCreateTime(dt);
		this.resourceGroupService.addResourceGroup(resourceGroup);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

@RequestMapping(value = "/getResourceGroupList", method = RequestMethod.POST)
@ResponseBody
public String getResourceGroupList(PageInfoBean pageInfo)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	ResourceGroupSearch resourceGroupSearch = null;

	Pagination<ResourceGroup> page = new Pagination<ResourceGroup>();
	page.setPageSize(pageInfo.getLimit());
	page.setCurrentPage(pageInfo.getPage());
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");

	logger.info(resourceGroupSearch);

	this.resourceGroupService.queryByCondition(resourceGroupSearch, order, page,
			JoinMode.AND);
	

	String result=page.getDatas().toString();
	System.out.println(result+"0101010101001");
	return result;
}

@RequestMapping(value = "/deleteResourceGroup", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> deleteResourceGroup(String ids) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("deleteResourceGroup method.");
	System.out.println(ids);
	this.resourceGroupService.deleteResourceGroup(Integer.parseInt(ids));
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
}

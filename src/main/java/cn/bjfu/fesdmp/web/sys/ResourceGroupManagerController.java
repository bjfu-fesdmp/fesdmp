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
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.ResourceGroupJson;
import cn.bjfu.fesdmp.json.ResourceGroupTreeJson;
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
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IResourceGroupService resourceGroupService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String resourceGroupPage() {
		logger.info("resourceGroupPage method.");
		return "resourceGroup/resourceGroupView";
	}
//资源组树形结构
	@RequestMapping(value = "/resourceGroupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resourceGroupList(String groupParentId,String roleId)
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("resourceGroupList method.");
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		List<ResourceGroup> resourceGroupList=new ArrayList();
		if(roleId!=null)
			resourceGroupList=this.resourceGroupService.findResourceGroupByroleId(roleId);
		else if(groupParentId!=null)
			resourceGroupList = this.resourceGroupService.findResourceGroupById(Integer.parseInt(groupParentId));

		List<ResourceGroupTreeJson> resourceGroupJsonList = new ArrayList<ResourceGroupTreeJson>();
		for(int i=0;i<resourceGroupList.size();i++){
			ResourceGroupTreeJson resourceGroupTreeJson=new ResourceGroupTreeJson();
			resourceGroupTreeJson.setGroupName(resourceGroupList.get(i).getGroupName());
			resourceGroupTreeJson.setGroupParentId(resourceGroupList.get(i).getGroupParentId());
			resourceGroupTreeJson.setId(resourceGroupList.get(i).getId());
			resourceGroupTreeJson.setMemo(resourceGroupList.get(i).getMemo());
			if(this.resourceGroupService.ifHaveChild(resourceGroupTreeJson.getId()))
				resourceGroupTreeJson.setLeaf(false);
			else
				resourceGroupTreeJson.setLeaf(true);
			resourceGroupJsonList.add(resourceGroupTreeJson);
			
		}
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, resourceGroupJsonList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/addResourceGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResourceGroup(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("userGroupList method.");
		ResourceGroup resourceGroup = new ResourceGroup();
		if (!StringUtils.isEmpty(formData)) {
			resourceGroup = mapper.readValue(formData,ResourceGroup.class);
		}
		
		if(resourceGroup.getGroupParentId()==null)
			resourceGroup.setGroupParentId(0);
		
		logger.info(resourceGroup);
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
public Map<String, Object> deleteResourceGroup(String id) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("deleteResourceGroup method.");
	System.out.println(id);
	this.resourceGroupService.deleteResourceGroup(Integer.parseInt(id));
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/modifyResourceGroup", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> modifyResourceGroup(String formData) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("modifyUserGroup method.");
	ResourceGroup resourceGroup = mapper.readValue(formData,ResourceGroup.class);
	this.resourceGroupService.modifyResourceGroup(resourceGroup);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/getAllResourceGroupList", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getAllResourceGroupList()
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("resourceGroupList method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList = this.resourceGroupService.queryAll(order);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}

@RequestMapping(value = "/checkIfHaveIndexResource", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> checkIfHaveIndexResource(String id)
		throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("checkIfHaveIndexResource method.");
	boolean checkResult = this.resourceGroupService.checkIfHaveIndexResource(Integer.parseInt(id));
	Map<String, Object> result = new HashMap<String, Object>();
	if(checkResult==true)
		result.put(SUCCESS, Boolean.TRUE);
	else
		result.put(SUCCESS, Boolean.FALSE);
	return result;
}

@RequestMapping(value = "/resourceGroupOfRoleList", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> resourceGroupOfRoleList(String roleId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("resourceGroupList method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(roleId!=null)
		resourceGroupList=this.resourceGroupService.findResourceGroupByroleId(roleId);

	List<ResourceGroupTreeJson> resourceGroupJsonList = new ArrayList<ResourceGroupTreeJson>();
	for(int i=0;i<resourceGroupList.size();i++){
		ResourceGroupTreeJson resourceGroupTreeJson=new ResourceGroupTreeJson();
		resourceGroupTreeJson.setGroupName(resourceGroupList.get(i).getGroupName());
		resourceGroupTreeJson.setGroupParentId(resourceGroupList.get(i).getGroupParentId());
		resourceGroupTreeJson.setId(resourceGroupList.get(i).getId());
		resourceGroupTreeJson.setMemo(resourceGroupList.get(i).getMemo());
		if(this.resourceGroupService.ifHaveChild(resourceGroupTreeJson.getId()))
			resourceGroupTreeJson.setLeaf(false);
		else
			resourceGroupTreeJson.setLeaf(true);
		resourceGroupJsonList.add(resourceGroupTreeJson);
		
	}
	
	
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupJsonList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/getResourceGroupListNotInThisRole", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getResourceGroupListNotInThisRole(String roleId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("resourceGroupList method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(roleId!=null)
	resourceGroupList = this.resourceGroupService.findResourceGroupNotInThisRole(roleId);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
}

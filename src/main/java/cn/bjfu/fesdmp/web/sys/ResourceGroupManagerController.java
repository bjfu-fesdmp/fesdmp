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

import cn.bjfu.fesdmp.constant.AppConstants;
import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddResourceGroupForUserJson;
import cn.bjfu.fesdmp.json.LocationResourceGroupForUserJson;
import cn.bjfu.fesdmp.json.ResourceGroupJson;
import cn.bjfu.fesdmp.json.ResourceGroupTreeJson;
import cn.bjfu.fesdmp.json.TreeJson;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.jsonbean.ResourceGroupSearch;

@Controller
@RequestMapping(value = "/resourceGroup")
public class ResourceGroupManagerController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ResourceGroupManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IResourceGroupService resourceGroupService;
	@Autowired
	private ILocationService locationService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String resourceGroupPage() {
		logger.info("resourceGroupPage method.");
		return "resourceGroup/resourceGroupView";
	}
//资源组树形结构
	@RequestMapping(value = "/resourceGroupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resourceGroupList(HttpServletRequest request,String groupParentId)
			throws Exception {
		User nowUser=(User) request.getSession().getAttribute(AppConstants.SESSION_USER);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("resourceGroupList method.");
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		List<ResourceGroup> resourceGroupList=new ArrayList();
		if(groupParentId!=null)
			resourceGroupList = this.resourceGroupService.findResourceGroupByParentIdAndUserId(0,nowUser.getId());

		List<ResourceGroupTreeJson> resourceGroupJsonList = new ArrayList<ResourceGroupTreeJson>();
		List<Location> locationList=new ArrayList<Location>();
		IOrder order2 = new Order();
		order2.addOrderBy("id", "DESC");
		locationList=this.locationService.queryAll(order2);
		for(int i=0;i<locationList.size();i++){
			ResourceGroupTreeJson resourceGroupTreeJson=new ResourceGroupTreeJson();
			resourceGroupTreeJson.setGroupName(locationList.get(i).getLocationName());
			resourceGroupTreeJson.setGroupParentId(0);
			resourceGroupTreeJson.setId(locationList.get(i).getId()+1000000000);
			resourceGroupTreeJson.setMemo(locationList.get(i).getMemo());		
			resourceGroupJsonList.add(resourceGroupTreeJson);
		}
		
		for(int i=0;i<resourceGroupList.size();i++){
			ResourceGroupTreeJson resourceGroupTreeJson=new ResourceGroupTreeJson();
			resourceGroupTreeJson.setGroupName(resourceGroupList.get(i).getGroupName());		
			resourceGroupTreeJson.setGroupParentId(this.locationService.findLocationIdByResourceGroupId(resourceGroupList.get(i).getId())+1000000000);
			resourceGroupTreeJson.setId(resourceGroupList.get(i).getId());
			resourceGroupTreeJson.setMemo(resourceGroupList.get(i).getMemo());
			if(this.resourceGroupService.ifHaveChild(resourceGroupTreeJson.getId()))
				resourceGroupTreeJson.setLeaf(false);
			else
				resourceGroupTreeJson.setLeaf(true);
			resourceGroupJsonList.add(resourceGroupTreeJson);
			
		}
		List<ResourceGroupTreeJson> newresourceGroupJsonList = new ArrayList();

		if(Integer.parseInt(groupParentId)==0)
		{
		for (int i = 0; i < resourceGroupJsonList.size(); i++) {
			if (resourceGroupJsonList.get(i).getGroupParentId()
					.equals(0))
				newresourceGroupJsonList.add(resourceGroupJsonList.get(i));
		}
		}
		else
		{
			for (int i = 0; i < resourceGroupJsonList.size(); i++) {
				if (resourceGroupJsonList.get(i).getGroupParentId()
						.equals(Integer.parseInt(groupParentId)))
					newresourceGroupJsonList.add(resourceGroupJsonList.get(i));
		}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, newresourceGroupJsonList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/addResourceGroup", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="资源组管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="添加资源组") 
	@ResponseBody
	public Map<String, Object> addResourceGroup(HttpServletRequest request,String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addResourceGroup method.");
		ResourceGroupJson resourceGroupJson = new ResourceGroupJson();
		if (!StringUtils.isEmpty(formData)) {
			resourceGroupJson = mapper.readValue(formData,ResourceGroupJson.class);
		}
		
		if(resourceGroupJson.getGroupParentId()==null)
			resourceGroupJson.setGroupParentId("0");
		logger.info(resourceGroupJson);
	 	this.resourceGroupService.addResourceGroup(resourceGroupJson);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

@RequestMapping(value = "/getResourceGroupList", method = RequestMethod.POST)
@ResponseBody
public String getResourceGroupList(PageInfoBean pageInfo)
		throws Exception {
	logger.info("getResourceGroupList method.");
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
	return result;
}

@RequestMapping(value = "/deleteResourceGroup", method = RequestMethod.POST)
@MethodRecordLog(moduleName="资源组管理", bussinessType="SYS_OPERATE", operateType = "DELETE", desc="删除资源组") 
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
@MethodRecordLog(moduleName="资源组管理", bussinessType="SYS_OPERATE", operateType = "UPDATE", desc="修改资源组") 
@ResponseBody
public Map<String, Object> modifyResourceGroup(String formData) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("modifyResourceGroup method.");
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
	logger.info("getAllResourceGroupList method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList = this.resourceGroupService.queryAll(order);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/getResourceGroupListOfNowUserAndLocation", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getResourceGroupListOfNowUserAndLocation(HttpServletRequest request,String locationId)
		throws Exception {
	User nowUser=(User) request.getSession().getAttribute(AppConstants.SESSION_USER);
	Map<String, Object> result = new HashMap<String, Object>();
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("getResourceGroupListOfNowUser method.");
	
	if (locationId!=null)
	{
	if (nowUser.getIsAdmin().equals((byte)1)){
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList = this.resourceGroupService.findResourceGroupInThisLocation(Integer.parseInt(locationId));
	result.put(RESULT, resourceGroupList);
	}
	else{
		List<ResourceGroup> resourceGroupList = this.resourceGroupService.findResourceGroupByUserIdAndLocation(nowUser.getId().toString(),Integer.parseInt(locationId));
		result.put(RESULT, resourceGroupList);
	}
	}
	
	
	
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

@RequestMapping(value = "/resourceGroupOfUserList", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> resourceGroupOfUserList(String userId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("resourceGroupOfUserList method.");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(userId!=null)
		resourceGroupList=this.resourceGroupService.findResourceGroupByUserId(userId);
	List<LocationResourceGroupForUserJson> locationResourceGroupForUserJsonList = new ArrayList<LocationResourceGroupForUserJson>();
	for(int i=0;i<resourceGroupList.size();i++){
		LocationResourceGroupForUserJson temp=new LocationResourceGroupForUserJson();
		temp.setId(resourceGroupList.get(i).getId());
		temp.setGroupName(resourceGroupList.get(i).getGroupName());
		if(resourceGroupList.get(i).getMemo()!=null)
			temp.setMemo(resourceGroupList.get(i).getMemo());
		temp.setLocation(this.locationService.findLocationNameByResourceGroupId(temp.getId()));
		locationResourceGroupForUserJsonList.add(temp);
	}
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, locationResourceGroupForUserJsonList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/getResourceGroupListNotInThisUser", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getResourceGroupListNotInThisUser(String userId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("getResourceGroupListNotInThisUser method.");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(userId!=null)
	resourceGroupList = this.resourceGroupService.findResourceGroupNotInThisUser(userId);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/addResourceGroupForUser", method = RequestMethod.POST)
@MethodRecordLog(moduleName="用户管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="为用户添加资源组") 
@ResponseBody
public Map<String, Object> addResourceGroupForUser(String formData) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("addResourceGroupForUser method.");
	AddResourceGroupForUserJson addResourceGroupForUserJson = null;
	if (!StringUtils.isEmpty(formData)) {
		addResourceGroupForUserJson = mapper.readValue(formData,AddResourceGroupForUserJson.class);
	}
	logger.info(addResourceGroupForUserJson);
	this.resourceGroupService.addResourceGroupForUser(addResourceGroupForUserJson);

	Map<String, Object> result = new HashMap<String, Object>();

	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/deleteResourceGroupForUser", method = RequestMethod.POST)
@MethodRecordLog(moduleName="用户管理", bussinessType="SYS_OPERATE", operateType = "DELETE", desc="为用户删除资源组") 
@ResponseBody
public Map<String, Object> deleteResourceGroupForUser(String id,String userId) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("deleteResourceGroupForUser method.");
	this.resourceGroupService.deleteResourceGroupForUser(id,userId);

	Map<String, Object> result = new HashMap<String, Object>();

	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/checkResourceGroupName", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> checkResourceGroupName(String resourceGroupName,int locationId) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("checkResourceGroupName method.");
	logger.info(resourceGroupName);
	Map<String, Object> result = new HashMap<String, Object>();
	boolean checkResult=this.resourceGroupService.checkResourceGroupName(resourceGroupName,locationId);
	if (checkResult==true)
		result.put(SUCCESS, Boolean.FALSE);	
	else
		result.put(SUCCESS, Boolean.TRUE);	
		return result;	
}
@RequestMapping(value = "/getResourceGroupInThisLocation", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getResourceGroupInThisLocation(String locationId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("getAllResourceGroupList method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(locationId!=null)
		resourceGroupList = this.resourceGroupService.findResourceGroupInThisLocation(Integer.parseInt(locationId));
	else
		resourceGroupList = this.resourceGroupService.queryAll(order);
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/getResourceGroupListInThisLocationAndNotInThisUser", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> getResourceGroupListInThisLocationAndNotInThisUser(String locationId,String userId)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("getResourceGroupListInThisLocationAndNotInThisUser method.");
	IOrder order = new Order();
	order.addOrderBy("id", "DESC");
	List<ResourceGroup> resourceGroupList=new ArrayList();
	if(!(locationId==""&&userId=="")){
		List<ResourceGroup> resourceGroupList1=this.resourceGroupService.findResourceGroupInThisLocation(Integer.parseInt(locationId));
		List<ResourceGroup> resourceGroupList2=this.resourceGroupService.findResourceGroupNotInThisUser(userId);
		for(int i=0;i<resourceGroupList1.size();i++){
			for(int j=0;j<resourceGroupList2.size();j++)
			{
				if(resourceGroupList1.get(i).getId()==resourceGroupList2.get(j).getId())
					resourceGroupList.add(resourceGroupList1.get(i));
			}
		}
			
		
	}
	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, resourceGroupList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
}


package cn.bjfu.fesdmp.web.sys;  

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Table;
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
import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.ResourceRelation;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.AddIndexResourceForUserJson;
import cn.bjfu.fesdmp.json.CreateTableJson;
import cn.bjfu.fesdmp.json.IndexResourceJson;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.sys.service.IResourceRelationService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.jsonbean.IndexResourceSearch;

@Controller
@RequestMapping(value = "/indexresource")
public class IndexManagerController extends BaseController {
	
	private String formData;
	
	public String getFormData() {
		return formData;
	}
	
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	private static final Logger logger = Logger.getLogger(IndexManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IIndexResourceService indexService;
	@Autowired
	private IResourceRelationService resourceRelationService; 
	@Autowired
	private IResourceGroupService resourceGroupService; 
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String indexResourcePage() {
		logger.info("indexResourcePage method.");
		return "indexResource/indexResourceView";
	}
	
	@RequestMapping(value = "/indexResourceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexResourceList(PageInfoBean pageInfo,String resourceGroupId) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("indexResourceList method.");
		logger.info(pageInfo);
		IndexResourceSearch indResourceSearch = null;
		Pagination<IndexResource> page = new Pagination<IndexResource>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			indResourceSearch = mapper.readValue(pageInfo.getSearchJson(), IndexResourceSearch.class);
		}		
		logger.info(indResourceSearch);
		
		List<IndexResource> indexResourceList =this.indexService.queryByConditionAndResourceGroupId(indResourceSearch, order, page, JoinMode.AND,resourceGroupId);		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<IndexResourceJson> indexResourceJsonList = new ArrayList<IndexResourceJson>();
		for (int i=0; i<indexResourceList.size(); i++) {
			IndexResource indexResource = page.getDatas().get(i);
			IndexResourceJson indexResourceJson = new IndexResourceJson();
			indexResourceJson.setId(indexResource.getId());
			indexResourceJson.setIndexName(indexResource.getIndexName());
			indexResourceJson.setIndexEnName(indexResource.getIndexEnName());
			indexResourceJson.setIndexUnit(indexResource.getIndexUnit());
			indexResourceJson.setIndexMemo(indexResource.getIndexMemo());
			if(indexResource.getCreater()!=null)
				indexResourceJson.setCreater(indexResource.getCreater().getUserName());
			indexResourceJson.setCreateTime(indexResource.getCreateTime());
			if(indexResource.getModifier()!=null)
				indexResourceJson.setModifier(indexResource.getModifier().getUserName());
			indexResourceJson.setModifyTime(indexResource.getModifyTime());
			indexResourceJsonList.add(indexResourceJson);
		}

		result.put(RESULT,indexResourceJsonList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/addIndexResource", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="指标资源管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="添加指标资源") 
	@ResponseBody
	public Map<String, Object> addIndexResource(HttpServletRequest request,String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addIndexResource method.");
		IndexResourceJson indexResourceJson=new IndexResourceJson();
		IndexResource indexResource = new IndexResource();
		int resourceGroupId = 0;
		if (!StringUtils.isEmpty(formData)) {
			indexResourceJson = mapper.readValue(formData,IndexResourceJson.class);
			indexResource.setIndexName(indexResourceJson.getIndexName());
			indexResource.setIndexEnName(indexResourceJson.getIndexEnName());
			indexResource.setIndexMemo(indexResourceJson.getIndexMemo());
			indexResource.setIndexUnit(indexResourceJson.getIndexUnit());
			User buildUser=(User) request.getSession().getAttribute(AppConstants.SESSION_USER);
			indexResource.setCreater(buildUser);
			resourceGroupId=indexResourceJson.getResourceGroupId();
		}
		logger.info(indexResource);
		
		
		Date dt=new Date();
		indexResource.setCreateTime(dt);
		Date dtm = new Date(70,0,1,0,0,0);
		indexResource.setModifyTime(dtm);
		this.indexService.addIndexResource(indexResource,resourceGroupId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/deleteIndexResource", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="指标资源管理", bussinessType="SYS_OPERATE", operateType = "DELETE", desc="删除指标资源")
	@ResponseBody
	public Map<String, Object> deleteIndexResource(String ids) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteIndexResource method.");
		System.out.println(ids);
		this.indexService.deleteIndexResource(Integer.parseInt(ids));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/modifyIndexResource", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="指标资源管理", bussinessType="SYS_OPERATE", operateType = "UPDATE", desc="修改指标资源")
	@ResponseBody
	public Map<String, Object> modifyIndexResource(HttpServletRequest request,String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("modifyIndexResource method.");
		IndexResource indexResource = mapper.readValue(formData,IndexResource.class);
		User modifyUser=(User) request.getSession().getAttribute(AppConstants.SESSION_USER);
		indexResource.setModifier(modifyUser);
		Date dt=new Date();
		indexResource.setModifyTime(dt);
		this.indexService.modifyIndexResource(indexResource);;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/addTable", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据表管理", bussinessType="DATA_OPERATE", operateType = "ADD", desc="添加数据表")
	@ResponseBody
	public Map<String, Object> addTable(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addTable method.");
		CreateTableJson createTableJson = mapper.readValue(formData,CreateTableJson.class);
		this.indexService.addTableByYear(createTableJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/indexResourceOfUserList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexResourceOfUserList(PageInfoBean pageInfo,String userId) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("indexResourceOfUserList method.");
		logger.info(pageInfo);
		IndexResourceSearch indResourceSearch = null;
		Pagination<IndexResource> page = new Pagination<IndexResource>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		IOrder order = new Order();
		order.addOrderBy("id", "DESC");
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			indResourceSearch = mapper.readValue(pageInfo.getSearchJson(), IndexResourceSearch.class);
		}		
		logger.info(indResourceSearch);
		
		List<IndexResource> indexResourceList =this.indexService.queryByConditionAndUserId(indResourceSearch, order, page, JoinMode.AND,userId);		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<IndexResourceJson> indexResourceJsonList = new ArrayList<IndexResourceJson>();
		for (int i=0; i<indexResourceList.size(); i++) {
			IndexResource indexResource = page.getDatas().get(i);
			IndexResourceJson indexResourceJson = new IndexResourceJson();
			indexResourceJson.setId(indexResource.getId());
			indexResourceJson.setIndexName(indexResource.getIndexName());
			indexResourceJson.setIndexEnName(indexResource.getIndexEnName());
			indexResourceJson.setIndexUnit(indexResource.getIndexUnit());
			indexResourceJson.setIndexMemo(indexResource.getIndexMemo());
			indexResourceJson.setResourceGroupName(this.resourceGroupService.findResourceGroupNameByIndexResourceId(indexResource.getId()));
			if(indexResource.getCreater()!=null)
				indexResourceJson.setCreaterId(indexResource.getCreater().getId());
			indexResourceJson.setCreateTime(indexResource.getCreateTime());
			if(indexResource.getModifier()!=null)
				indexResourceJson.setModifierId(indexResource.getModifier().getId());
			indexResourceJson.setModifyTime(indexResource.getModifyTime());
			indexResourceJsonList.add(indexResourceJson);
		}

		result.put(RESULT,indexResourceJsonList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/getIndexResourceListNotInThisUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getIndexResourceListNotInThisUser(String userId,String resourceGroupId)
			throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("getIndexResourceListNotInThisUser method.");
		List<IndexResource> indexResourceList=new ArrayList();
		if(userId!=null)
			indexResourceList = this.indexService.getIndexResourceListNotInThisUser(userId,resourceGroupId);
		List<IndexResource> newIndexResourceList=new ArrayList();
		for(int i=0;i<indexResourceList.size();i++){
			IndexResource indexResource=new IndexResource();
			indexResource.setId(indexResourceList.get(i).getId());
			indexResource.setIndexEnName(indexResourceList.get(i).getIndexEnName());
			newIndexResourceList.add(indexResource);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, newIndexResourceList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/addIndexResourceForUser", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="用户管理", bussinessType="SYS_OPERATE", operateType = "ADD", desc="为用户添加指标资源") 
	@ResponseBody
	public Map<String, Object> addIndexResourceForUser(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addIndexResourceForUser method.");
		AddIndexResourceForUserJson addIndexResourceForUserJson = null;
		if (!StringUtils.isEmpty(formData)) {
			addIndexResourceForUserJson = mapper.readValue(formData,AddIndexResourceForUserJson.class);
		}
		logger.info(addIndexResourceForUserJson);
		this.indexService.addIndexResourceForUser(addIndexResourceForUserJson);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/deleteIndexResourceForUser", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="用户管理", bussinessType="SYS_OPERATE", operateType = "DELETE", desc="为用户删除指标资源") 
	@ResponseBody
	public Map<String, Object> deleteIndexResourceForUser(String id,String userId) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteIndexResourceForUser method.");
		this.indexService.deleteIndexResourceForUser(id,userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/checkIndexResourceName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIndexResourceName(String indexResourceName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIndexResourceName method.");
		logger.info(indexResourceName);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult=this.indexService.checkIndexResourceName(indexResourceName);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkIndexResourceEnName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIndexResourceEnName(String indexResourceEnName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIndexResourceEnName method.");
		logger.info(indexResourceEnName);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult=this.indexService.checkIndexResourceEnName(indexResourceEnName);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkIfIsYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIfIsYear(String year) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIfIsYear method.");
		logger.info(year);
		Map<String, Object> result = new HashMap<String, Object>();
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(year);
		 if( !isNum.matches() ){
			 result.put(SUCCESS, Boolean.FALSE);	
			 return result;
		 }
		 if(Integer.parseInt(year)<1900||Integer.parseInt(year)>2100){
			 result.put(SUCCESS, Boolean.FALSE);	
			 return result;
		 }
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkIfIsExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIfIsExist(String year,String indexResoure) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIfIsExist method.");
		logger.info(year);
		logger.info(indexResoure);
		Map<String, Object> result = new HashMap<String, Object>();

		boolean checkResult=this.indexService.checkYear(year,indexResoure);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.FALSE);	
		else
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
	@RequestMapping(value = "/checkIfHaveTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIfHaveTable(String ids) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIfHaveTable method.");
		logger.info(ids);
		Map<String, Object> result = new HashMap<String, Object>();

		boolean checkResult=this.indexService.checkIfHaveTable(ids);
		if (checkResult==true)
			result.put(SUCCESS, Boolean.TRUE);	
		else
			result.put(SUCCESS, Boolean.FALSE);	
			return result;	
	}
	@RequestMapping(value = "/checkSpacee", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkSpacee(String indexResourceEnName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkSpacee method.");
		logger.info(indexResourceEnName);
		Map<String, Object> result = new HashMap<String, Object>();
		if (indexResourceEnName.contains(" "))
			result.put(SUCCESS, Boolean.TRUE);	
		else
			result.put(SUCCESS, Boolean.FALSE);	
			return result;	
	}
}
 
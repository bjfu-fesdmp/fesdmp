
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
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.IndexResourceJson;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
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
//	private Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
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
	public Map<String, Object> indexResourceList(PageInfoBean pageInfo) throws Exception {
		
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
		
		this.indexService.queryByCondition(indResourceSearch, order, page, JoinMode.AND );
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		List<IndexResourceJson> indexResourceJsonList = new ArrayList<IndexResourceJson>();
		for (int i=0; i<page.getDatas().size(); i++) {
			IndexResource indexResource = page.getDatas().get(i);
			IndexResourceJson indexResourceJson = new IndexResourceJson();
			indexResourceJson.setId(indexResource.getId());
			indexResourceJson.setIndexName(indexResource.getIndexName());
			indexResourceJson.setIndexEnName(indexResource.getIndexEnName());
			indexResourceJson.setIndexUnit(indexResource.getIndexUnit());
			indexResourceJson.setIndexMemo(indexResource.getIndexMemo());
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
	
	@RequestMapping(value = "/addIndexResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIndexResource(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("addIndexResource method.");
		IndexResource indexResource = null;
		if (!StringUtils.isEmpty(formData)) {
			indexResource = mapper.readValue(formData,IndexResource.class);
		}
		logger.info(indexResource);
		Date dt=new Date();
		indexResource.setCreateTime(dt);
		Date dtm = new Date(70,0,1,0,0,0);
		indexResource.setModifyTime(dtm);
		this.indexService.addIndResource(indexResource);

		Map<String, Object> result = new HashMap<String, Object>();

		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/deleteIndexResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIndexResource(String ids) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteIndexResource method.");
		System.out.println(ids);
		this.indexService.deleteIndResource(Integer.parseInt(ids));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	@RequestMapping(value = "/modifyIndexResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyIndexResource(String formData) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("modifyIndexResource method.");
		IndexResource indexResource = mapper.readValue(formData,IndexResource.class);
		this.indexService.modifyIndResource(indexResource);;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
}
 
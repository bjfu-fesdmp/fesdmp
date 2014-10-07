package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.sys.service.ISystemLogService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;
import cn.bjfu.fesdmp.web.jsonbean.ExtJSFormResult;
import cn.bjfu.fesdmp.web.jsonbean.FileUploadBean;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
@RequestMapping(value = "/dataDisplay")
public class DataDisplayManagerController extends BaseController {

	//这里暂时用的绝对路径
  private static final String FILE_PATH = "C:/Users/Admn/git/fesdmp/src/main/webapp/WEB-INF/Table";
	private static final Logger logger = Logger.getLogger(DataDisplayManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IDataService dataService;
	
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String fileUploadPage() {
		logger.info("fileUploadPage method.");
		return "dataDisplay/dataDisplayView";
	}
	
	@RequestMapping(value = "/dataDisplayList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dataDisplayList(PageInfoBean pageInfo) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("dataDisplayList method.");
		logger.info(pageInfo);
		DataSearch dataSearch = null;
		
		Pagination<DataJson> page = new Pagination<DataJson>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		order.addOrderBy("operateTime", "DESC");
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			dataSearch = mapper.readValue(pageInfo.getSearchJson(), DataSearch.class);
		}
		
		logger.info(dataSearch);
		
		this.dataService.queryByCondtinWithOperationTime(dataSearch, order, page, JoinMode.AND);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	
	
	
	
  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
  @ResponseBody
  public String create(FileUploadBean uploadItem, BindingResult result){  
	   
      ExtJSFormResult extjsFormResult = new ExtJSFormResult();  
 
      if (result.hasErrors()){  
          for(ObjectError error : result.getAllErrors()){  
              System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());  
          }  
          extjsFormResult.setSuccess(false);  
          return extjsFormResult.toString();  
      }  
      if (!uploadItem.getFile().getOriginalFilename().substring(uploadItem.getFile().getOriginalFilename().length()-3,uploadItem.getFile().getOriginalFilename().length()).equals("xls")){  
          for(ObjectError error : result.getAllErrors()){  
              System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());  
          }  
          extjsFormResult.setSuccess(false);  
          return extjsFormResult.toString();  
      }  
      // Some type of file processing...  
      System.err.println("-------------------------------------------");  
      System.err.println("Test upload: " + uploadItem.getFile().getOriginalFilename());  
      System.err.println("-------------------------------------------");  
 
      //saving。。。
      String savePath = FILE_PATH;
      java.io.File folder = new java.io.File(savePath);
      if (!folder.exists()) {
        folder.mkdirs();
      }
      System.out.println("文件保存路径：" + savePath);
      String rndFilename = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-"))
    	        .format(new Date());
      java.io.File file = new java.io.File(savePath+"/"+rndFilename+uploadItem.getFile().getOriginalFilename());
      try {
    	  uploadItem.getFile().transferTo(file); 
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      
      
      //set <a target="_blank" title="extjs" href="http://sencha.com/">extjs</a> return - sucsess  
      extjsFormResult.setSuccess(true);  
 
      return extjsFormResult.toString();
  }

}
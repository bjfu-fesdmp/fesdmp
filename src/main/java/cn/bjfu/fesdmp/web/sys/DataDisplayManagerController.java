package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;
import cn.bjfu.fesdmp.web.jsonbean.ExtJSFormResult;
import cn.bjfu.fesdmp.web.jsonbean.FileUploadBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Map<String, Object> dataDisplayList(PageInfoBean pageInfo,String tableName) throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("dataDisplayList method.");
		logger.info(pageInfo);
		DataSearch dataSearch = null;
		
		Pagination<DataJson> page = new Pagination<DataJson>();
		page.setPageSize(pageInfo.getLimit());
		page.setCurrentPage(pageInfo.getPage());
		
		IOrder order = new Order();
		order.addOrderBy("time", "DESC");
		order.addOrderBy("id", "DESC");
		
		if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
			dataSearch = mapper.readValue(pageInfo.getSearchJson(), DataSearch.class);
		}
		
		logger.info(dataSearch);
		this.dataService.queryByCondtinWithOperationTime(tableName,dataSearch, order, page, JoinMode.AND);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(PAGE_COUNT, page.getTotalRecord());
		result.put(RESULT, page.getDatas());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	
	@RequestMapping(value = "/tableList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tableList() throws Exception {
		
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		
		logger.info("tableList method.");

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, this.dataService.findTable());
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	
	
  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
  @ResponseBody
  public String create(FileUploadBean uploadItem, BindingResult result,String tableName)throws IOException{  
      ExtJSFormResult extjsFormResult = new ExtJSFormResult();   
      if (result.hasErrors()){  
          for(ObjectError error : result.getAllErrors()){  
              System.err.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());  
          }  
          extjsFormResult.setSuccess(false);  
          return extjsFormResult.toString();  
      }  
      if (!uploadItem.getFile().getOriginalFilename().substring(uploadItem.getFile().getOriginalFilename().length()-3,uploadItem.getFile().getOriginalFilename().length()).equals("xls")||tableName==null){  
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
 
      //将文件存到服务器
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
      //读取excel文件进入相应表中
      InputStream is = new FileInputStream(savePath+"/"+rndFilename+uploadItem.getFile().getOriginalFilename()); 
      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is); 
      DataJson dataJson=null;
      List<DataJson> list = new ArrayList<DataJson>();  
      for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {  
          HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);  
          if (hssfSheet == null) {
              continue;  
          }  
          // 循环行Row  
          for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {  
              HSSFRow hssfRow = hssfSheet.getRow(rowNum);  
              if (hssfRow == null) {  
                  continue;  
              }  
              dataJson = new DataJson();
              hssfRow.getCell(0).setCellType(Cell.CELL_TYPE_NUMERIC);
              hssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
              HSSFCell timeCell = hssfRow.getCell(0);  
              if (timeCell == null) {  
                  continue;  
              }  
              dataJson.setTime(timeCell.getDateCellValue());  
              HSSFCell dataCell = hssfRow.getCell(1);  
              if (dataCell == null) {  
                  continue;  
              }  
              dataJson.setData(dataCell.getStringCellValue());
              list.add(dataJson);
          }  
      }  
      dataService.addData(tableName,list);
      
      
      
      extjsFormResult.setSuccess(true);  
 
      return extjsFormResult.toString();
  }

}
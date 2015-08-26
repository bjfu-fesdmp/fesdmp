package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.Location;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.SystemLog;
import cn.bjfu.fesdmp.domain.sys.User;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.json.TreeJson;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.web.annotation.MethodRecordLog;
import cn.bjfu.fesdmp.web.jsonbean.DataSearch;
import cn.bjfu.fesdmp.web.jsonbean.ExtJSFormResult;
import cn.bjfu.fesdmp.web.jsonbean.FileUploadBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/dataDisplay")
public class DataDisplayManagerController extends BaseController {

	private static final String FILE_PATH = "/upload";
	private static final String TEMP_PATH = "resources/temp";
	private static final Logger logger = Logger
			.getLogger(DataDisplayManagerController.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private IDataService dataService;
	@Autowired
	private ILocationService locationService;
	@Autowired
	private IResourceGroupService resourceGroupService;
	@Autowired
	private IIndexResourceService indexResourceService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String fileUploadPage() {
		logger.info("listView method.");
		return "dataDisplay/dataDisplayView";
	}

	@RequestMapping(value = "/dataDisplayList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dataDisplayList(PageInfoBean pageInfo,
			String tableName) throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("dataDisplayList method.");
		logger.info(pageInfo);
		DataSearch dataSearch = null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
		if(tableName.length()>4){
			if(tableName.charAt(4)=='_'){
				String newTableName = tableName.substring(0, 4) + "_"
				+ tableName.substring(5);
				Pagination<DataJson> page = new Pagination<DataJson>();
				page.setPageSize(pageInfo.getLimit());
				page.setCurrentPage(pageInfo.getPage());

				IOrder order = new Order();
				//order.addOrderBy("time", "DESC");
				order.addOrderBy("station", "DESC");

				if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
					dataSearch = mapper.readValue(pageInfo.getSearchJson(),
							DataSearch.class);
				}

				logger.info(dataSearch);
				this.dataService.queryByCondtinWithOperationTime(newTableName,
						dataSearch, order, page, JoinMode.AND);
				List<DataJson> lll=this.dataService.queryByCondtinWithOperationTime(newTableName,
						dataSearch, order, page, JoinMode.AND);
				result.put(PAGE_COUNT, page.getTotalRecord());
				result.put(RESULT, page.getDatas());
		
			}
		}
	}	
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/unionDataDisplayList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unionDataDisplayList(PageInfoBean pageInfo,
			String tableName) throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("unionDataDisplayList method.");
		logger.info(pageInfo);
		DataSearch dataSearch = null;
		Map<String, Object> result = new HashMap<String, Object>();
	
		if(tableName!=null){
		if(this.dataService.checkIfHasTable(tableName)){
				Pagination<DataJson> page = new Pagination<DataJson>();
				page.setPageSize(pageInfo.getLimit());
				page.setCurrentPage(pageInfo.getPage());

				IOrder order = new Order();
				order.addOrderBy("time", "DESC");
				order.addOrderBy("station", "DESC");

				if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
					dataSearch = mapper.readValue(pageInfo.getSearchJson(),
							DataSearch.class);
				}

				logger.info(dataSearch);
				this.dataService.queryUnionByCondtinWithOperationTime(tableName,
						dataSearch, order, page, JoinMode.AND);
				result.put(PAGE_COUNT, page.getTotalRecord());
				result.put(RESULT, page.getDatas());
		
			
		}
	}	
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/tableList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tableList(String parentId) throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		logger.info("tableList method.");
		//所有表
		List<TableJson> tableList = this.dataService.findTable();
		// 所有的节点列表
		List<TreeJson> treeTimeList = new ArrayList();
		//所有年份节点
		List<TreeJson> treeOtherList = new ArrayList();
		//其他节点
		TreeJson firstYearTree = new TreeJson();
		firstYearTree.setId(Integer.parseInt(tableList.get(0).getName()
				.substring(0, 4)));
		firstYearTree.setParentId(0);
		firstYearTree.setLeaf(false);
		firstYearTree.setText(tableList.get(0).getName().substring(0, 4));
		treeTimeList.add(firstYearTree);
		for (int i = 1; i < tableList.size(); i++) {
			Integer temp = Integer.parseInt(tableList.get(i).getName()
					.substring(0, 4));
			boolean exist = false;
			for (int j = 0; j < treeTimeList.size(); j++) {
				if (treeTimeList.get(j).getId().equals(temp)) {
					exist = true;
					continue;
				}
			}
			if (exist == false) {
				TreeJson yearTree = new TreeJson();
				yearTree.setText(tableList.get(i).getName().substring(0, 4));
				yearTree.setParentId(0);
				yearTree.setLeaf(false);
				yearTree.setId(temp);
				treeTimeList.add(yearTree);
			}
		}
			//所有其他节点
		IOrder order = new Order();
		order.addOrderBy("id", "ASC");
		List<Location> locationList=this.locationService.queryAll(order);
		List<ResourceGroup> resourceGroupList=this.resourceGroupService.queryAll(order);
		for (int i = 0; i < treeTimeList.size(); i++) {
	//		if(treeList.get(i).getText().getBytes().length==4){
				Integer temp = treeTimeList.get(i).getId();
				for(int j=0;j<locationList.size();j++)
				{
					TreeJson tempTree = new TreeJson();
					tempTree.setText(locationList.get(j).getLocationName());
					Integer tempLocation = temp * 100000 + locationList.get(j).getId();
					tempTree.setId(tempLocation);
					tempTree.setParentId(temp);
					treeOtherList.add(tempTree);
				}
				for (int j = 0; j < resourceGroupList.size(); j++){
					TreeJson tempTree = new TreeJson();
					Integer tempLocation = temp * 100000 + this.locationService.findLocationIdByResourceGroupId(resourceGroupList.get(j).getId());
					List<IndexResource> indexResourceList=this.indexResourceService.queryByResourceGroupId(resourceGroupList.get(j).getId());			
					tempTree.setParentId(tempLocation);
					Integer tempResource = tempLocation * 100000 + j;
					tempTree.setId(tempResource);
					
					tempTree.setText(resourceGroupList.get(j).getGroupName());
					tempTree.setLeaf(true);
					for(int m=0;m<tableList.size();m++){
						for(int n=0;n<indexResourceList.size();n++){
							if(tableList.get(m).getName().substring(5).equalsIgnoreCase(indexResourceList.get(n).getIndexEnName())&&tableList.get(m).getName().substring(0,4).equals(String.valueOf(temp))){
								TreeJson newtempTree = new TreeJson();
								Integer tempIndexResource = tempResource * 100000 + n;
								newtempTree.setId(tempIndexResource);
								newtempTree.setParentId(tempResource);
								newtempTree.setText(tableList.get(m).getName());
								newtempTree.setLeaf(true);
								treeOtherList.add(newtempTree);
								if(tempTree.getLeaf()==true)
									tempTree.setLeaf(false);
							}
						}
					}
					treeOtherList.add(tempTree);
				}
	//		}
		}
		
		treeOtherList.addAll(treeTimeList);
		
		
		List<TreeJson> newtreeList = new ArrayList();
		for (int i = 0; i < treeOtherList.size(); i++) {
			if (treeOtherList.get(i).getParentId()
					.equals(Integer.parseInt(parentId)))
				newtreeList.add(treeOtherList.get(i));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, newtreeList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/unionTableList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unionTableList(String parentId) throws Exception {

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		logger.info("tableList method.");
		// 所有的节点列表
		List<TreeJson> treeOtherList = new ArrayList();

			//所有其他节点
		IOrder order = new Order();
		order.addOrderBy("id", "ASC");
		List<Location> locationList=this.locationService.queryAll(order);
		List<ResourceGroup> resourceGroupList=this.resourceGroupService.queryAll(order);

	//		if(treeList.get(i).getText().getBytes().length==4){
				for(int j=0;j<locationList.size();j++)
				{
					TreeJson tempTree = new TreeJson();
					tempTree.setText(locationList.get(j).getLocationName());
					tempTree.setParentId(0);
					Integer tempLocation = locationList.get(j).getId();
					tempTree.setId(tempLocation);
					treeOtherList.add(tempTree);
				}
				for (int j = 0; j < resourceGroupList.size(); j++){
					TreeJson tempTree = new TreeJson();
					Integer tempLocation = this.locationService.findLocationIdByResourceGroupId(resourceGroupList.get(j).getId());
					List<IndexResource> indexResourceList=this.indexResourceService.queryByResourceGroupId(resourceGroupList.get(j).getId());			
					tempTree.setParentId(tempLocation);
					Integer tempResource = tempLocation * 100000 + j;
					tempTree.setId(tempResource);
					
					tempTree.setText(resourceGroupList.get(j).getGroupName());
					tempTree.setLeaf(true);
						for(int n=0;n<indexResourceList.size();n++){
								TreeJson newtempTree = new TreeJson();
								Integer tempIndexResource = tempResource * 100000 + n;
								newtempTree.setId(tempIndexResource);
								newtempTree.setParentId(tempResource);
								newtempTree.setText(indexResourceList.get(n).getIndexEnName());
								newtempTree.setLeaf(true);
								treeOtherList.add(newtempTree);
								if(tempTree.getLeaf()==true)
									tempTree.setLeaf(false);
							
						}
					treeOtherList.add(tempTree);
				}
	//		}
		
		
		
		List<TreeJson> newtreeList = new ArrayList();
		for (int i = 0; i < treeOtherList.size(); i++) {
			if (treeOtherList.get(i).getParentId()
					.equals(Integer.parseInt(parentId)))
				newtreeList.add(treeOtherList.get(i));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, newtreeList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/uploadGroupFile", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据管理", bussinessType="DATA_OPERATE", operateType = "ADD", desc="批量上传数据")
	@ResponseBody
	public String uploadGroupFile(MultipartHttpServletRequest request,FileUploadBean uploadItem,
			BindingResult result, String tableName) throws IOException {
		logger.info("uploadGroupFile method.");
		 List<MultipartFile> multipartFiles = request.getFiles("file");
		 List<CommonsMultipartFile> files=new ArrayList();
		 for(int i=0;i<multipartFiles.size();i++){
			 CommonsMultipartFile file= (CommonsMultipartFile)multipartFiles.get(i);
			 files.add(file);
		 }
		 ExtJSFormResult extjsFormResult = new ExtJSFormResult();
		if(tableName==null){
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();
		}
		if(tableName.length()<=4){
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();
		}
		if(tableName.charAt(4)!='_'){
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();	
		}
		String newTableName = tableName.substring(0, 4) + "_"+ tableName.substring(5);
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "+ error.getDefaultMessage());
			}
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();
		}
		for(int i=0;i<files.size();i++){		
			if ((!files.get(i).getOriginalFilename().substring(files.get(i).getOriginalFilename().length() - 3,
				files.get(i).getOriginalFilename().length()).equals("xls") && !files.get(i)
				.getOriginalFilename().substring(files.get(i).getOriginalFilename().length() - 3,
				files.get(i).getOriginalFilename().length()).equals("txt"))) {
				for (ObjectError error : result.getAllErrors()) {
					System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
				}
				extjsFormResult.setSuccess(false);
				return extjsFormResult.toString();
			}
		}
			// Some type of file processing...
			System.err.println("-------------------------------------------");
			for(int i=0;i<files.size();i++){
			System.err.println("Test upload: "
					+ files.get(i).getOriginalFilename());
			}
			System.err.println("-------------------------------------------");
			for(int i=0;i<files.size();i++){	
			// 将文件存到服务器
			String savePath = request.getSession().getServletContext()
					.getRealPath(FILE_PATH);
			System.out.println(request.getSession().getServletContext()
					.getRealPath(FILE_PATH));
			java.io.File folder = new java.io.File(request.getSession()
					.getServletContext().getRealPath(FILE_PATH));
			if (!folder.exists()) {
				folder.mkdirs();
			}
			System.out.println("文件保存路径：" + savePath);
			String rndFilename = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-"))
					.format(new Date());
			java.io.File file = new java.io.File(savePath + "/" + rndFilename
					+ files.get(i).getOriginalFilename());
			try {
				files.get(i).transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 读取excel文件进入相应表中
			if (files.get(i).getOriginalFilename().substring(
					files.get(i).getOriginalFilename().length() - 3,
					files.get(i).getOriginalFilename().length())
					.equals("xls")) {
				InputStream is = new FileInputStream(savePath + "/" + rndFilename
						+ files.get(i).getOriginalFilename());
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
				DataJson dataJson = null;
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
						hssfRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
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
						HSSFCell stationCell = hssfRow.getCell(2);
						if (stationCell == null) {
							continue;
						}
						dataJson.setStation(stationCell.getStringCellValue());
						list.add(dataJson);
					}
				}
				dataService.addData(newTableName, list);
			} else {
				BufferedReader input = new BufferedReader(new FileReader(savePath
						+ "/" + rndFilename
						+ files.get(i).getOriginalFilename()));
				String s = input.readLine();
				DataJson dataJson = null;
				List<DataJson> list = new ArrayList<DataJson>();
				while ((s = input.readLine()) != null) {
					try {
						String info[] = s.split("	");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy/MM/dd HH:mm:ss");
						Date date = sdf.parse(info[0]);
						dataJson = new DataJson();
						dataJson.setData(info[1]);
						dataJson.setStation(info[2]);
						dataJson.setTime(date);
						list.add(dataJson);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				dataService.addData(newTableName, list);
			}
			}
			
				extjsFormResult.setSuccess(true);
			
			return extjsFormResult.toString();
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据管理", bussinessType="DATA_OPERATE", operateType = "ADD", desc="上传数据")
	@ResponseBody
	public String create(HttpServletRequest request, FileUploadBean uploadItem,
			BindingResult result, String tableName) throws IOException {
		logger.info("uploadFile method.");
		ExtJSFormResult extjsFormResult = new ExtJSFormResult();
		if(tableName!=null){
			if(tableName.length()>4){
				if(tableName.charAt(4)=='_'){
		String newTableName = tableName.substring(0, 4) + "_"
				+ tableName.substring(5);
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();
		}
		if ((!uploadItem
				.getFile()
				.getOriginalFilename()
				.substring(
						uploadItem.getFile().getOriginalFilename().length() - 3,
						uploadItem.getFile().getOriginalFilename().length())
				.equals("xls") && !uploadItem
				.getFile()
				.getOriginalFilename()
				.substring(
						uploadItem.getFile().getOriginalFilename().length() - 3,
						uploadItem.getFile().getOriginalFilename().length())
				.equals("txt"))
				|| tableName == null) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			extjsFormResult.setSuccess(false);
			return extjsFormResult.toString();
		}
		// Some type of file processing...
		System.err.println("-------------------------------------------");
		System.err.println("Test upload: "
				+ uploadItem.getFile().getOriginalFilename());
		System.err.println("-------------------------------------------");

		// 将文件存到服务器
		String savePath = request.getSession().getServletContext()
				.getRealPath(FILE_PATH);
		System.out.println(request.getSession().getServletContext()
				.getRealPath(FILE_PATH));
		java.io.File folder = new java.io.File(request.getSession()
				.getServletContext().getRealPath(FILE_PATH));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		System.out.println("文件保存路径：" + savePath);
		String rndFilename = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-"))
				.format(new Date());
		java.io.File file = new java.io.File(savePath + "/" + rndFilename
				+ uploadItem.getFile().getOriginalFilename());
		try {
			uploadItem.getFile().transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 读取excel文件进入相应表中
		if (uploadItem
				.getFile()
				.getOriginalFilename()
				.substring(
						uploadItem.getFile().getOriginalFilename().length() - 3,
						uploadItem.getFile().getOriginalFilename().length())
				.equals("xls")) {
			InputStream is = new FileInputStream(savePath + "/" + rndFilename
					+ uploadItem.getFile().getOriginalFilename());
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			DataJson dataJson = null;
			List<DataJson> list = new ArrayList<DataJson>();
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					try{
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}
					
					if(hssfRow.getCell(0)!=null||hssfRow.getCell(1)!=null||hssfRow.getCell(2)!=null)
					{
					dataJson = new DataJson();
					hssfRow.getCell(0).setCellType(Cell.CELL_TYPE_NUMERIC);
					hssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					hssfRow.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
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
					HSSFCell stationCell = hssfRow.getCell(2);
					if (stationCell == null) {
						continue;
					}
					dataJson.setStation(stationCell.getStringCellValue());
					list.add(dataJson);
					}
				}
				catch(Exception e){
					
				}
				}
				
				
				}

			dataService.addData(newTableName, list);
		} else {
			BufferedReader input = new BufferedReader(new FileReader(savePath
					+ "/" + rndFilename
					+ uploadItem.getFile().getOriginalFilename()));
			String s = input.readLine();
			DataJson dataJson = null;
			List<DataJson> list = new ArrayList<DataJson>();
			while ((s = input.readLine()) != null) {
				try {
					String info[] = s.split("	");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					Date date = sdf.parse(info[0]);
					dataJson = new DataJson();
					dataJson.setData(info[1]);
					dataJson.setStation(info[2]);
					dataJson.setTime(date);
					list.add(dataJson);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dataService.addData(newTableName, list);
		}
			extjsFormResult.setSuccess(true);
		}
		else
			extjsFormResult.setSuccess(false);
		}
		else
			extjsFormResult.setSuccess(false);
		}
		else
			extjsFormResult.setSuccess(false);
		return extjsFormResult.toString();
	}

	@RequestMapping(value = "/modifyData", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据管理", bussinessType="DATA_OPERATE", operateType = "UPDATE", desc="修改数据")
	@ResponseBody
	public Map<String, Object> modifyData(String formData, String tableName)
			throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("modifyData method.");
		String newTableName = tableName.substring(0, 4) + "_"
				+ tableName.substring(5);
		DataJson data = mapper.readValue(formData, DataJson.class);
		this.dataService.modifyData(data, newTableName);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据管理", bussinessType="DATA_OPERATE", operateType = "DELETE", desc="删除数据")
	@ResponseBody
	public Map<String, Object> deleteData(String tableName, String ids)
			throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteData method.");
		String id[]=ids.split(",");
		this.dataService.deleteData(tableName, id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}
	@RequestMapping(value = "/downloadData", method = RequestMethod.POST)
	@ResponseBody
	public String downloadData(HttpServletRequest request,String ids, String tableName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("downloadData method.");
		String FinalPath =null;
		if(tableName!=null){
			if(tableName.length()>4){
				if(tableName.charAt(4)=='_'){
		String newTableName = tableName.substring(0, 4) + "_"
				+ tableName.substring(5);
		List<DataJson> list = new ArrayList<DataJson>();

		String id[] = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			DataJson dataJson = new DataJson();
			dataJson = this.dataService.findDataById(id[i], newTableName);
			list.add(dataJson);
		}
		String fileName = "outPutData.xls";
		String savePath = request.getSession().getServletContext()
				.getRealPath(TEMP_PATH);
		java.io.File folder = new java.io.File(savePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String rndFilename = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-"))
				.format(new Date());
		java.io.File excelFile = new java.io.File(savePath + "/" + rndFilename
				+ fileName);

		FileOutputStream fos = new FileOutputStream(excelFile);
		HSSFWorkbook wb = new HSSFWorkbook();// 创建工作薄
		HSSFSheet sheet = wb.createSheet();// 创建工作表
		wb.setSheetName(0, "sheet0");// 设置工作表名

		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("time");
		cell = row.createCell(1);
		cell.setCellValue("data");
		cell = row.createCell(2);
		cell.setCellValue("station");
		for (int i = 1; i < list.size() + 1; i++) {
			row = sheet.createRow(i);// 新增一行
			cell = row.createCell(0);// 新增一列
			cell.setCellType(Cell.CELL_TYPE_STRING);
			Date tempDate = list.get(i - 1).getTime();
			String stringDate = (1900 + tempDate.getYear()) + "-"
					+ (1 + tempDate.getMonth()) + "-" + tempDate.getDate()
					+ " " + tempDate.getHours() + ":" + tempDate.getMinutes()
					+ ":" + tempDate.getSeconds();
			cell.setCellValue(stringDate);// 向单元格中写入数据
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell = row.createCell(1);// 新增一列
			cell.setCellValue(list.get(i - 1).getData());
			cell = row.createCell(2);// 新增一列
			cell.setCellValue(list.get(i - 1).getStation());
		}
		wb.write(fos);
		fos.close();
		FinalPath = TEMP_PATH + "/" + excelFile.getName();
				}
			}
			}
		
		return FinalPath;
	}

	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
	@ResponseBody
	public String downloadTemplate() throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("downloadTemplate method.");
		String Path = "resources/extjs/Template/Template.zip";
		return Path;
	}
	@RequestMapping(value = "/deleteTable", method = RequestMethod.POST)
	@MethodRecordLog(moduleName="数据表管理", bussinessType="DATA_OPERATE", operateType = "DELETE", desc="删除数据表") 
	@ResponseBody
	public Map<String, Object> deleteTable(String tableName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("deleteTable method.");
		logger.info(tableName);
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
			this.dataService.deleteTable(tableName);
			result.put(SUCCESS, Boolean.TRUE);
			}
		else
			result.put(SUCCESS, Boolean.FALSE);
		return result;	
	}
	@RequestMapping(value = "/CheckIsTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CheckIsTable(String tableName) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("CheckIsTable method.");
		logger.info(tableName);
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
			if(tableName.length()>4){
				if(tableName.charAt(4)=='_'){
					Pattern pattern = Pattern.compile("[0-9]*");
					Matcher isNum = pattern.matcher(tableName.substring(0,4));
					 if( !isNum.matches() ){
						 result.put(SUCCESS, Boolean.FALSE);	
					 }
					 else result.put(SUCCESS, Boolean.TRUE);
				}
				else
					result.put(SUCCESS, Boolean.FALSE);	
			}
			else
				result.put(SUCCESS, Boolean.FALSE);	
		}
		else
			result.put(SUCCESS, Boolean.FALSE);	
			return result;	
	}
}
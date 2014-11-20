package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.ResourceGroup;
import cn.bjfu.fesdmp.domain.sys.UserGroup;
import cn.bjfu.fesdmp.frame.dao.IOrder;
import cn.bjfu.fesdmp.frame.dao.JoinMode;
import cn.bjfu.fesdmp.frame.dao.Order;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.TableJson;
import cn.bjfu.fesdmp.json.TreeJson;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.utils.PageInfoBean;
import cn.bjfu.fesdmp.utils.Pagination;
import cn.bjfu.fesdmp.web.BaseController;
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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
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
	private IResourceGroupService resourceGroupService;
	@Autowired
	private IIndexResourceService indexResourceService;
	@RequestMapping(value = "/listView", method = RequestMethod.GET)
	public String fileUploadPage() {
		logger.info("fileUploadPage method.");
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
				order.addOrderBy("time", "DESC");
				order.addOrderBy("id", "DESC");

				if (!StringUtils.isEmpty(pageInfo.getSearchJson())) {
					dataSearch = mapper.readValue(pageInfo.getSearchJson(),
							DataSearch.class);
				}

				logger.info(dataSearch);
				this.dataService.queryByCondtinWithOperationTime(newTableName,
						dataSearch, order, page, JoinMode.AND);
				result.put(PAGE_COUNT, page.getTotalRecord());
				result.put(RESULT, page.getDatas());
		
			}
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
		List<TreeJson> treeList = new ArrayList();
		//所有年份节点
		TreeJson firstYearTree = new TreeJson();
		firstYearTree.setId(Integer.parseInt(tableList.get(0).getName()
				.substring(0, 4)));
		firstYearTree.setParentId(0);
		firstYearTree.setLeaf(false);
		firstYearTree.setText(tableList.get(0).getName().substring(0, 4));
		treeList.add(firstYearTree);
		for (int i = 1; i < tableList.size(); i++) {
			Integer temp = Integer.parseInt(tableList.get(i).getName()
					.substring(0, 4));
			boolean exist = false;
			for (int j = 0; j < treeList.size(); j++) {
				if (treeList.get(j).getId().equals(temp)) {
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
				treeList.add(yearTree);
			}
		}
			//所有其他节点
		IOrder order = new Order();
		order.addOrderBy("id", "ASC");
		List<ResourceGroup> resourceGroupList=this.resourceGroupService.queryAll(order);
		for (int i = 0; i < treeList.size(); i++) {
			if(treeList.get(i).getText().getBytes().length==4){
				for (int j = 0; j < resourceGroupList.size(); j++){
					TreeJson tempTree = new TreeJson();
					List<IndexResource> indexResourceList=this.indexResourceService.queryByResourceGroupId(resourceGroupList.get(j).getId());
					Integer temp = treeList.get(i).getId();
					Integer temp0 = temp * 100000 + j;
					tempTree.setId(temp0);
					tempTree.setParentId(temp);
					tempTree.setText(resourceGroupList.get(j).getGroupName());
					tempTree.setLeaf(true);
					for(int m=0;m<tableList.size();m++){
						for(int n=0;n<indexResourceList.size();n++){
							if(tableList.get(m).getName().substring(5).equalsIgnoreCase(indexResourceList.get(n).getIndexEnName())&&tableList.get(m).getName().substring(0,4).equals(String.valueOf(temp))){
								TreeJson newtempTree = new TreeJson();
								Integer temp00 = temp0 * 100000 + n;
								newtempTree.setId(temp00);
								newtempTree.setParentId(temp0);
								newtempTree.setText(tableList.get(m).getName());
								newtempTree.setLeaf(true);
								treeList.add(newtempTree);
								if(tempTree.getLeaf()==true)
									tempTree.setLeaf(false);
							}
						}
					}
					treeList.add(tempTree);
				}
			}
		}
//		for (int i = 0; i < tableList.size(); i++) {
//			TreeJson tempTree = new TreeJson();
//			Integer temp = Integer.parseInt(tableList.get(i).getName()
//					.substring(0, 4));
//			Integer temp0 = temp * 100000 + i;
//			tempTree.setId(temp0);
//			tempTree.setParentId(temp);
//			tempTree.setText(String.valueOf(temp) + "年"
//					+ tableList.get(i).getName().substring(5));
//			tempTree.setLeaf(true);
//			treeList.add(tempTree);
//
//		}
		
		
		
		
		List<TreeJson> newtreeList = new ArrayList();
		for (int i = 0; i < treeList.size(); i++) {
			if (treeList.get(i).getParentId()
					.equals(Integer.parseInt(parentId)))
				newtreeList.add(treeList.get(i));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(RESULT, newtreeList);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
	}

	@RequestMapping(value = "/uploadGroupFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadGroupFile(MultipartHttpServletRequest request,FileUploadBean uploadItem,
			BindingResult result, String tableName) throws IOException {
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
	@ResponseBody
	public String create(HttpServletRequest request, FileUploadBean uploadItem,
			BindingResult result, String tableName) throws IOException {
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
		}
		wb.write(fos);
		fos.close();
		FinalPath = TEMP_PATH + "/" + excelFile.getName();
				}
			}
			}
		
		return FinalPath;
	}

	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
	@ResponseBody
	public String downloadTemplate() throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("downloadData method.");
		String Path = "resources/extjs/Template/Template.xls";
		return Path;
	}
}
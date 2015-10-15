package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.algorithm.GetDayAve;
import cn.bjfu.fesdmp.algorithm.HierarchicalClustering;
import cn.bjfu.fesdmp.algorithm.Kmeans;
import cn.bjfu.fesdmp.algorithm.Kmedoids;
import cn.bjfu.fesdmp.json.AddUserJson;
import cn.bjfu.fesdmp.json.AllTableJson;
import cn.bjfu.fesdmp.json.AveDataJson;
import cn.bjfu.fesdmp.json.ChartDataJson;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.HierarchicalClusteringJson;
import cn.bjfu.fesdmp.json.HierarchicalClusteringTableJson;
import cn.bjfu.fesdmp.json.StatisticsBean;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.web.BaseController;
import cn.bjfu.fesdmp.algorithm.GetMonthAve;
import cn.bjfu.fesdmp.constant.AppConstants;
import cn.bjfu.fesdmp.domain.sys.IndexResource;
import cn.bjfu.fesdmp.domain.sys.User;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dataClustering")
public class DataClusteringManagerController extends BaseController {


	private static final Logger logger = Logger
			.getLogger(DataClusteringManagerController.class);
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
		return "dataClustering/dataClusteringView";
	}
	
	@RequestMapping(value = "/unionListView", method = RequestMethod.GET)
	public String unionLileUploadPage() {
		return "unionDataClustering/unionDataClusteringView";
	}
	@RequestMapping(value = "/hierarchicalClusteringListView", method = RequestMethod.GET)
	public String hierarchicalClusteringUploadPage() {
		logger.info("hierarchicalClusteringListView method.");
		return "hierarchicalClustering/hierarchicalClusteringListView";
	}
	@RequestMapping(value = "/kmeans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> kmeans(HttpServletRequest request,String ids, String tableName,String num) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("kmeans method.");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
			if(tableName.length()==16){
				String newTableName = tableName.substring(0, 4) + "_"
						+ Integer.parseInt(tableName.substring(14));
		List<DataJson> list = new ArrayList<DataJson>();

		String id[] = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			DataJson dataJson = new DataJson();
			dataJson = this.dataService.findDataById(id[i], newTableName);
			list.add(dataJson);
		}


		num=num.trim();
		String str2="";
		if(num != null && !"".equals(num)){
		for(int i=0;i<num.length();i++){
		if(num.charAt(i)>=48 && num.charAt(i)<=57){
		str2+=num.charAt(i);
		}
		}

		}
		
		
        Kmeans kmeans = new Kmeans(list,Integer.parseInt(str2));  
        List<DataJson>[] results = kmeans.comput();  
        
		List<StatisticsBean> list1 = new ArrayList<StatisticsBean>();
		
		for(int i=0;i<results.length;i++)
		{
			StatisticsBean statBoyBean = new StatisticsBean();
			statBoyBean.setData(String.valueOf(results[i].size()));
			statBoyBean.setId(i+1);
			double k=0;
			for(int j=0;j<results[i].size();j++)
			{
			k=k+Double.parseDouble(results[i].get(j).getData());
			}
			k=k/results[i].size();
			
			BigDecimal bd=new BigDecimal(k);
			statBoyBean.setName("聚类中心为"+bd.setScale(2,bd.ROUND_HALF_UP)); 
			list1.add(statBoyBean);
		}
		result.put(RESULT, list1);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
				
			}
			}
		else
			result.put(SUCCESS, Boolean.FALSE);	
		
		return result;
	}
	@RequestMapping(value = "/unionKmeans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unionKmeans(HttpServletRequest request,String ids, String tableName,String num) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("unionKmeans method.");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tableName!=null){
			if(this.dataService.checkIfHasTable(tableName)){

		List<DataJson> list = new ArrayList<DataJson>();

		String id[] = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			DataJson dataJson = new DataJson();
			dataJson.setData(id[i]);
			list.add(dataJson);
		}


		num=num.trim();
		String str2="";
		if(num != null && !"".equals(num)){
		for(int i=0;i<num.length();i++){
		if(num.charAt(i)>=48 && num.charAt(i)<=57){
		str2+=num.charAt(i);
		}
		}

		}
		
		
        Kmeans kmeans = new Kmeans(list,Integer.parseInt(str2));  
        List<DataJson>[] results = kmeans.comput();  
        
		List<StatisticsBean> list1 = new ArrayList<StatisticsBean>();
		
		for(int i=0;i<results.length;i++)
		{
			StatisticsBean statBoyBean = new StatisticsBean();
			statBoyBean.setData(String.valueOf(results[i].size()));
			statBoyBean.setId(i+1);
			double k=0;
			for(int j=0;j<results[i].size();j++)
			{
			k=k+Double.parseDouble(results[i].get(j).getData());
			}
			k=k/results[i].size();
			
			BigDecimal bd=new BigDecimal(k);
			statBoyBean.setName("聚类中心为"+bd.setScale(2,bd.ROUND_HALF_UP)); 
			list1.add(statBoyBean);
		}
		result.put(RESULT, list1);
		result.put(SUCCESS, Boolean.TRUE);
		return result;
				
			}
			}
		else
			result.put(SUCCESS, Boolean.FALSE);	
		
		return result;
	}
	@RequestMapping(value = "/checkIfIsNumber", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIfIsNumber(String num) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("checkIfIsNumber method.");
		logger.info(num);
		Map<String, Object> result = new HashMap<String, Object>();
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(num);
		 if( !isNum.matches() ){
			 result.put(SUCCESS, Boolean.FALSE);	
			 return result;
		 }
			result.put(SUCCESS, Boolean.TRUE);	
			return result;	
	}
//返回日平均数据
@RequestMapping(value = "/dayAve", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> dayAve(HttpServletRequest request,String ids, String tableName) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("dayAve method.");
	Map<String, Object> result = new HashMap<String, Object>();
	List<ChartDataJson> list = new ArrayList<ChartDataJson>();
	List<AveDataJson> newList = new ArrayList<AveDataJson>();
	if(tableName!=null){
		if(tableName.length()==16){
			String newTableName = tableName.substring(0, 4) + "_"
					+ Integer.parseInt(tableName.substring(14));
	String id[] = ids.split(",");
	for (int i = 0; i < id.length; i++) {
		DataJson dataJson = new DataJson();
		ChartDataJson chartDataJson = new ChartDataJson();
		dataJson = this.dataService.findDataById(id[i], newTableName);
		chartDataJson.setTime(dataJson.getTime());
		chartDataJson.setData(Double.parseDouble(dataJson.getData()));
		list.add(chartDataJson);
	}
	GetDayAve dayAve=new GetDayAve(list);
	newList =dayAve.comput();
	
	
	}
	result.put(RESULT, newList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
			}
	else
		result.put(SUCCESS, Boolean.FALSE);	
	return result;
}

@RequestMapping(value = "/kmedoids", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> kmedoids(HttpServletRequest request,String ids, String tableName,String num) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("kmedoids method.");
	Map<String, Object> result = new HashMap<String, Object>();
	if(tableName!=null){
		if(tableName.length()==16){
			String newTableName = tableName.substring(0, 4) + "_"
					+ Integer.parseInt(tableName.substring(14));
	List<DataJson> list = new ArrayList<DataJson>();

	String id[] = ids.split(",");
	for (int i = 0; i < id.length; i++) {
		DataJson dataJson = new DataJson();
		dataJson = this.dataService.findDataById(id[i], newTableName);
		list.add(dataJson);
	}


	num=num.trim();
	String str2="";
	if(num != null && !"".equals(num)){
	for(int i=0;i<num.length();i++){
	if(num.charAt(i)>=48 && num.charAt(i)<=57){
	str2+=num.charAt(i);
	}
	}

	}
	
	
	Kmedoids kmedoids = new Kmedoids(list,Integer.parseInt(str2));  
    List<DataJson>[] results = kmedoids.comput();  
    
	List<StatisticsBean> list1 = new ArrayList<StatisticsBean>();
	
	for(int i=0;i<results.length;i++)
	{
		StatisticsBean statBoyBean = new StatisticsBean();
		statBoyBean.setData(String.valueOf(results[i].size()));
		statBoyBean.setId(i+1);
		double k=0;
		for(int j=0;j<results[i].size();j++)
		{
		k=k+Double.parseDouble(results[i].get(j).getData());
		}
		k=k/results[i].size();
		
		BigDecimal bd=new BigDecimal(k);
		statBoyBean.setName("聚类中心为"+bd.setScale(2,bd.ROUND_HALF_UP)); 
		list1.add(statBoyBean);
	}
	result.put(RESULT, list1);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
			
		}
		}
	else
		result.put(SUCCESS, Boolean.FALSE);	
	
	return result;
}
@RequestMapping(value = "/unionKmedoids", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> unionKmedoids(HttpServletRequest request,String ids, String tableName,String num) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("kmedoids method.");
	Map<String, Object> result = new HashMap<String, Object>();
	if(tableName!=null){
			if(this.dataService.checkIfHasTable(tableName)){
	List<DataJson> list = new ArrayList<DataJson>();

	String id[] = ids.split(",");
	for (int i = 0; i < id.length; i++) {
		DataJson dataJson = new DataJson();
		dataJson.setData(id[i]);;
		list.add(dataJson);
	}


	num=num.trim();
	String str2="";
	if(num != null && !"".equals(num)){
	for(int i=0;i<num.length();i++){
	if(num.charAt(i)>=48 && num.charAt(i)<=57){
	str2+=num.charAt(i);
	}
	}

	}
	
	
	Kmedoids kmedoids = new Kmedoids(list,Integer.parseInt(str2));  
    List<DataJson>[] results = kmedoids.comput();  
    
	List<StatisticsBean> list1 = new ArrayList<StatisticsBean>();
	
	for(int i=0;i<results.length;i++)
	{
		StatisticsBean statBoyBean = new StatisticsBean();
		statBoyBean.setData(String.valueOf(results[i].size()));
		statBoyBean.setId(i+1);
		double k=0;
		for(int j=0;j<results[i].size();j++)
		{
		k=k+Double.parseDouble(results[i].get(j).getData());
		}
		k=k/results[i].size();
		
		BigDecimal bd=new BigDecimal(k);
		statBoyBean.setName("聚类中心为"+bd.setScale(2,bd.ROUND_HALF_UP)); 
		list1.add(statBoyBean);
	}
	result.put(RESULT, list1);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
			}
		
		}
	else
		result.put(SUCCESS, Boolean.FALSE);	
	
	return result;
}
//返回月平均数据
@RequestMapping(value = "/monthAve", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> monthAve(HttpServletRequest request,String ids, String tableName) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("monthAve method.");
	Map<String, Object> result = new HashMap<String, Object>();
	List<ChartDataJson> list = new ArrayList<ChartDataJson>();
	List<AveDataJson> monthList = new ArrayList<AveDataJson>();
	if(tableName!=null){
		if(tableName.length()==16){
			String newTableName = tableName.substring(0, 4) + "_"
					+ Integer.parseInt(tableName.substring(14));
	String id[] = ids.split(",");
	for (int i = 0; i < id.length; i++) {
		DataJson dataJson = new DataJson();
		ChartDataJson chartDataJson = new ChartDataJson();
		dataJson = this.dataService.findDataById(id[i], newTableName);
		chartDataJson.setTime(dataJson.getTime());
		chartDataJson.setData(Double.parseDouble(dataJson.getData()));
		list.add(chartDataJson);
	}

	GetMonthAve January=new GetMonthAve(list,1);
	monthList.add(January.comput());
	GetMonthAve Feburary=new GetMonthAve(list,2);
	monthList.add(Feburary.comput());
	GetMonthAve March=new GetMonthAve(list,3);
	monthList.add(March.comput());
	GetMonthAve April=new GetMonthAve(list,4);
	monthList.add(April.comput());
	GetMonthAve May=new GetMonthAve(list,5);
	monthList.add(May.comput());
	GetMonthAve June=new GetMonthAve(list,6);
	monthList.add(June.comput());
	GetMonthAve July=new GetMonthAve(list,7);
	monthList.add(July.comput());
	GetMonthAve August=new GetMonthAve(list,8);
	monthList.add(August.comput());
	GetMonthAve September=new GetMonthAve(list,9);
	monthList.add(September.comput());
	GetMonthAve October=new GetMonthAve(list,10);
	monthList.add(October.comput());
	GetMonthAve November=new GetMonthAve(list,11);
	monthList.add(November.comput());
	GetMonthAve December=new GetMonthAve(list,12);
	monthList.add(December.comput());
	
	}
	result.put(RESULT, monthList);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
			}
	else
		result.put(SUCCESS, Boolean.FALSE);	
	return result;
}
//返回日平均数据
@RequestMapping(value = "/tableSelect", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> tableSelect(HttpServletRequest request,String ids,String tableName) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("tableCombine method.");
	String id[]=ids.split(",");
	List<String> table=new ArrayList<String>();
	String year=tableName.substring(0, 4);
	for(int i=0;i<id.length;i++){
		String tempTableName=year+"_"+id[i];
		table.add(tempTableName);
	}
	Map<String, Object> result = new HashMap<String, Object>();

		result.put(RESULT, table);
		result.put(SUCCESS, Boolean.TRUE);	
	return result;
}
@RequestMapping(value = "/hierarchicalClusteringTableList", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> hierarchicalClusteringTableList(String allTable)
		throws Exception {

	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("hierarchicalClusteringTableList method.");

	List<HierarchicalClusteringTableJson> table=new ArrayList<HierarchicalClusteringTableJson>();

	String tables[]=allTable.split(",");
	for(int i=0;i<tables.length;i++){
		HierarchicalClusteringTableJson temp=new HierarchicalClusteringTableJson();
		temp.setId(tables[i]);
		temp.setTableName(this.indexResourceService.findByKey(Integer.parseInt(tables[i].substring(5))).getIndexName());
		table.add(temp);
	}

	Map<String, Object> result = new HashMap<String, Object>();
	result.put(RESULT, table);
	result.put(SUCCESS, Boolean.TRUE);
	return result;
}
@RequestMapping(value = "/hierarchicalClustering", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> hierarchicalClustering(HttpServletRequest request,String allTable, String searchJson) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("hierarchicalClustering method.");
	Map<String, Object> result = new HashMap<String, Object>();
	String tables[]=allTable.split(",");
	int hierarchicalClusteringCenter=0;
	HierarchicalClusteringJson hierarchicalClusteringJson = new HierarchicalClusteringJson();
	if (!StringUtils.isEmpty(searchJson)) {
		hierarchicalClusteringJson = mapper.readValue(searchJson,HierarchicalClusteringJson.class);
	}
	for(int i=0;i<tables.length;i++){
		if(tables[i].equals(hierarchicalClusteringJson.getHierarchicalClusteringCenterId())){
			hierarchicalClusteringCenter=i;
			break;
		}
	}
	DataJson dataJson[][]=new DataJson[tables.length][];
	for(int i=0;i<tables.length;i++){
		if(i!=hierarchicalClusteringCenter){
			DataJson temp[]=this.dataService.timeCoordination(hierarchicalClusteringJson,tables[i]);
			dataJson[i]=temp;
		}
		else
			dataJson[i]=this.dataService.findData(hierarchicalClusteringJson);
	}
	int Threshold=Integer.parseInt(hierarchicalClusteringJson.getThresHlod());//设定阈值
	
	HierarchicalClustering hierarchicalClustering=new HierarchicalClustering(dataJson,Threshold);
	
	
	String finalResult=hierarchicalClustering.comput();
	
	
	
	
	
	
	result.put(RESULT, finalResult);
	result.put(SUCCESS, Boolean.TRUE);
	

		//result.put(SUCCESS, Boolean.FALSE);	
	return result;
}	@RequestMapping(value = "/checkThresHlod", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> checkThresHlod(HttpServletRequest request,String thresHlod) throws Exception {
	mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	logger.info("checkThresHlod method.");
	User user=(User) request.getSession().getAttribute(AppConstants.SESSION_USER);
	Map<String, Object> result = new HashMap<String, Object>();
	boolean k=true;
	   for(int i=thresHlod.length();--i>=0;){
		      int chr=thresHlod.charAt(i);
		      if(chr<48 || chr>57){
		        k=false;
		        break;
		      }
		   }
	   if(k==true){
		   if(Integer.parseInt(thresHlod)<2)
			   k=false;
	   }
	   
	   		if(k==true)
	   			result.put(SUCCESS, Boolean.TRUE);	
	   		else
	   			result.put(SUCCESS, Boolean.FALSE);	
	
				return result;	
}
}
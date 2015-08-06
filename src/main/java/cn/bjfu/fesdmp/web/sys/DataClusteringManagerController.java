package cn.bjfu.fesdmp.web.sys;

import cn.bjfu.fesdmp.algorithm.Kmeans;
import cn.bjfu.fesdmp.json.DataJson;
import cn.bjfu.fesdmp.json.StatisticsBean;
import cn.bjfu.fesdmp.sys.service.IDataService;
import cn.bjfu.fesdmp.sys.service.IIndexResourceService;
import cn.bjfu.fesdmp.sys.service.ILocationService;
import cn.bjfu.fesdmp.sys.service.IResourceGroupService;
import cn.bjfu.fesdmp.web.BaseController;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	@RequestMapping(value = "/kmeans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> kmeans(HttpServletRequest request,String ids, String tableName,String num) throws Exception {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		logger.info("kmeans method.");
		Map<String, Object> result = new HashMap<String, Object>();
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
        
        
        
//        for (int i = 0; i < results.length; i++) {  
//            System.out.println("===========类别" + (i + 1) + "================");  
//            List<DataJson> list1 = results[i];  
//            for (DataJson p : list1) {  
//                System.out.println(p.getTime() + "--->" + p.getData());  
//            }  
//        }  

				}
			}
			}
		else
			result.put(SUCCESS, Boolean.FALSE);	
		
		return result;
	}
	
}
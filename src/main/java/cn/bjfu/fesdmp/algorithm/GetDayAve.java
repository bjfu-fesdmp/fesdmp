package cn.bjfu.fesdmp.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bjfu.fesdmp.json.AveDataJson;
import cn.bjfu.fesdmp.json.ChartDataJson;
import cn.bjfu.fesdmp.json.DataJson;

public class GetDayAve {
	private List<ChartDataJson> list;
	
	public GetDayAve() {
	}
	public GetDayAve(List<ChartDataJson> list) {
		this.list = list;
	}
	
	public List<AveDataJson> comput(){
		double allData=0;
		List<AveDataJson> ave=new ArrayList<AveDataJson>();
		for(int i=0;i<12;i++){
			for(int j=1;j<32;j++){
				AveDataJson dayAve=new AveDataJson();
				double dayNum=0;
				double aveData=0;
				for(int k=0;k<list.size();k++){
					if(list.get(k).getTime().getMonth()==i&&list.get(k).getTime().getDate()==j){
						aveData=aveData+list.get(k).getData();
						dayNum++;
					}
				}
				if(dayNum!=0){
					aveData=aveData/dayNum;
					dayAve.setData(aveData);
					dayAve.setTime((i+1)+"月"+j+"日");
					ave.add(dayAve);
				}
			}
		}
		
		
		
		
//		ave.setData((double) 0);
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).getTime().getMonth()+1==month){
//				k++;	
//				ave.setData(ave.getData()+list.get(i).getData());
//			}
//		}
//		if(k!=0){
//		BigDecimal bd=new BigDecimal(ave.getData()/k);
//		ave.setData(bd.setScale(2,bd.ROUND_HALF_UP).doubleValue());
//		}
//		else ave.setData((double) 0);
//		ave.setTime(month+"月");
		return ave;
		
		
		
		
		
	}
}

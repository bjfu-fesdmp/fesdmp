package cn.bjfu.fesdmp.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bjfu.fesdmp.json.AveDataJson;
import cn.bjfu.fesdmp.json.ChartDataJson;
import cn.bjfu.fesdmp.json.DataJson;

public class GetMonthAve {
	private List<ChartDataJson> list;
	private int month =0;
	
	public GetMonthAve() {
	}
	public GetMonthAve(List<ChartDataJson> list,int month) {
		this.list = list;
		this.month = month;
	}
	
	public AveDataJson comput(){
		double k=0;
		AveDataJson ave=new AveDataJson(); 
		ave.setData((double) 0);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getTime().getMonth()+1==month){
				k++;	
				ave.setData(ave.getData()+list.get(i).getData());
			}
		}
		if(k!=0){
		BigDecimal bd=new BigDecimal(ave.getData()/k);
		ave.setData(bd.setScale(2,bd.ROUND_HALF_UP).doubleValue());
		}
		else ave.setData((double) 0);
		ave.setTime(month+"月");
		return ave;
		
		
		
		
		
	}
}

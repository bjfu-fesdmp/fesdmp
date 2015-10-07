package cn.bjfu.fesdmp.algorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import cn.bjfu.fesdmp.json.AveDataJson;
import cn.bjfu.fesdmp.json.DataJson;



public class HierarchicalClustering {
	private int threshold;//阈值
	private DataJson dataJson[][];
	public float finalAveDis=3.4e38f;
	public int finalNodeNum=0;
	public HierarchicalClustering() {
	}
	public HierarchicalClustering(DataJson dataJson[][],int threshold) {
		this.threshold = threshold;
		this.dataJson=dataJson;
	}
	public static void spell(Alert alert[],int k,int Treenum){					//将警报名以第k个为起始按照顺序拼起来
		for(int i=0;i<alert.length;i++){															
			alert[i].alertName=alert[i].getElement(k+1).data;
			int o=0;
			while(o<Treenum){
				if(o!=k+1){
					alert[i].alertName=alert[i].alertName+alert[i].getElement(o).data;
				}
				o++;
			}
		}
	}
////////////////////////////////////////////快速排序//////////////////////////////////
	public static void nonRecrutQuickSort(Alert a[]) {
		if (a == null || a.length <= 0)
			return;
		Stack<Integer> index = new Stack<Integer>();
		int start = 0;
		int end = a.length - 1;

		int pivotPos;

		index.push(start);
		index.push(end);

		while (!index.isEmpty()) {
			end = index.pop();
			start = index.pop();

			pivotPos = partition(a, start, end);
			if (start < pivotPos - 1) {
				index.push(start);
				index.push(pivotPos - 1);
			}
			if (end > pivotPos + 1) {
				index.push(pivotPos + 1);
				index.push(end);
			}
		}
	}

	public static int partition(Alert[] a, int start, int end) {// 分块方法，在数组a中，对下标从start到end的数列进行划分
		Alert pivot = a[start]; // 把比pivot(初始的pivot=a[start]小的数移动到pivot的左边
		while (start < end) { // 把比pivot大的数移动到pivot的右边
			while (start < end && a[end].isBigger(pivot))
				end--;
			a[start] = a[end];
			while (start < end && !a[start].isBigger(pivot))
				start++;
			a[end] = a[start];
		}
		a[start] = pivot;
		return start;// 返回划分后的pivot的位置
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] combine(int a[], int b[]) {// 将a,b两个数组取交集
		int al = a.length;
		int bl = b.length;
		int aPointer = 0;
		int bPointer = 0;
		List<Integer> resultList = new ArrayList();
		while (aPointer < al && bPointer < bl) {
			if (a[aPointer] == b[bPointer]) {
				resultList.add(a[aPointer]);
				aPointer++;
				bPointer++;
			} else if (a[aPointer] > b[bPointer])
				bPointer++;
			else
				aPointer++;
		}
		int result[] = new int[resultList.size()];
		for (int i = 0; i < resultList.size(); i++)
			result[i] = resultList.get(i);
		return result;

	}
	
	public String comput(){
		 int Treenum=dataJson.length+1;//树的数目,+1是加上时间
		 int N=dataJson[0].length;//数据行数
			Long start = System.currentTimeMillis();//起始时间
			/*	 
			  获取数据行数
			  */
			Alert[] alert=new Alert[N]; //存放所有基本事件的数组
			for(int i=0;i<N;i++)
				alert[i]=new Alert(Treenum);
				alert[0]=new Alert(Treenum);
									//将dataJson中的时间数据存入alert中
				for(int j=0;j<N;j++)
				{
				alert[j].setElement(new Node(String.valueOf(dataJson[0][j].getTime().getTime())), 0);
				alert[j].setAnum(Treenum);
				alert[j].staticNum=j;							//每条警报的固定编号
				alert[j].setAlertNum(1);
				alert[j].setOrder(j, 0);
				}
			for(int i=0;i<N;i++)	//将dataJson中的其他数据存入alert中
			{	
				for(int j=1;j<Treenum;j++)
				{
				alert[i].setElement(new Node(dataJson[j-1][i].getData()),j);
				}
			}
			Node finalNode[]=new Node[Treenum];						//存储最终的结果
			Node Pointer[]=new Node[Treenum+1];						//所有树的指针
			Node luo=new Node("luo");								//用来判断抽象事件是否遍历结束
			Node yang=new Node("yang");
			Pointer[0]=luo;
			yang.father=luo;
			luo.son=yang;
			
			
			Date startDate=new Date(Long.parseLong(alert[0].getElement(0).data));	//构建第一棵树(时间)
			Date endDate=new Date(Long.parseLong(alert[N-1].getElement(0).data));
			String whichYear=String.valueOf(startDate.getYear()+1900);													//将所有基本事件按照第个节点树（时间）排序
			Node year=new Node(whichYear);//第一棵树分为5层，分别为年份；月份；天；小时与具体的时间
			List<Node> month=new ArrayList();
			List<Node> day=new ArrayList();
			List<Node> hour=new ArrayList();
			List<Node> concreteTime=new ArrayList<Node>();
			int concreteTimePointer=0;
			int hourPointer=0;
			int dayPointer=0;
			int monthPointer=0;
			concreteTime.add(concreteTimePointer,new Node(String.valueOf(startDate.getTime())));//第一棵树
			hour.add(hourPointer, new Node(String.valueOf(startDate.getHours())));		
			day.add(dayPointer, new Node(String.valueOf(startDate.getDate())));		
			month.add(monthPointer, new Node(String.valueOf(startDate.getMonth()+1)));
			concreteTime.get(concreteTimePointer).father=hour.get(hourPointer);
			hour.get(hourPointer).father=day.get(dayPointer);
			day.get(dayPointer).father=month.get(monthPointer);
			month.get(monthPointer).father=year;
			year.son=month.get(monthPointer);
			month.get(monthPointer).son=day.get(dayPointer);
			day.get(dayPointer).son=hour.get(hourPointer);
			hour.get(hourPointer).son=concreteTime.get(concreteTimePointer);
			concreteTimePointer++;
			hourPointer++;
			dayPointer++;
			monthPointer++;
			for(int i=1;i<Treenum;i++){
				Date lastDate=new Date(Long.parseLong(alert[i-1].getElement(0).data));
				Date currentDate=new Date(Long.parseLong(alert[i].getElement(0).data));
				concreteTime.add(concreteTimePointer, new Node(String.valueOf(currentDate.getTime())));
				if(currentDate.getMonth()!=lastDate.getMonth()){				//月份变了的情况
					month.add(monthPointer, new Node(String.valueOf(currentDate.getMonth()+1)));		
					day.add(dayPointer, new Node(String.valueOf(currentDate.getDate())));	
					hour.add(hourPointer, new Node(String.valueOf(currentDate.getHours())));
					concreteTime.get(concreteTimePointer).father=hour.get(hourPointer);
					hour.get(hourPointer).father=day.get(dayPointer);
					day.get(dayPointer).father=month.get(monthPointer);
					month.get(monthPointer).father=year;
					month.get(monthPointer).son=day.get(dayPointer);
					day.get(dayPointer).son=hour.get(hourPointer);
					hour.get(hourPointer).son=concreteTime.get(concreteTimePointer);
					month.get(monthPointer-1).brother=month.get(monthPointer);
					concreteTimePointer++;
					hourPointer++;
					dayPointer++;
					monthPointer++;
				}else if(currentDate.getDate()!=lastDate.getDate()){			//月份没变日期变了的情况
					day.add(dayPointer, new Node(String.valueOf(currentDate.getDate())));	
					hour.add(hourPointer, new Node(String.valueOf(currentDate.getHours())));
					concreteTime.get(concreteTimePointer).father=hour.get(hourPointer);
					hour.get(hourPointer).father=day.get(dayPointer);
					day.get(dayPointer).father=month.get(monthPointer-1);
					day.get(dayPointer).son=hour.get(hourPointer);
					hour.get(hourPointer).son=concreteTime.get(concreteTimePointer);
					day.get(dayPointer-1).brother=day.get(dayPointer);
					concreteTimePointer++;
					hourPointer++;
					dayPointer++;
				}else if(currentDate.getHours()!=lastDate.getHours()){			//月份日期没变小时变了的情况
					hour.add(hourPointer, new Node(String.valueOf(currentDate.getHours())));
					concreteTime.get(concreteTimePointer).father=hour.get(hourPointer);
					hour.get(hourPointer).father=day.get(dayPointer-1);
					hour.get(hourPointer).son=concreteTime.get(concreteTimePointer);
					hour.get(hourPointer-1).brother=hour.get(hourPointer);
					concreteTimePointer++;
					hourPointer++;
				}else{
					concreteTime.get(concreteTimePointer).father=hour.get(hourPointer-1);
					concreteTime.get(concreteTimePointer-1).brother=concreteTime.get(concreteTimePointer);
					concreteTimePointer++;
				}
			}
			

			
			
			
			
			
			
			
			for(int i=1;i<Treenum;i++){     						//构建其余树
				spell(alert,i,Treenum);								//将所有基本事件按照第个节点树（时间）排序
				nonRecrutQuickSort(alert);
				char head=(char)(97+i);
				
			
			
			
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		
		
		
		return "23";
		
	}
	
	
}

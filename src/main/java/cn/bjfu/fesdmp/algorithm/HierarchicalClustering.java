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
			Alert[] alert=new Alert[N]; //存放所有警报的数组
			for(int i=0;i<N;i++)
				alert[i]=new Alert(Treenum);
				alert[0]=new Alert(Treenum);
									//将dataJson中的时间数据存入alert中
				for(int j=0;j<Treenum;j++)
				{
				alert[0].setElement(new Node(String.valueOf(dataJson[0][j].getTime().getTime())), j);
				}
				alert[0].setAnum(Treenum);
				alert[0].staticNum=0;							//每条警报的固定编号
				alert[0].setAlertNum(1);
				alert[0].setOrder(0, 0);
			for(int i=1;i<N;i++)	//将dataJson中的其他数据存入alert中
			{	
				for(int j=0;j<Treenum;j++)
				{
				alert[i].setElement(new Node(dataJson[i-1][j].getData()), j);
				}
				alert[i].setAnum(Treenum);
				alert[i].staticNum=i;							//每条警报的固定编号
				alert[i].setAlertNum(1);
				alert[i].setOrder(i, 0);
			}
			Node finalNode[]=new Node[Treenum];						//存储最终的结果
			Node Pointer[]=new Node[Treenum+1];						//所有树的指针
			Node luo=new Node("luo");								//用来判断抽象事件是否遍历结束
			Node yang=new Node("yang");
			Pointer[0]=luo;
			yang.father=luo;
			luo.son=yang;
			Date startDate=new Date(Long.parseLong(alert[0].getElement(0).data));	//构建第一棵树(时间)
			String year=String.valueOf(startDate.getYear()+1900);													//将所有基本事件按照第个节点树（时间）排序
			
			  		
			

			
			
			
			
			
			
			
			for(int i=1;i<Treenum;i++){     						//构建其余树
				spell(alert,i,Treenum);								//将所有基本事件按照第个节点树（时间）排序
				nonRecrutQuickSort(alert);
				char head=(char)(97+i);
				
			
			
			
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		
		
		
		return "23";
		
	}
	
	
}

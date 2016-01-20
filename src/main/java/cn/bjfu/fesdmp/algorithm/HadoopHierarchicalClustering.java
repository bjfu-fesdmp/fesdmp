package cn.bjfu.fesdmp.algorithm;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cn.bjfu.fesdmp.algorithm.HierarchicalMapReduce;
import cn.bjfu.fesdmp.json.AveDataJson;
import cn.bjfu.fesdmp.json.DataJson;



public class HadoopHierarchicalClustering  {
	HierarchicalMapReduce mapreduce;
	private static int threshold;//阈值
	private int middleNumber;//中间层节点数
	private int leafNumber;//每个中间层的叶子数
	private static Node Pointer[];
	private static Alert alert[];
	private static int Treenum;
	private static int N;
	private static Node finalNode[];
	private DataJson dataJson[][];
	private String tableName[];
	public static float finalAveDis=3.4e38f;
	public static int finalNodeNum=0;
    static Configuration conf = new Configuration();
    static FileSystem hdfs;
    static {
        String path = "/home/ivysaur/hadoop/conf/";
        conf.addResource(new Path(path + "core-site.xml"));
        conf.addResource(new Path(path + "hdfs-site.xml"));
        conf.addResource(new Path(path + "mapred-site.xml"));
        try {
            hdfs = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //create a direction
    public void createDir(String dir) throws IOException {
        Path path = new Path(dir);
        hdfs.mkdirs(path);
        System.out.println("new dir \t" + conf.get("fs.default.name") + dir);
    }   
     
    //copy from local file to HDFS file
    public void copyFile(String localSrc, String hdfsDst) throws IOException{
        Path src = new Path(localSrc);      
        Path dst = new Path(hdfsDst);
        hdfs.copyFromLocalFile(src, dst);
         
        //list all the files in the current direction
        FileStatus files[] = hdfs.listStatus(dst);
        System.out.println("Upload to \t" + conf.get("fs.default.name") + hdfsDst);
        for (FileStatus file : files) {
            System.out.println(file.getPath());
        }
    }
    //create a new file
    public void createFile(String fileName, String fileContent) throws IOException {
        Path dst = new Path(fileName);
        byte[] bytes = fileContent.getBytes();
        FSDataOutputStream output = hdfs.create(dst);
        output.write(bytes);
        System.out.println("new file \t" + conf.get("fs.default.name") + fileName);
    }
     
    //list all files
    public void listFiles(String dirName) throws IOException {
        Path f = new Path(dirName);
        FileStatus[] status = hdfs.listStatus(f);
        System.out.println(dirName + " has all files:");
        for (int i = 0; i< status.length; i++) {
            System.out.println(status[i].getPath().toString());
        }
    }
 
    //judge a file existed? and delete it!
    public void deleteFile(String fileName) throws IOException {
        Path f = new Path(fileName);
        boolean isExists = hdfs.exists(f);
        if (isExists) { //if exists, delete
            boolean isDel = hdfs.delete(f,true);
            System.out.println(fileName + "  delete? \t" + isDel);
        } else {
            System.out.println(fileName + "  exist? \t" + isExists);
        }
    }
	


	public HadoopHierarchicalClustering() {
		
	}
	public HadoopHierarchicalClustering(DataJson dataJson[][],int threshold,String tableName[],int middleNumber,int leafNumber) {
		this.threshold = threshold;
		this.dataJson=dataJson;
		this.tableName=tableName;
		this.middleNumber=middleNumber;
		this.leafNumber=leafNumber;
	}
	//将警报名以第k个为起始按照顺序拼起来
	public static void spell(Alert alert[],int k,int Treenum){				
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
	// 分块方法，在数组a中，对下标从start到end的数列进行划分
	public static int partition(Alert[] a, int start, int end) {
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
	
	public String comput() throws Exception{
		HadoopHierarchicalClustering ofs = new HadoopHierarchicalClustering();
		
		
		
		 Treenum=dataJson.length+1;//树的数目,+1是加上时间
		 
			Long start = System.currentTimeMillis();//起始时间
			double allMin[]=new double[dataJson.length];		//求出每个每种数据的最值
			double allMax[]=new double[dataJson.length];
			for(int i=0;i<allMin.length;i++){
				allMin[i]=1000000000;
				allMax[i]=-1000000000;
			}
			for(int i=0;i<dataJson[0].length;i++){
				for(int j=0;j<dataJson.length;j++){
					if(Double.parseDouble(dataJson[j][i].getData())<allMin[j])
						allMin[j]=Double.parseDouble(dataJson[j][i].getData());
					if(Double.parseDouble(dataJson[j][i].getData())>allMax[j])
						allMax[j]=Double.parseDouble(dataJson[j][i].getData());
				}
			}
																//将dataJson中的数据进行预处理、分类统计值
			double limit[][]=new double[Treenum-1][middleNumber*leafNumber+1];//每棵树叶子节点的范围组成值
			String stringLimit[][]=new String[Treenum-1][middleNumber*leafNumber+1];//每棵树叶子节点的范围组成(字符串，补0)     

			for(int i=0;i<Treenum-1;i++){
				limit[i][0]=allMin[i];
				double k=(allMax[i]-allMin[i])/(middleNumber*leafNumber);//叶子节点之间的差距
				for(int j=1;j<middleNumber*leafNumber;j++){
					limit[i][j]=limit[i][j-1]+k;
				}
				limit[i][middleNumber*leafNumber]=allMax[i];
			}
			 for(int i=0;i<Treenum-1;i++){
				 for(int j=0;j<middleNumber*leafNumber+1;j++){
					 String m="";
					int n=String.valueOf((int)allMax[i]).length()-String.valueOf((int)limit[i][j]).length();
					if(n!=0){
						for(int z=0;z<n;z++)
						m=m+"0";
					}
					 m=m+String.valueOf(limit[i][j]);
					 stringLimit[i][j]=m;
				 }
			 }
			
			List<String>kkkk=new ArrayList(); 
			List<String>kkkk2=new ArrayList();
			for(int i=0;i<dataJson[0].length;i++){
				String tempK=(dataJson[0][i].getTime().getYear()+1900)+"-"+(dataJson[0][i].getTime().getMonth()+1)+"-";
				if(dataJson[0][i].getTime().getDate()<10)
					tempK=tempK+"0"+(dataJson[0][i].getTime().getDate())+",";
				else
					tempK=tempK+(dataJson[0][i].getTime().getDate())+",";
				
				for(int j=0;j<dataJson.length;j++){
					for(int k=1;k<middleNumber*leafNumber+1;k++){
						if(Double.parseDouble(dataJson[j][i].getData())<=limit[j][k]){
							String m="";
							String p="";
							int n=String.valueOf((int)allMax[j]).length()-String.valueOf((int)limit[j][k-1]).length();
							int o=String.valueOf((int)allMax[j]).length()-String.valueOf((int)limit[j][k]).length();
							if(n!=0){
								for(int z=0;z<n;z++)
								m=m+"0";
							}
							if(o!=0){
								for(int z=0;z<o;z++)
								p=p+"0";
							}
							m=m+limit[j][k-1]+"-"+p+limit[j][k]+",";
							tempK=tempK+m;
							break;
						}
						
					}
				}
				kkkk.add(tempK);
			}
			Collections.sort(kkkk);
			int z=1;
			int totle=0;
			for(int i=0;i<kkkk.size()-1;i++){
				if(kkkk.get(i).equals(kkkk.get(i+1))){
					z++;
				}
				else{
					System.out.println(kkkk.get(i)+z);
					kkkk2.add(kkkk.get(i)+z);
					totle=totle+z;
					z=1;
				}
				
			}
			if(!kkkk.get(kkkk.size()-2).equals(kkkk.get(kkkk.size()-1))){
				kkkk2.add(kkkk.get(kkkk.size()-1)+1);
				totle=totle+1;
			}
			                                                            //kkkk2为最终整理好的基本事件集
			N=kkkk2.size();//数据行数
			
			alert=new Alert[N]; //存放所有基本事件的数组
			for(int i=0;i<N;i++)
				alert[i]=new Alert(Treenum);
				alert[0]=new Alert(Treenum);
									//将dataJson中的时间数据存入alert中
				
				for(int j=0;j<N;j++)
				{
				String info[] = kkkk2.get(j).split(",");
				for(int i=0;i<Treenum;i++)
				{
				alert[j].setElement(new Node(info[i]),i);
				}
				alert[j].setAnum(Treenum);
				alert[j].staticNum=j;							//每条警报的固定编号
				alert[j].setAlertNum(Integer.parseInt(info[Treenum]));
				alert[j].setOrder(j, 0);
				}
			finalNode=new Node[Treenum];						//存储最终的结果
			Pointer=new Node[Treenum+1];						//所有树的指针
			Node FileCreatePointer[]=new Node[Treenum+1];			//所有树的指针
			Node luo=new Node("luo");								//用来判断抽象事件是否遍历结束
			Node yang=new Node("yang");
			Pointer[0]=luo;
			FileCreatePointer[0]=luo;
			yang.father=luo;
			luo.son=yang;
													//构建第一棵树(时间)
			String fistTime[]=alert[0].getElement(0).data.split("-");	
			Node year=new Node(fistTime[0]);//第一棵树分为3层，分别为年份；月份；天
			List<Node> month=new ArrayList();
			List<Node> day=new ArrayList();
			int dayPointer=0;
			int monthPointer=0;
			month.add(monthPointer,new Node(fistTime[0]+"-"+fistTime[1]));
			day.add(dayPointer,new Node(fistTime[0]+"-"+fistTime[1]+"-"+fistTime[2]));
			month.get(monthPointer).father=year;
			day.get(dayPointer).father=month.get(monthPointer);
			year.son=month.get(monthPointer);
			month.get(monthPointer).son=day.get(dayPointer);
			monthPointer++;
			dayPointer++;
			for(int i=1;i<Treenum;i++){							//将所有基本事件按照第个节点树（时间）排序
				if(!alert[i-1].getElement(0).data.split("-")[1].equals(alert[i].getElement(0).data.split("-")[1])){				//月份变了的情况
					month.add(monthPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")[1]));		
					day.add(dayPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")[1]+"-"+alert[i].getElement(0).data.split("-")[2]));	
					day.get(dayPointer).father=month.get(monthPointer);
					month.get(monthPointer).father=year;
					month.get(monthPointer).son=day.get(dayPointer);
					month.get(monthPointer-1).brother=month.get(monthPointer);
					dayPointer++;
					monthPointer++;
				}else if(!alert[i-1].getElement(0).data.split("-")[2].equals(alert[i].getElement(0).data.split("-")[2]))
					{			                                 //月份没变日期变了的情况
					day.add(dayPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")[1]+"-"+alert[i].getElement(0).data.split("-")[2]));	
					day.get(dayPointer).father=month.get(monthPointer-1);
					day.get(dayPointer-1).brother=day.get(dayPointer);
					dayPointer++;
				}
			}
			
			
			Pointer[1]=year;
			FileCreatePointer[1]=year;
			
			Node root[]=new Node[Treenum-1];
			Node middleTier[][]=new Node[Treenum-1][middleNumber];
			Node leafTier[][]=new Node[Treenum-1][middleNumber*leafNumber];
			int rootPointer=0;
			for(int i=1;i<Treenum;i++){     						//构建其余树
				spell(alert,i-1,Treenum);								//将所有基本事件按照第个节点树（时间）排序
				nonRecrutQuickSort(alert);
				int middleTierPointer=0;
				int leafTierPointer=0;
				root[rootPointer]=new Node(tableName[i-1]);
				middleTier[i-1][middleTierPointer]=new Node(stringLimit[i-1][0]+"-"+stringLimit[i-1][leafNumber]);
				root[rootPointer].son=middleTier[i-1][middleTierPointer];
				middleTier[i-1][middleTierPointer].father=root[rootPointer];
				for(int j=1;j<middleNumber;j++){
					leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(j-1)]+"-"+stringLimit[i-1][leafNumber*(j-1)+1]);
					middleTier[i-1][middleTierPointer].son=leafTier[i-1][leafTierPointer];
					leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
					leafTierPointer++;
					for(int x=1;x<leafNumber;x++){
						leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafTierPointer]+"-"+stringLimit[i-1][leafTierPointer+1]);
						leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
						leafTier[i-1][leafTierPointer-1].brother=leafTier[i-1][leafTierPointer];
						leafTierPointer++;
					}
					middleTierPointer++;
					middleTier[i-1][middleTierPointer]=new Node(stringLimit[i-1][j*leafNumber]+"-"+stringLimit[i-1][j*leafNumber+leafNumber]);
					middleTier[i-1][middleTierPointer].father=root[rootPointer];
					middleTier[i-1][middleTierPointer-1].brother=middleTier[i-1][middleTierPointer];
				}
				leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(middleNumber-1)]+"-"+stringLimit[i-1][leafNumber*(middleNumber-1)+1]);
				middleTier[i-1][middleTierPointer].son=leafTier[i-1][leafTierPointer];
				leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
				leafTierPointer++;
				for(int x=1;x<leafNumber;x++){
					leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(middleNumber-1)+x]+"-"+stringLimit[i-1][leafNumber*(middleNumber-1)+x+1]);
					leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
					leafTier[i-1][leafTierPointer-1].brother=leafTier[i-1][leafTierPointer];
					leafTierPointer++;
				}
				
				
				rootPointer++;
			}
			
			for(int i=0;i<root.length;i++){
				Pointer[i+2]=root[i];
				FileCreatePointer[i+2]=root[i];
			}
																			//开始层次聚类
			
			
			
			
			
			
			
			
	   		
//       		Node rePointer[]=new Node[Treenum];
//       		for(int i=0;i<Pointer.length-1;i++)
//       			rePointer[i]=Pointer[i+1].nextNode();
//       		
//       		while(rePointer[0].father!=null){
//       			List<Integer> coverNode=new ArrayList();
//				Node L=rePointer[0].leftLeaf();
//				Node R=rePointer[0].rightLeaf();
//				for(int i=0;i<N;i++){								//把第一棵树每个节点所包含的事件放入相应的节点中
//					if(alert[i].getElement(0).isBigger(L) && alert[i].getElement(0).isSmaller(R)){
//						coverNode.add(alert[i].staticNum);
//					}
//					else if(alert[i].getElement(0).isBigger(R))
//						break;
//				}
//   				int m=coverNode.size();
//   				int[] covernode=new int[m];
//				for(int i=0;i<m;i++){
//					covernode[i]=coverNode.get(i);
//				}
//				rePointer[0].coverNode=covernode;
//       			rePointer[0]=rePointer[0].nextNode();
//       		}
//       		
//    		for(int k=0;k<Treenum-1;k++){			//根据每棵树排序，同时把相应的树每个节点所包含的事件放入相应的节点中
//    			spell(alert,k,Treenum);
//    			nonRecrutQuickSort(alert);
//    			for(int i=0;i<N;i++)
//    				alert[i].setOrder(i, k+1);
//           		while(rePointer[k+1].father!=null){
//       			List<Integer> coverNode=new ArrayList();
//				Node L=rePointer[k+1].leftLeaf();
//				Node R=rePointer[k+1].rightLeaf();
//				for(int i=0;i<N;i++){
//					if(alert[i].getElement(k+1).isBigger(L) && alert[i].getElement(k+1).isSmaller(R)){
//						coverNode.add(alert[i].staticNum);
//					}
//					else if(alert[i].getElement(k+1).isBigger(R))
//						break;
//				}
//   				int m=coverNode.size();
//   				int[] covernode=new int[m];
//				for(int i=0;i<m;i++){
//					covernode[i]=coverNode.get(i);
//				}
//				
//				int minIndex=0;//将节点包含的事件数字顺序排列
//				int temp=0;
//				for(int i=0;i<covernode.length;i++){
//					minIndex=i;
//					for(int j=i+1;j<covernode.length;j++){
//						if(covernode[j]<covernode[minIndex])
//							minIndex=j;
//						}
//						if(minIndex!=i){
//					temp=covernode[i];
//					covernode[i]=covernode[minIndex];
//					covernode[minIndex]=temp;
//					}
//					
//				}
//				rePointer[k+1].coverNode=covernode;
//       			rePointer[k+1]=rePointer[k+1].nextNode();
//           		}
//    			
//    		}
//    		for(int i=0;i<a1.coverNode.length;i++)
//   		System.out.println(a1.coverNode[i]);
			for(int i=0;i<N;i++){								//将警报名按照原始顺序拼起来
				alert[i].alertName=alert[i].getElement(0).data;
				int o=1;
				while(o<Treenum){
					alert[i].alertName=alert[i].alertName+alert[i].getElement(o).data;
					o++;
				}
			}	
			nonRecrutQuickSort(alert);
    		//创建上传到hdfs的文件，即所有抽象事件
			File m=new File("/home/ivysaur/workspace2/temp/allTree");
			try{
				
				FileWriter fw=new FileWriter(m);
				BufferedWriter bw=new BufferedWriter(fw);
				
				while(FileCreatePointer[0].son!=null){
					for(int i=1;i<Treenum+1;i++){
						String hang=FileCreatePointer[i].data+",";
						bw.write(hang);
					}
					bw.write('\n');
					if (FileCreatePointer[Treenum].nextNode().father==null){	//开始循环

						int k=Treenum;
						while(FileCreatePointer[k].nextNode().father==null){
							FileCreatePointer[k]=FileCreatePointer[k].nextNode();
							k--;
						}
						FileCreatePointer[k]=FileCreatePointer[k].nextNode();
					}
					else FileCreatePointer[Treenum]=FileCreatePointer[Treenum].nextNode();

					
				}
				
				bw.flush();
				bw.close();
				fw.close();
			}catch(Exception o){}
			
        String src = "/home/ivysaur/workspace2/temp/allTree";
        String dir="/user/ivysaur/temp/temp";
       // ofs.createDir(dir);
        ofs.copyFile(src, dir);
        
        
        mapreduce=new HierarchicalMapReduce(threshold,Treenum,alert,tableName,stringLimit);
        String fineResult=mapreduce.comput();
        ofs.deleteFile("/user/ivysaur/temp/temp");
        ofs.deleteFile("/user/ivysaur/output");
			return fineResult;
			
			
			
			
			
			
			
    		
    ////////////////////////////开始运算	
   		
	
	
}
}

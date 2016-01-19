package cn.bjfu.fesdmp.algorithm;




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



















import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class HierarchicalMapReduce extends Configured implements Tool {
	
	
	HierarchicalMapReduce(int Threshold,int Treenum,Alert[] alert,String tableName[],String stringLimit[][]){
		HierarchicalMapReduce.Threshold=Threshold;
		HierarchicalMapReduce.Treenum=Treenum;
		HierarchicalMapReduce.alert=alert;
		HierarchicalMapReduce.tableName=tableName;
		HierarchicalMapReduce.stringLimit=stringLimit;
		HierarchicalMapReduce.N=alert.length;
	};
	
	HierarchicalMapReduce(){
	};
	public static int Threshold;//设定阈值
	public static int Treenum;//树的数目
	public static String tableName[];
	public static String stringLimit[][];
	public static int middleNumber=2;//树的数目
	public static int leafNumber=2;//树的数目
	public static int N=0;//数据行数
	public static float finalAveDis=3.4e38f;
	public static int finalNodeNum=0;
	public static Alert[] alert; //存放所有警报的数组
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
	
	
	//////////////////////////////////////////////快速排序////////////////////////////////////////////////////////////

	public static void nonRecrutQuickSort(Alert a[]){  
	    if(a==null||a.length<=0)return;  
	    Stack<Integer> index=new Stack<Integer>();  
	    int start=0;  
	    int end=a.length-1;  
	      
	    int pivotPos;  
	          
	    index.push(start);  
	    index.push(end);  
	          
	    while(!index.isEmpty()){  
	        end=index.pop();  
	        start=index.pop();  
	          
	        pivotPos=partition(a,start,end);  
	        if(start<pivotPos-1){  
	            index.push(start);  
	            index.push(pivotPos-1);  
	        }  
	        if(end>pivotPos+1){  
	            index.push(pivotPos+1);  
	            index.push(end);  
	        }  
	    }     
	}  
	  
	public static int partition(Alert[] a,int start,int end){//分块方法，在数组a中，对下标从start到end的数列进行划分  
	    Alert pivot=a[start];                     //把比pivot(初始的pivot=a[start]小的数移动到pivot的左边  
	    while(start<end){                       //把比pivot大的数移动到pivot的右边  
	        while(start<end&&a[end].isBigger(pivot)) end--;  
	        a[start]=a[end];  
	        while(start<end&&!a[start].isBigger(pivot)) start++;  
	        a[end]=a[start];              
	    }  
	    a[start]=pivot;  
	    return start;//返回划分后的pivot的位置    
	}  

	public static int[] combine(int a[],int b[]){//将a,b两个数组取交集
		int al=a.length;
		int bl=b.length;
		int aPointer=0;
		int bPointer=0;
		List<Integer> resultList=new ArrayList();
		while(aPointer<al&&bPointer<bl){
			if(a[aPointer]==b[bPointer]){
				resultList.add(a[aPointer]);
				aPointer++;
				bPointer++;
			}
			else if(a[aPointer]>b[bPointer])
				bPointer++;
			else 
				aPointer++;
		}
		int result[]=new int[resultList.size()];
		for(int i=0;i<resultList.size();i++)
			result[i]=resultList.get(i);
		return result;
		
	}

	static Node Pointer[];//所有树的指针
	static Node finalNode[];
	enum Counter
	{
		LINESKIP,
	}
	public static class Map extends Mapper<LongWritable,Text,Text,Text>
	{
		public void map(LongWritable key,Text value,Context context)throws IOException,InterruptedException
		{

			String line=value.toString();
			try
			{
				context.write(new Text(line), new Text(""));

			}catch(java.lang.ArrayIndexOutOfBoundsException e)
			{
				context.getCounter(Counter.LINESKIP).increment(1);
				return;
			}
			
			
			
		}
		
		
	}
	public static class Reduce extends Reducer<Text,Text,Text,Text>
	{
		public void reduce(Text key,Iterable<Text>values,Context context)throws IOException,InterruptedException
		{

			Node tree[]=new Node[Treenum];						//获取当前作为cover的节点
			for(int i=0;i<Treenum;i++)
				tree[i]=Pointer[i+1];
			String line[]=key.toString().split(",");
			for(int i=0;i<Treenum;i++){
				while(!tree[i].data.equals(line[i]))
				tree[i]=tree[i].nextNode();
			}	
    		int alertNum=0;
    		boolean jump=false;
    		float distanceSum=0;
    		int listNum=Treenum;
    		for(int i=0;i<Treenum;i++){					//需要进行合并的数组数
    			if (tree[i].father==null)
    				listNum--;
    		}
    		if(listNum==0){									//全部都在根节点的情况
    			for(int i=0;i<Treenum;i++)
    				finalNode[i]=tree[i];
    			for(int i=0;i<N;i++){
    				alertNum=alertNum+alert[i].alertNum;
    				for(int j=0;j<Treenum;j++){
    					distanceSum=distanceSum+alert[i].alertNum*tree[j].distance(alert[i].getElement(j).data);		
    				}		
    			}
    			if(distanceSum/alertNum<finalAveDis){
    			finalNodeNum=alertNum;
    			finalAveDis=distanceSum/alertNum;
    			}
    		}
    		else{
        		int list[][]=new int[listNum][];
        		int temp0=0;
        		for(int i=0;i<Treenum;i++){					//加入需要合并的数组
        			if (tree[i].father!=null){
        				list[temp0]=tree[i].coverNode;
            			temp0++;
        			}
        		}
        		
        		
        		int tempmin=list[0].length;						//找出最短数组，并把最短数组放在第一位，若包含警报总数小于阈值直接跳出
        		int minnum=0;
        		for(int i=1;i<list.length;i++){
        			if(list[i].length<tempmin){
        				tempmin=list[i].length;
        				minnum=i;
        			}
        		}
        		int tempo[]=list[minnum];
        		
        		int minIndex=0;									//将数组由小到大排序
        		int temp[];
        		for(int i=0;i<list.length;i++){
        			minIndex=i;
    				for(int j=i+1;j<list.length;j++){
    					if(list[j].length<list[minIndex].length)
    						minIndex=j;
    					}
    					if(minIndex!=i){
    						temp=list[i];
    						list[i]=list[minIndex];
    						list[minIndex]=temp;
    					}
    				tempo=combine(tempo,list[i]);				
    	    		int tempAlertNumNew=0;
    	    		for(int j=0;j<tempo.length;j++)
    	    			tempAlertNumNew=tempAlertNumNew+alert[tempo[j]].alertNum;
    					if(tempAlertNumNew<Threshold){
    						jump=true;
    						break;						
    					}	
        		}    	

        		if(jump==false){
        			int alertNumNew=0;
            		float distanceSumNew=0;

        			for(int i=0;i<tempo.length;i++){
        				alertNumNew=alertNumNew+alert[tempo[i]].alertNum;
            			for(int j=0;j<Treenum;j++){		
            				distanceSumNew=distanceSumNew+alert[tempo[i]].alertNum*tree[j].distance(alert[tempo[i]].getElement(j).data);
            			}
        			}
            		float distanceAve=distanceSumNew/alertNumNew;
            		if(distanceAve<finalAveDis){
            			finalAveDis=distanceAve;
            			finalNodeNum=alertNumNew;
            			for(int i=0;i<Treenum;i++){
            				finalNode[i]=tree[i];
            				}
            			}

        			}
        		
        	}
		}
		
	}
	
	public int run(String[] args)throws Exception
	{
		Configuration conf=getConf();
		
//        String path = "D:/ubuntu_de_wenjian/hadoop/conf/";
//        conf.addResource(new Path(path + "core-site.xml"));
//        conf.addResource(new Path(path + "hdfs-site.xml"));
//        conf.addResource(new Path(path + "mapred-site.xml"));
		Job job=new Job(conf,"cover");
		job.setJarByClass(HierarchicalMapReduce.class);
		
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job,new Path(args[1]));
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		//
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class); 
		job.setCombinerClass(Reduce.class);
		job.setInputFormatClass(TextInputFormat.class);
		//
		
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.waitForCompletion(true);
		
		return job.isSuccessful()?0:1;
	
	}
	
	
	public String comput() throws Exception
	{	
		Long start = System.currentTimeMillis();//起始时间
		Node FileCreatePointer[]=new Node[Treenum+1];			//所有树的指针
		Node luo=new Node("luo");								//用来判断抽象事件
		finalNode=new Node[Treenum];
		Node yang=new Node("yang");
		yang.father=luo;
		luo.son=yang;
		Pointer=new Node[Treenum+1];
		Pointer[0]=luo;
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
		for(int i=1;i<Treenum;i++){							//将所有基本事件按照第个节
			if(!alert[i-1].getElement(0).data.split("-")[1].equals(alert[i].getElement(0).data.split("-")[1])){	

			//月份变了的情况
				month.add(monthPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")

[1]));		
				day.add(dayPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")

[1]+"-"+alert[i].getElement(0).data.split("-")[2]));	
				day.get(dayPointer).father=month.get(monthPointer);
				month.get(monthPointer).father=year;
				month.get(monthPointer).son=day.get(dayPointer);
				month.get(monthPointer-1).brother=month.get(monthPointer);
				dayPointer++;
				monthPointer++;
			}else if(!alert[i-1].getElement(0).data.split("-")[2].equals(alert[i].getElement(0).data.split

("-")[2]))
				{			                                 //月份没变日期变了的情况
				day.add(dayPointer, new Node(fistTime[0]+"-"+alert[i].getElement(0).data.split("-")

[1]+"-"+alert[i].getElement(0).data.split("-")[2]));	
				day.get(dayPointer).father=month.get(monthPointer-1);
				day.get(dayPointer-1).brother=day.get(dayPointer);
				dayPointer++;
			}
		}
		
		
		Pointer[1]=year;
		
		Node root[]=new Node[Treenum-1];
		Node middleTier[][]=new Node[Treenum-1][middleNumber];
		Node leafTier[][]=new Node[Treenum-1][middleNumber*leafNumber];
		int rootPointer=0;
		for(int i=1;i<Treenum;i++){     						//构建其余树
			spell(alert,i-1,Treenum);								//将所有基
			nonRecrutQuickSort(alert);
			int middleTierPointer=0;
			int leafTierPointer=0;
			root[rootPointer]=new Node(tableName[i-1]);
			middleTier[i-1][middleTierPointer]=new Node(stringLimit[i-1][0]+"-"+stringLimit[i-1][2]);
			root[rootPointer].son=middleTier[i-1][middleTierPointer];
			middleTier[i-1][middleTierPointer].father=root[rootPointer];
			for(int j=1;j<middleNumber;j++){
				leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(j-1)]+"-"+stringLimit

[i-1][leafNumber*(j-1)+1]);
				middleTier[i-1][middleTierPointer].son=leafTier[i-1][leafTierPointer];
				leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
				leafTierPointer++;
				for(int x=1;x<leafNumber;x++){
					leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][x]+"-"+stringLimit[i-1][x

+1]);
					leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
					leafTier[i-1][leafTierPointer-1].brother=leafTier[i-1][leafTierPointer];
					leafTierPointer++;
				}
				middleTierPointer++;
				middleTier[i-1][middleTierPointer]=new Node(stringLimit[i-1][j*leafNumber]+"-"+stringLimit

[i-1][j*leafNumber+leafNumber]);
				middleTier[i-1][middleTierPointer].father=root[rootPointer];
				middleTier[i-1][middleTierPointer-1].brother=middleTier[i-1][middleTierPointer];
			}
			leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(middleNumber-

1)]+"-"+stringLimit[i-1][leafNumber*(middleNumber-1)+1]);
			middleTier[i-1][middleTierPointer].son=leafTier[i-1][leafTierPointer];
			leafTier[i-1][leafTierPointer].father=middleTier[i-1][middleTierPointer];
			leafTierPointer++;
			for(int x=1;x<leafNumber;x++){
				leafTier[i-1][leafTierPointer]=new Node(stringLimit[i-1][leafNumber*(middleNumber-

1)+x]+"-"+stringLimit[i-1][leafNumber*(middleNumber-1)+x+1]);
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
		
		
		

			
		Node rePointer[]=new Node[Treenum];
   		for(int i=0;i<Pointer.length-1;i++)
   			rePointer[i]=Pointer[i+1].nextNode();
   		
   		while(rePointer[0].father!=null){
   			List<Integer> coverNode=new ArrayList();
			Node L=rePointer[0].leftLeaf();
			Node R=rePointer[0].rightLeaf();
			for(int i=0;i<N;i++){								//把第一棵树每个节点所包含的事件放入相应的节点中
				if(alert[i].getElement(0).isBigger(L) && alert[i].getElement(0).isSmaller(R)){
					coverNode.add(alert[i].staticNum);
				}
				else if(alert[i].getElement(0).isBigger(R))
					break;
			}
				int m=coverNode.size();
				int[] covernode=new int[m];
			for(int i=0;i<m;i++){
				covernode[i]=coverNode.get(i);
			}
			rePointer[0].coverNode=covernode;
   			rePointer[0]=rePointer[0].nextNode();
   		}
   		
		for(int k=0;k<Treenum-1;k++){			//根据每棵树排序，同时把相应的树每个节点所包含的事件放入相应的节点中
			spell(alert,k,Treenum);
			nonRecrutQuickSort(alert);
			for(int i=0;i<N;i++)
				alert[i].setOrder(i, k+1);
       		while(rePointer[k+1].father!=null){
   			List<Integer> coverNode=new ArrayList();
			Node L=rePointer[k+1].leftLeaf();
			Node R=rePointer[k+1].rightLeaf();
			for(int i=0;i<N;i++){
				if(alert[i].getElement(k+1).isBigger(L) && alert[i].getElement(k+1).isSmaller(R)){
					coverNode.add(alert[i].staticNum);
				}
				else if(alert[i].getElement(k+1).isBigger(R))
					break;
			}
				int m=coverNode.size();
				int[] covernode=new int[m];
			for(int i=0;i<m;i++){
				covernode[i]=coverNode.get(i);
			}
			
			int minIndex=0;//将节点包含的事件数字顺序排列
			int temp=0;
			for(int i=0;i<covernode.length;i++){
				minIndex=i;
				for(int j=i+1;j<covernode.length;j++){
					if(covernode[j]<covernode[minIndex])
						minIndex=j;
					}
					if(minIndex!=i){
				temp=covernode[i];
				covernode[i]=covernode[minIndex];
				covernode[minIndex]=temp;
				}
				
			}
			rePointer[k+1].coverNode=covernode;
   			rePointer[k+1]=rePointer[k+1].nextNode();
       		}
			
		}
		for(int i=0;i<N;i++){								//将警报名按照原始顺序拼起来
			alert[i].alertName=alert[i].getElement(0).data;
			int o=1;
			while(o<Treenum){
				alert[i].alertName=alert[i].alertName+alert[i].getElement(o).data;
				o++;
			}
		}	
		nonRecrutQuickSort(alert);
	
	    Configuration conf = new Configuration();

		
	    String location[]=new String[]{"hdfs://master:9000/user/ivysaur/temp","hdfs://master:9000/user/ivysaur/output"};
		int res=ToolRunner.run(new Configuration(),new HierarchicalMapReduce(),location);
	   

	  
		Long end = System.currentTimeMillis();
		String finalReault="";
		finalReault=finalReault+"所求cover为	[";						//结果输出
		for(int i=0;i<Treenum-1;i++){
			finalReault=finalReault+finalNode[i].data+" , ";
			}
		finalReault=finalReault+finalNode[Treenum-1].data;
		finalReault=finalReault+"]，";
		finalReault=finalReault+"共包含"+finalNodeNum+"条基本事件，平均距离为"+finalAveDis/Treenum;
		finalReault=finalReault+"，";
		finalReault=finalReault+"所花时间"+(end-start)*0.001+"秒 , ";
		finalReault=finalReault+"注： cover中的每个数据分别代表[时间 , ";
		for(int i=0;i<Treenum-2;i++){
			finalReault=finalReault+tableName[i]+" , ";
			}
		finalReault=finalReault+tableName[Treenum-2];
			
		finalReault=finalReault+"]";
		
		return finalReault;
	}
}
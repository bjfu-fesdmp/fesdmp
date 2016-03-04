package cn.bjfu.fesdmp.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;








import cn.bjfu.fesdmp.json.DataJson;


public  class HadoopKmeans  extends Configured implements Tool {
	
	private static List<DataJson[]>[] results;
	/**
	 * 所有数据列表
	 */
	private static List<DataJson[]> datas=new ArrayList<DataJson[]>();


	/**
	 * 初始化列表
	 */
	private static List<DataJson[]> initdatas=new ArrayList<DataJson[]>();


	/**
	 * 分类数
	 */
	private static int k = 1;
	/**
	 * 表数
	 */
	private static int N = 0;
	/**
	 * 数据总数
	 */
	private static int allNum = 0;
	private static HadoopHierarchicalClustering ofs = new HadoopHierarchicalClustering();
	public HadoopKmeans() {

	}
	public HadoopKmeans(DataJson[][] dataJson, int k) {
		this.k = k;
		this.N=dataJson.length;
		this.allNum=dataJson[0].length;
		for(int i=0;i<dataJson[0].length;i++){
			DataJson temp[]=new DataJson[N];
			for(int j=0;j<N;j++){
				temp[j]=dataJson[j][i];
			}
			datas.add(temp);
		}
		results=new ArrayList[k];
		
		
		for(int i=0;i<k;i++){
			results[i]=new ArrayList();
		}
		for (int i = 0; i < k; i++) {
			initdatas.add(datas.get(i));
		}
	}
      
	
	/**
	 * 比较是否两个对象是否属性一致
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean IsPlayerEqual(DataJson[] p1, DataJson[] p2) {
		boolean m=true;
		for(int i=0;i<p1.length;i++){
			if ((int)Double.parseDouble(p1[i].getData())*100!= (int)Double.parseDouble(p2[i].getData())*100) {
				m=false;
				break;
			}
			
		}
		
		
			return m;
		

	}

	/**
	 * 得到新聚类中心对象
	 * 
	 * @param ps
	 * @return
	 */
	public DataJson[] findNewCenter(List<DataJson[]> ps) {
		try {
			DataJson[] t = new DataJson[N];
			if (ps == null || ps.size() == 0) {
				return t;
			}
			double sum[]=new double[N];
			for(int i=0;i<N;i++){
				t[i]=new DataJson();
			}
			
			
			for(int i=0;i<ps.size();i++){
				for(int j=0;j<N;j++){
					sum[j]=sum[j]+Double.parseDouble(ps.get(i)[j].getData());	
				}
			}
			double num=ps.size();
			for(int j=0;j<N;j++){
				sum[j]=sum[j]/num;
			}
			for(int j=0;j<N;j++){
				t[j].setData(String.valueOf(sum[j]));
			}
			
			return t;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	/**
	 * 得到最短距离，并返回最短距离索引
	 * 
	 * @param dists
	 * @return
	 */
	public static int computOrder(double[] dists) {
		double min = 0;
		int index = 0;
		for (int i = 0; i < dists.length - 1; i++) {
			double dist0 = dists[i];
			if (i == 0) {
				min = dist0;
				index = 0;
			}
			double dist1 = dists[i + 1];
			if (min > dist1) {
				min = dist1;
				index = i + 1;
			}
		}

		return index;
	}

	/**
	 * 计算距离（相似性） 采用欧几里得算法
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	public static double distance(DataJson p0[], DataJson p1[]) {
		double dis = 0;
		for(int i=0;i<p0.length;i++){
		Double field0Value=Double.parseDouble(p0[i].getData());
		Double field1Value=Double.parseDouble(p1[i].getData());
		dis += Math.pow(field0Value - field1Value, 2); 
		}
		return Math.sqrt(dis);

	}
	
	
	
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
			
			String line=key.toString();
			
				DataJson p[] = new DataJson[N];
				
				String lineSpilt[]=line.split(",");
				for(int i=0;i<N;i++){
					p[i]=new DataJson();
					p[i].setData(lineSpilt[i]);
					p[i].setId(i);
				}
				double[] dists = new double[k];
				for (int j = 0; j < initdatas.size(); j++) {
					DataJson[] initP = initdatas.get(j);
					/* 计算距离 */
					double dist = distance(initP, p);
					dists[j] = dist;
				}

				int dist_index = computOrder(dists);
				results[dist_index].add(p);
				
		}
		
	}
	
	
	
	public int run(String[] args)throws Exception
	{
		Configuration conf2=getConf();
		
//        String path = "D:/ubuntu_de_wenjian/hadoop/conf/";
//        conf.addResource(new Path(path + "core-site.xml"));
//        conf.addResource(new Path(path + "hdfs-site.xml"));
//        conf.addResource(new Path(path + "mapred-site.xml"));
		Job job=new Job(conf2,"kmeans");
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
	
	
	public List comput()  throws Exception{
		//把数据文件上传到hdfs
		File m=new File("/home/ivysaur/workspace2/temp/data");
		try{
			FileWriter fw=new FileWriter(m);
			BufferedWriter bw=new BufferedWriter(fw);
				for(int i=0;i<datas.size();i++){
					
					for(int j=0;j<datas.get(i).length;j++){
					bw.write(datas.get(i)[j].getData());
					bw.write(",");
					}
					
					
					bw.write('\n');
				}
				
				bw.flush();
				bw.close();
				fw.close();
		}catch(Exception o){
			
		}
        String src = "/home/ivysaur/workspace2/temp/data";
        String dir="/user/ivysaur/kmeansTemp/data";
       

        try {
			ofs.copyFile(src, dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		boolean centerchange = true;
		while (centerchange) {
			centerchange = false;
		    String location[]=new String[]{"hdfs://master:9000/user/ivysaur/kmeansTemp","hdfs://master:9000/user/ivysaur/output"};
			int res=ToolRunner.run(new Configuration(),new HadoopKmeans(),location);
			ofs.deleteFile("/user/ivysaur/output");
			


			for (int i = 0; i < k; i++) {
				DataJson[] player_new = findNewCenter(results[i]);
				DataJson[] player_old = initdatas.get(i);
				if (!IsPlayerEqual(player_new, player_old)) {
					centerchange = true;
					initdatas.set(i, player_new);
				}

			}

		}
		ofs.deleteFile("/user/ivysaur/kmeansTemp");
		List list = new ArrayList();
		list.add(initdatas);
		List<Integer> num = new ArrayList();
		for(int i=0;i<results.length;i++){
			num.add(results[i].size());
		}
		list.add(num);
		return list;
	}
	
	
	
	
}
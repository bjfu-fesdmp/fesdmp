package cn.bjfu.fesdmp.algorithm;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import cn.bjfu.fesdmp.json.DataJson;


public class Kmedoids {

	/**
	 * 所有数据列表
	 */
	private List<DataJson[]> datas=new ArrayList<DataJson[]>();


	/**
	 * 初始化列表
	 */
	private List<DataJson[]> initdatas=new ArrayList<DataJson[]>();


	/**
	 * 分类数
	 */
	private int k = 1;

	/**
	 * 表数
	 */
	private int N = 0;
	/**
	 * 数据总数
	 */
	private int allNum = 0;
	
	public Kmedoids() {

	}

	/**
	 * 初始化列表
	 * 
	 * @param list
	 * @param k
	 */
	public Kmedoids(DataJson[][] dataJson, int k) {
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

		for (int i = 0; i < k; i++) {
			initdatas.add(datas.get(i));
		}
	}

	public List comput() {
	
		List<DataJson[]>[] results=new ArrayList[k];

		
		boolean centerchange = true;
		while (centerchange) {
			centerchange = false;
			for(int i=0;i<k;i++){
				results[i]=new ArrayList();
				
			}
			for (int i = 0; i < datas.size(); i++) {
				DataJson[] p = datas.get(i);
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

			for (int i = 0; i < k; i++) {
				DataJson[] player_new = findNewCenter(results[i]);
				DataJson[] player_old = initdatas.get(i);
				if (!IsPlayerEqual(player_new, player_old)) {
					centerchange = true;
					initdatas.set(i, player_new);
				}

			}

		}
		List list = new ArrayList();
		list.add(initdatas);
		List<Integer> num = new ArrayList();
		for(int i=0;i<results.length;i++){
			num.add(results[i].size());
		}
		list.add(num);
		return list;
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
			t=ps.get(0);
			double ave=0;
			for(int i=1;i<ps.size();i++){
				double temp=0;
				for(int j=0;j<N;j++){
					double z=java.lang.Math.abs(Double.parseDouble(ps.get(i)[j].getData())-Double.parseDouble(ps.get(0)[j].getData()));
					temp=temp+z*z;
				}
				ave=ave+temp;
			}
			
			for(int i=1;i<ps.size();i++){
				double newave=0;
				for(int j=0;j<ps.size();j++){
					double temp=0;
					for(int m=0;m<N;m++){
					double z=java.lang.Math.abs(Double.parseDouble(ps.get(i)[m].getData())-Double.parseDouble(ps.get(j)[m].getData()));
					temp=temp+z*z;
					}
					newave=newave+temp;
				}
				if(newave<ave){
					ave=newave;
					t=ps.get(i);
				}
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
	public int computOrder(double[] dists) {
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
	public double distance(DataJson p0[], DataJson p1[]) {
		double dis = 0;
		for(int i=0;i<p0.length;i++){
		Double field0Value=Double.parseDouble(p0[i].getData());
		Double field1Value=Double.parseDouble(p1[i].getData());
		dis += Math.pow(field0Value - field1Value, 2); 
		}
		return Math.sqrt(dis);

	}
	


}


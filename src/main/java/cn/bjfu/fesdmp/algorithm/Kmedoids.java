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
	private List<DataJson> datas = new ArrayList<DataJson>();


	/**
	 * 初始化列表
	 */
	private List<DataJson> initdatas;


	/**
	 * 分类数
	 */
	private int k = 1;

	public Kmedoids() {

	}

	/**
	 * 初始化列表
	 * 
	 * @param list
	 * @param k
	 */
	public Kmedoids(List<DataJson> list, int k) {
		this.datas = list;
		this.k = k;
		DataJson t = list.get(0);

		initdatas = new ArrayList<DataJson>();
		for (int i = 0; i < k; i++) {
			initdatas.add(datas.get(i));
		}
	}

	public List<DataJson>[] comput() {
		List<DataJson>[] results = new ArrayList[k];

		boolean centerchange = true;
		while (centerchange) {
			centerchange = false;
			for (int i = 0; i < k; i++) {
				results[i] = new ArrayList<DataJson>();
			}
			for (int i = 0; i < datas.size(); i++) {
				DataJson p = datas.get(i);
				double[] dists = new double[k];
				for (int j = 0; j < initdatas.size(); j++) {
					DataJson initP = initdatas.get(j);
					/* 计算距离 */
					double dist = distance(initP, p);
					dists[j] = dist;
				}

				int dist_index = computOrder(dists);
				results[dist_index].add(p);
			}

			for (int i = 0; i < k; i++) {
				DataJson player_new = findNewCenter(results[i]);
				DataJson player_old = initdatas.get(i);
				if (!IsPlayerEqual(player_new, player_old)) {
					centerchange = true;
					initdatas.set(i, player_new);
				}

			}

		}

		return results;
	}

	/**
	 * 比较是否两个对象是否属性一致
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean IsPlayerEqual(DataJson p1, DataJson p2) {
		if (Double.parseDouble(p1.getData()) == Double.parseDouble(p2.getData())) {
			return true;
		}
		else
			return false;
		

	
		

	}

	/**
	 * 得到新聚类中心对象
	 * 
	 * @param ps
	 * @return
	 */
	public DataJson findNewCenter(List<DataJson> ps) {
		try {
			DataJson t = new DataJson();
			if (ps == null || ps.size() == 0) {
				return t;
			}
			t=ps.get(0);
			double ave=0;
			for(int i=1;i<ps.size();i++){
				ave=ave+java.lang.Math.abs(Double.parseDouble(ps.get(i).getData())-Double.parseDouble(ps.get(0).getData()));
			}
			
			for(int i=1;i<ps.size();i++){
				double newave=0;
				for(int j=0;j<ps.size();j++){
					newave=newave+java.lang.Math.abs(Double.parseDouble(ps.get(i).getData())-Double.parseDouble(ps.get(j).getData()));
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
	public double distance(DataJson p0, DataJson p1) {
		double dis = 0;
		Double field0Value=Double.parseDouble(p0.getData());
		Double field1Value=Double.parseDouble(p1.getData());
		dis += Math.pow(field0Value - field1Value, 2); 
		return Math.sqrt(dis);

	}
	


}


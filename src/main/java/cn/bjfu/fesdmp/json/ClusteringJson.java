package cn.bjfu.fesdmp.json;

import java.util.Date;

import javax.validation.constraints.Past;

import cn.bjfu.fesdmp.frame.CustomDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class ClusteringJson {

	private int number;

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	private String clusteringCenterId;
	/**
	 * @return the clusteringCenterId
	 */
	public String getClusteringCenterId() {
		return clusteringCenterId;
	}
	/**
	 * @param clusteringCenterId the clusteringCenterId to set
	 */
	public void setClusteringCenterId(
			String clusteringCenterId) {
		this.clusteringCenterId = clusteringCenterId;
	}



}
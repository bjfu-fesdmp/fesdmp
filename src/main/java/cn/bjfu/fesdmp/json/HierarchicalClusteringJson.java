package cn.bjfu.fesdmp.json;

import java.util.Date;

import javax.validation.constraints.Past;

import cn.bjfu.fesdmp.frame.CustomDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class HierarchicalClusteringJson {
	private String hierarchicalClusteringCenterId;
	/**
	 * @return the hierarchicalClusteringCenterId
	 */
	public String getHierarchicalClusteringCenterId() {
		return hierarchicalClusteringCenterId;
	}
	/**
	 * @param hierarchicalClusteringCenterId the hierarchicalClusteringCenterId to set
	 */
	public void setHierarchicalClusteringCenterId(
			String hierarchicalClusteringCenterId) {
		this.hierarchicalClusteringCenterId = hierarchicalClusteringCenterId;
	}
	/**
	 * @return the thresHlod
	 */
	public String getThresHlod() {
		return thresHlod;
	}
	/**
	 * @param thresHlod the thresHlod to set
	 */
	public void setThresHlod(String thresHlod) {
		this.thresHlod = thresHlod;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	private String thresHlod;
	@JsonSerialize(using = CustomDateSerializer.class)
	@Past
	private Date startTime;
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date endTime;

}
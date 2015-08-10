package cn.bjfu.fesdmp.json;

import java.util.Date;



public class ChartDataJson {
	private Integer id;
	private Date time;
	private Double data;
	public ChartDataJson() {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}
	

}
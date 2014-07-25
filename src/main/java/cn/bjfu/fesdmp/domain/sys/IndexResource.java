package cn.bjfu.fesdmp.domain.sys;  

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class IndexResource implements Serializable{

	private static final long serialVersionUID = 4563893285739204858L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer indexResourceId;
	@Column (nullable = false)
	private String indexName;
	@Column (nullable = false)
	private String indexEnName;
	@Column (nullable = false)
	private String indexUnit;
	@Column 
	private String indexMemo;
	@OneToOne
	@JoinColumn(name = "creater_id")
	private User creater;
	@Column(nullable = false)
	private Date createTime;
	@OneToOne
	@JoinColumn(name = "modifier_id")
	private User modifier;
	@Column(nullable = false)
	private Date modifyTime;
}

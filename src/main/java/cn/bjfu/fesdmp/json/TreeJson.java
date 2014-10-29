package cn.bjfu.fesdmp.json;

import java.util.ArrayList;
import java.util.List;


public class TreeJson {
	private Integer id;
	private Integer parentId;
	private String text;
	private boolean leaf;
	public TreeJson() {}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public Integer  getId() {
		return id;
	}
	public void setId(Integer  id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
//	public Boolean getDisabled() {
//		return disabled;
//	}
//
//	public void setDisabled(Boolean disabled) {
//		this.disabled = disabled;
//	}
//	public Boolean getChecked() {
//		return checked;
//	}
//
//	public void setChecked(Boolean checked) {
//		this.checked = checked;
//	}

}
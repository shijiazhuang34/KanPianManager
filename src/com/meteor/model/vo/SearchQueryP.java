package com.meteor.model.vo;

import java.util.Map;

public class SearchQueryP {
	private int nowpage,count;
	private String sortname,sorttype,sbtype;
	private Map parameters ;

	public String getSbtype() {
		return sbtype;
	}

	public void setSbtype(String sbtype) {
		this.sbtype = sbtype;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSorttype() {
		return sorttype;
	}

	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}

	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
}

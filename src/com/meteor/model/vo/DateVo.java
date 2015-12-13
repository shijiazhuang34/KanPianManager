package com.meteor.model.vo;

import java.io.Serializable;

public class DateVo implements Serializable{
	private String dateen,datecn;
	private String count;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDateen() {
		return dateen;
	}

	public void setDateen(String dateen) {
		this.dateen = dateen;
	}

	public String getDatecn() {
		return datecn;
	}

	public void setDatecn(String datecn) {
		this.datecn = datecn;
	}
	
}

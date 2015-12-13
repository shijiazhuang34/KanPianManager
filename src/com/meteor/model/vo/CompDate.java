/**
 * 
 */
package com.meteor.model.vo;

import org.jsoup.nodes.Element;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author justlikemaki
 *
 */
public class CompDate implements Comparable{
	private int index;
	private Element ele;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private String date;

	public Element getEle() {
		return ele;
	}

	public void setEle(Element ele) {
		this.ele = ele;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	/**
	 * @标题: CompDls.java 
	 * @版权: Copyright (c) 2014
	 * @公司: VETECH
	 * @作者：LF
	 * @时间：2014-8-16
	 * @版本：1.0
	 * @方法描述：
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		try {
			CompDate cp=(CompDate) o;
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d, YYYY", Locale.US);
			Date date1 = sdf.parse(this.date);
			Date date2 = sdf.parse(((CompDate) o).getDate());
			long reslong=date2.getTime()-date1.getTime();
			int res=new Long(reslong).intValue();
			return res;
		} catch (ParseException e) {
//			e.printStackTrace();
			return 0;
		}
	}


}

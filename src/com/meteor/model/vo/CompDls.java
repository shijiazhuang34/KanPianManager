/**
 * 
 */
package com.meteor.model.vo;

import org.jsoup.nodes.Element;

/**
 * @author justlikemaki
 *
 */
public class CompDls implements Comparable{
	private int index,dls;
	private Element ele;

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

	public int getDls() {
		return dls;
	}

	public void setDls(int dls) {
		this.dls = dls;
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
		CompDls cp=(CompDls) o;
		return cp.getDls()-this.dls;
	}

}

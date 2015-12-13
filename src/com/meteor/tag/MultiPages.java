package com.meteor.tag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.jfinal.kit.PropKit;
import org.apache.commons.lang.StringUtils;


public class MultiPages extends TagSupport {
	private String actionUrl,searchname,searchvalue;
	private int count,start,allcount,nowpage;

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getAllcount() {
		return allcount;
	}

	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}

	public int doStartTag() throws JspException {
		//String pageUrl = calculateURL();//页面路径
		int pagecount=(allcount+count-1)/count;//总页数
		start=(nowpage-1)*count;
		String searchstr="";
		if(StringUtils.isNotBlank(searchname)){
			searchstr="?"+searchname+"="+searchvalue;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("<div class='pagediv'>");
		if(nowpage<=0){
			nowpage=1;
		}
		if(!(nowpage>pagecount || nowpage==pagecount) || (nowpage==pagecount && pagecount>1)){
			int pageend=nowpage+4;
			int pagebg=nowpage-4;
			if(pageend>=pagecount){
				pageend=pagecount;
			}
			if(pagebg<=1){
				pagebg=1;
			}
			
			if(start>0){
				sb.append("<a class='prev pagelink' href='"+actionUrl+"/"+(nowpage-1)+searchstr+"'><<上一页</a>");
			}
			if(pagebg-1==1){
				sb.append("<a class='pagelink' href='"+actionUrl+"/1"+searchstr+"'>1</a>");
			}
			else if(pagebg>1){
				sb.append("<a class='pagelink' href='"+actionUrl+"/1"+searchstr+"'>1</a><a class='pagelink'>...</a>");
			}
			for(int i=pagebg;i<=pageend;i++){
				if(i==nowpage){
					sb.append("<a class='nowpage pagelink' href='"+actionUrl+"/"+i+searchstr+"'>"+i+"</a>");
				}else{
					sb.append("<a class='pagelink' href='"+actionUrl+"/"+i+searchstr+"'>"+i+"</a>");
				}
			}
			if(pageend+1==pagecount){
				sb.append("<a class='pagelink' href='"+actionUrl+"/"+pagecount+searchstr+"'>"+pagecount+"</a>");
			}
			else if(pageend<pagecount){
				sb.append("<a class='pagelink'>...</a><a class='pagelink' href='"+actionUrl+"/"+pagecount+searchstr+"'>"+pagecount+"</a>");
			}
			if((start+count)<allcount){
				sb.append("<a class='next pagelink' href='"+actionUrl+"/"+(nowpage+1)+searchstr+"'>下一页>></a>");
			}
		}
		else{
			if(nowpage!=pagecount){
				sb.append("没有数据！");
			}		
		}
		sb.append("</div>");
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		request.setAttribute("pagelist", sb.toString());

		return (EVAL_BODY_INCLUDE);		
	}

	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}

	public void release() {
		super.release();
	}

}

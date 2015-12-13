/**
 * 
 */
package com.meteor.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.meteor.kit.*;
import com.meteor.kit.http.MultitHttpClient;
import com.meteor.model.po.javsrc;
import com.meteor.model.vo.SearchQueryP;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * @author justlikemaki
 *
 */


public class HttpInterfaceAction extends Controller {
	private final Logger logger = LoggerFactory.getLogger(HttpInterfaceAction.class);

	public void getRight(){
		ServletContext sct=getRequest().getServletContext();
		int withdate=getParaToInt("withdate");
		Map res=PageKit.getparametersInterface(sct, withdate);

		String callback=getPara("callback");
		if(StringUtils.isNotBlank(callback)){
			renderText("jsonp(" + JsonKit.bean2JSON(res)+")");
		}else {
			renderText(JsonKit.bean2JSON(res));
		}
	}

	private String getuuid(Map res){
		res.put("status", -3);
		res.put("errmsg","没有权限的设备！");
		return "jsonp("+JsonKit.bean2JSON(res)+")";
	}

	public void getLeft() {
		Map res = new HashMap();
		try {
			String spstr=getPara("sp");
			SearchQueryP p= JsonKit.json2Bean(spstr,SearchQueryP.class);
			int count=0;
			if(p.getSbtype().equals("web")){
				count=PropKit.getInt("webpagesize");
			}else{
				String uuid=getPara("uuid");
				if(StringUtils.isBlank(uuid)){
					throw new Exception("没有权限的设备！");
				}
				logger.error("当前访问设备号："+uuid);
				Prop pk=PropKit.getProp("accessuuid.txt");
				boolean flag=false;
				Iterator it= pk.getProperties().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					String value = (String) entry.getValue();
					if(value.equals(uuid)){
						flag=true;
						break;
					}
				}
				if(flag) {
					count = PropKit.getInt("mbpagesize");
				}else{
					throw  new Exception("没有权限的设备！");
				}
			}
			p.setCount(count);
			p=istoday(p);

			if(p.getNowpage()!=0) {
				res = PgsqlKit.findByCondition(ClassKit.javClass, p);
				res.put("status", 0);
				res.put("pagecount",count);
			}else{
				res.put("status", -2);
				res.put("errmsg","参数错误");
			}
		} catch (Exception e) {
			logger.error("getLeft: " + e);
			res.put("errmsg",e.toString());
			res.put("status", -1);
		}

		String callback=getPara("callback");
		if(StringUtils.isNotBlank(callback)){
			renderText("jsonp(" + JsonKit.bean2JSON(res)+")");
		}else {
			renderText(JsonKit.bean2JSON(res));
		}
	}

	private SearchQueryP istoday(SearchQueryP p){
		int istoday=PropKit.getInt("showintoday");
		if(istoday==1){
			String overtime=DateKit.getStringDate();
			Map rp=p.getParameters();
			if(rp==null){
				rp=new HashMap();
				rp.put("LTE_times",overtime);
			}else{
				if(null==rp.get("times")){
					rp.put("LTE_times",overtime);
				}
			}
			p.setParameters(rp);
		}
		return p;
	}

	public void getBt() {
		try {
			javsrc jav = getModel(javsrc.class);
			String sv = getPara("searchval");
			String id = getPara("mgid");
			String res = PageKit.getBtLinks(sv, id, jav);

			String callback=getPara("callback");
			if(StringUtils.isNotBlank(callback)){
				renderText("jsonp(" + res+")");
			}else {
				renderText(res);
			}
		}catch (Exception e){
			logger.error("getBt: " + e.toString());
			renderText("");
		}
	}

	public void getclweb(){
		Map resp=new HashMap();
		Map head=new HashMap();
		head.put("Content-Type","application/x-www-form-urlencoded");
		String strStream="a=g&v=0";
		String url=PropKit.get("clweb");
		String res= null;
		try {
			res = MultitHttpClient.postInStreamAndHeaders(url,strStream,head);
		} catch (Exception e) {
			res = "获取草榴网址异常";
		}
		resp.put("status","0");
		resp.put("res",res);
		String callback=getPara("callback");
		if(StringUtils.isNotBlank(callback)){
			renderText("jsonp(" + JsonKit.bean2JSON(res)+")");
		}else {
			renderText(JsonKit.bean2JSON(res));
		}
	}
}

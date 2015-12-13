/**
 * 
 */
package com.meteor.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.meteor.interceptor.LoginCheck;
import com.meteor.kit.PageKit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author justlikemaki
 *
 */

@Before(LoginCheck.class)
public class WebAction extends Controller {
	private final Logger logger = LoggerFactory.getLogger(WebAction.class);

	@Clear
	public void tologin(){
		render("login.jsp");
	}
	@Clear
	public void login(){
		String root=getPara("root");
		String pass=getPara("pass");
		String cfroot=PropKit.get("root");
		String cfpass=PropKit.get("pass");
		if(root.equals(cfroot)&&pass.equals(cfpass)) {
			Map p = new HashMap();
			p.put("root", root);
			p.put("pass", pass);
			getSession().setAttribute("lguser", p);
			redirect("/");
		}else{
			getRequest().setAttribute("errmsg","账号密码错误");
			tologin();
		}
	}

	public void index(){
		newspage();
	}
	
	public void newspage(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request, num, pagesize, "newspage", "list", "") + ".jsp");
	}

	public void censored(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request, num, pagesize, "censored", "list", "tabtype")+ ".jsp");
	}

	public void uncensored(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request, num, pagesize, "uncensored", "list", "tabtype")+ ".jsp");
	}

	public void westporn(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request, num, pagesize, "westporn", "list", "tabtype")+ ".jsp");
	}

	public void classical(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request, num, pagesize, "classical", "list", "tabtype")+ ".jsp");
	}


	public void addedit(){
		HttpServletRequest request=getRequest();
		render(PageKit.topage(request, 0, 0, "addsrc", "addedit", "")+ ".jsp");
	}

	public void setting(){
		HttpServletRequest request=getRequest();
		render(PageKit.topage(request, 0, 0, "setting", "setting", "")+ ".jsp");
	}


	public void search(){
		HttpServletRequest request=getRequest();
		String p=getPara();
		editForm();
		String tagstr="";
		String searchzd = "";
		String sp = getPara("sp");
		String time = getPara("time");

		if(StringUtils.isNotBlank(sp)||StringUtils.isNotBlank(time)) {
			if (StringUtils.isBlank(sp)) {
				tagstr = time;
				searchzd = "times";
				if (StringUtils.isNotBlank(tagstr)) {
					request.setAttribute("searchname", "time");
					request.setAttribute("searchvalue", tagstr);
				}
			} else {
				tagstr = sp;
				searchzd = "tags";
				if (StringUtils.isNotBlank(tagstr)) {
					request.setAttribute("searchname", "sp");
					request.setAttribute("searchvalue", tagstr.toUpperCase());
				}
			}
		}else{
				tagstr = "";
				searchzd = "tags";
				request.setAttribute("searchname", "sp");
				request.setAttribute("searchvalue", "");
		}

		int num=1;
		if(StringUtils.isNotBlank(p)) {
			num = getParaToInt();
		}
		int pagesize= PropKit.getInt("pagesize");
		render(PageKit.topage(request,num, pagesize, tagstr.toUpperCase(), "list", searchzd)+ ".jsp");
	}

	private void editForm() {
		HttpServletRequest request=getRequest();
		ServletContext sct=getSession().getServletContext();
		//如果页面右侧参数为空，默认跳转到首页
		String res= PageKit.getparameters(sct);
		if(res==null){
			request.setAttribute("pagetype", "list");
			request.setAttribute("tab", "newspage");
		}
	}

}

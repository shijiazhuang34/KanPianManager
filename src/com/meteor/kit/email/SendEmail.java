package com.meteor.kit.email;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.PropKit;
import com.meteor.kit.DateKit;

/**
 * 
 * @ClassName SendEmail
 * @author Meteor
 * @date 2015年8月8日 下午9:32:34
 * @category 发送邮件的工具类
 */
public class SendEmail {
	private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);
	private static String flagDate = null;

	public static void sendWebChangeWarn(String url){
		String date = DateKit.getStringDateShort();
		if(flagDate == null || isDiffDay(date)) {
			boolean flag = send("justlikemaki@qq.com", "抓取网站内容或变更，请及时更新程序", "变更的网站为：" + url);
			logger.info("网站内容变更告警邮件发送结果：" + flag);
			flagDate = date;
		}
	}

	private static boolean isDiffDay(String date){
		int dayly = Integer.valueOf(DateKit.getTwoDay(date, flagDate));
		if (dayly>0){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月8日 下午9:34:47
	 * @Title send
	 * @param email
	 * @param etitle
	 * @param econtent
	 * @return boolean 返回类型
	 * @category 发送普通邮件调用方法
	 */
	public static boolean send(String email,String etitle,String econtent){
		//读取配置文件
		String  serverhost = PropKit.get("mail.smtp.serverhost");
		String	serverport = PropKit.get("mail.smtp.serverport");
		String	username = PropKit.get("mail.username");
		String	password = PropKit.get("mail.password");
		String	fromAddress = PropKit.get("mail.fromAddress");
				
		//这个类主要是设置邮件   
		MailSenderInfo mailInfo = new MailSenderInfo();    
		mailInfo.setMailServerHost(serverhost);    
		mailInfo.setMailServerPort(serverport);    
		mailInfo.setValidate(true);    
		mailInfo.setUserName(username);    
		mailInfo.setPassword(password);//您的邮箱密码    
		mailInfo.setFromAddress(fromAddress);    
      	mailInfo.setToAddress(email);    
      	mailInfo.setSubject(etitle);    
      	mailInfo.setContent(econtent);    
      	//这个类主要来发送邮件   
      	return SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式 
	}
}

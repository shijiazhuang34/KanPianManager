package com.meteor.task;

import java.net.URL;
import java.util.*;

import com.jfinal.kit.PropKit;
import com.meteor.kit.*;
import com.meteor.kit.http.MultitHttpClient;
import com.meteor.model.po.errpage;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;

public class GetResourceTask  implements Job{
	private final Logger logger = LoggerFactory.getLogger(GetResourceTask.class);

	
	/**
	 * 标题列表,页码,资源类型
	 * 获取资源
	 */
	private void getResourse(String type){
		try {
			long starttime=new Date().getTime();
			/**得到所有的资源标题--begin**/
			String javtitles="";
			/**得到所有的资源标题--end**/
			List<Integer> errPages=new ArrayList<Integer>();
			int loopnum=0;
			if(type.equals("censored")) {
				loopnum=10;
			}
			if(type.equals("uncensored")) {
				loopnum=5;
			}
			if(type.equals("westpron")) {
				loopnum=15;
			}
			logger.error("正在获取"+type+"的资源,page:1--"+loopnum);
			for(int i=1;i<=loopnum;i++){
				try{
					String newi = String.valueOf(i);
					if(type.equals("censored")) {
						 PageKit.getJavmoo(javtitles, "", newi);
					}
					if(type.equals("uncensored")) {
						 PageKit.getJavlog(javtitles, "", newi);
					}
					if(type.equals("westpron")) {
						 PageKit.getPornleech(javtitles, "", newi);
					}
				} catch (Exception e) {
					if(!e.toString().contains("404")) {
						errPages.add(i);
						logger.error(type+" 当前请求：" + i + "---" + e.toString());
						errpage err= new errpage(type,i+"",e.toString(),"");
						PgsqlKit.save(ClassKit.errTableName, err);
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException g) {
						//g.printStackTrace();
					}
				} catch(Throwable t) {
					if(!t.toString().contains("404")) {
						errPages.add(i);
						logger.error(type+" 当前请求：" + i + "---" + t.toString());
						errpage err= new errpage(type,i+"",t.toString(),"");
						PgsqlKit.save(ClassKit.errTableName,err);
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException g) {
						//g.printStackTrace();
					}
				}
			}

			if(errPages.size()>0){
				logger.error(type+" 失败页数:" + JsonKit.bean2JSON(errPages));
			}
			long edtime=new Date().getTime()-starttime;
			String hs= DateKit.getTimeOff(edtime);
			logger.error(type+" 全部执行完毕,"+"耗时："+hs);
		} catch (Exception e) {
			logger.error("更新"+type+"失败:"+e.toString());
		}		
	}
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		PageKit.testHaveNewHost();
		getResourse("censored");
		getResourse("uncensored");
		getResourse("westpron");
		PageKit.delrepeated();
	}
}

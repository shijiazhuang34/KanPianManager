package com.meteor.task;

import com.jfinal.kit.PropKit;
import com.meteor.kit.ClassKit;
import com.meteor.kit.PageKit;
import com.meteor.kit.PgsqlKit;
import com.meteor.kit.getpage.PageManager;
import com.meteor.kit.getpage.PageRun;
import com.meteor.model.po.errpage;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HandleErrTask implements Job{
	private final Logger logger = LoggerFactory.getLogger(HandleErrTask.class);

	/**
	 * @author Meteor
	 * @Title
	 * @category 重新拉取发生错误了的页面
	 */
	private void reFechPage(){
		try {
			boolean flag=HandleErrCircularQueueService.isEmpty();
			if(flag) {
				List<errpage> errpages = PgsqlKit.findall(ClassKit.errClass, null);
				HandleErrCircularQueueService.add(errpages);
				while (!HandleErrCircularQueueService.isEmpty()) {
					errpage onepage=HandleErrCircularQueueService.getList().poll();
					PgsqlKit.deleteById(ClassKit.errTableName, onepage.getId());
					PageManager pm = new PageManager();
					PageRun pr = new PageRun(pm);
					pr.doit(1, onepage.getNum(), onepage.getType(), onepage.getSearchkey());
					Thread.sleep(100000);
				}
			}
		} catch (Exception e) {
			logger.error("重新获取错误页面异常: " + e.toString());
		}

	}



	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		reFechPage();
	}
}

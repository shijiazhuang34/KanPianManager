package com.meteor.kit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 工作池
 * <功能详细描述>
 * 
 * @author  zhanglei
 * @version  [版本号, Jul 9, 2012]
 * @see  [相关类/方法]
 * @since  [民航代理人系统(ASMS)/ASMS5000]
 */
public class WorkPool {
	/**
	 * 分配线程池
	 */
	private ThreadPoolExecutor executorService;
	/**
	 * CallerRunsPolicy当线程池不能处理时，用当前线程处理。那么后续处理必须当前线程弄完了才能执行
	 * •第一步，初始的poolSize < corePoolSize，提交的runnable任务，会直接做为new一个Thread的参数，立马执行
•第二步，当提交的任务数超过了corePoolSize，就进入了第二步操作。会将当前的runable提交到一个block queue中

•第三步，如果block queue是个有界队列，当队列满了之后就进入了第三步。如果poolSize < maximumPoolsize时，会尝试new 一个Thread的进行救急处理，立马执行对应的runnable任务
•第四步，如果第三步救急方案也无法处理了，就会走到第四步执行reject操作。
	@param size 大小
	 */
	public WorkPool(int size){
		executorService = new ThreadPoolExecutor(size, size,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
		//executorService = Executors.newFixedThreadPool(size);
	}
	/**
	 * 获取线程池
	 * @return 获取线程池
	 */
	public ThreadPoolExecutor getExecutorService() {
		return executorService;
	}
	/**
	 * 执行任务
	 * @param command 任务
	 */
	public void execute(Runnable command){
		executorService.execute(command);
	}

	/**
	 * 提交线程
	 * @param call 任务
	 * @return 任务
	 */
	public Future submit(Callable call){
		return executorService.submit(call);
	}
	
	/**
	 * 关闭线程池
	 */
	public void shutdown(){
		executorService.shutdown();
	}
	
}

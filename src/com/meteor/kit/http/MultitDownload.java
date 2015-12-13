package com.meteor.kit.http;


public class MultitDownload {
	public static TaskThreadManagers instanceMultitDownload(long length,String url,String filename) throws Exception{
		com.meteor.kit.http.TaskThreadManagers ttm=new TaskThreadManagers();
		String out=new CyclicBarrierMultitDownload(ttm).multitDownload(length,url,5,filename);
		return ttm;
	}

	public static TaskThreadManagers instanceMultitDownloadSetThread(long length,String url,String filename,int threadnum) throws Exception{
		com.meteor.kit.http.TaskThreadManagers ttm=new TaskThreadManagers();
		String out=new CyclicBarrierMultitDownload(ttm).multitDownload(length,url,threadnum,filename);
		return ttm;
	}

	public static TaskThreadManagers instanceMultitDownloadSetRetry(long length,String url,String filename,int retrynum) throws Exception{
		com.meteor.kit.http.TaskThreadManagers ttm=new TaskThreadManagers();
		String out=new CyclicBarrierMultitDownload(ttm).multitDownload(length,url,5,filename,retrynum);
		return ttm;
	}

	public static TaskThreadManagers instanceMultitDownloadSetAll(long length,String url,String filename,int threadnum,int retrynum) throws Exception{
		com.meteor.kit.http.TaskThreadManagers ttm=new TaskThreadManagers();
		String out=new CyclicBarrierMultitDownload(ttm).multitDownload(length,url,threadnum,filename,retrynum);
		return ttm;	
	}
}

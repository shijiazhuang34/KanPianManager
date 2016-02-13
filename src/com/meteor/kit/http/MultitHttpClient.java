package com.meteor.kit.http;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.meteor.kit.DateKit;
import com.meteor.kit.JsonKit;
import com.meteor.kit.SecurityEncodeKit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @ClassName MultitHttpClient45
 * @author Meteor
 * @date 2015年8月11日 下午12:37:38
 * @category 基于httpclient4_5 多线程get post 下载文件 
 */
public class MultitHttpClient {
	private static final Logger logger = LoggerFactory.getLogger(MultitHttpClient.class);
	
	private static PoolingHttpClientConnectionManager cm = null;
	private static CloseableHttpClient httpclient=null;
	private static PoolingHttpClientConnectionManager cmDownload = null;
	private static CloseableHttpClient httpclientDownload=null;
	
	private static Builder unbRequestConfig = null;
	private static Map<String, String> headers = null;
	private static final int TIMEOUT = 30 * 1000;
	private static final int MAX_HTTP_CONNECTION = 60;
	private static final int MAX_HTTP_CONNECTION_D = 128;
	private static final int MAX_PER_ROUTE = 100;
	private static final String encode = "UTF-8";

	public static String getFileroot() {
		return fileroot;
	}

	//文件下载根目录
	private static final String fileroot=System.getProperty("catalina.home") + "/temp/download/";
	
	static { 
		//设置header
		headers = new HashMap<String, String>();
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		//headers.put("Accept-Language", "zh-CN");
		headers.put("Connection", "Keep-Alive");
		headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 QIHU 360SE");
	    //headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 Geek");
	    //headers.put("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; Geek)");
	    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip,deflate,sdch");
		headers.put("Cache-Control", "no-cache");
		//headers.put("Cache-Control", "max-age=0");				

		//设置request
		unbRequestConfig = RequestConfig.custom()
				.setSocketTimeout(TIMEOUT)
				.setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT);
				
		//关闭不活动的连接,2分钟检测一次
		new Thread() {
			public void run() {
				while (!Thread.interrupted()) {			
					closeClient();					
					closeClientDownload();
					try {
						Thread.sleep(60 * 1000 * 2);
					} catch (InterruptedException e) {
						//e.printStackTrace();
						logger.error("循环清空线程池的线程sleep",e);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午4:32:10
	 * @Title instancePool void 返回类型
	 * @category 实例化多线程管理连接池
	 */
	private static void instancePool(){
		try {
			//需要通过以下代码声明对https连接支持 
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
		    SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore,new AnyTrustStrategy()).build();
		    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);  
		    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.getSocketFactory())  
		           .register("https", sslsf)  
		           .build();  
		    cm=new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		    //将最大连接数增加到128
			cm.setMaxTotal(MAX_HTTP_CONNECTION);
			//将每个路由基础的连接增加到50
			cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("初始化cm线程池",e);
		}
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午4:32:10
	 * @Title instancePool void 返回类型
	 * @category 实例化多线程管理连接池
	 */
	private static void instancePoolDownload(){
		try {
			//需要通过以下代码声明对https连接支持 
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
		    SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore,new AnyTrustStrategy()).build();
		    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;  
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);  
		    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
		           .register("http", PlainConnectionSocketFactory.getSocketFactory())  
		           .register("https", sslsf)  
		           .build();  
		    cmDownload=new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		    //将最大连接数增加到128
		    cmDownload.setMaxTotal(MAX_HTTP_CONNECTION_D);
			//将每个路由基础的连接增加到50
		    cmDownload.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error("初始化cmDownload线程池",e);
		}
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:38:33
	 * @Title createHttpClient
	 * @return Map 返回类型
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.KeyManagementException
	 * @category 获取httpclient对象和request配置
	 */
	protected static Map createHttpClient(boolean bcs) {
		if(cm==null||httpclient==null){
			if(cm==null){
				instancePool();
			}
			if(httpclient==null){
				if(PropKit.get("isproxy").equals("1")){
					String host= PropKit.get("host");
					int port=PropKit.getInt("port");
					httpclient = HttpClients.custom().setProxy(new HttpHost(host, port)).setConnectionManager(cm).build();
				}else{
					httpclient = HttpClients.custom().setConnectionManager(cm).build();
				}
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RequestConfig  defaultRequestConfig= null;
		if(bcs==false){
			unbRequestConfig.setCookieSpec(CookieSpecs.STANDARD_STRICT);
		}

		defaultRequestConfig = unbRequestConfig.build();
		Map p=new HashMap();
		p.put("RequestConfig", defaultRequestConfig);
		p.put("httpclient", httpclient);
		return p;
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:38:33
	 * @Title createHttpClient
	 * @return Map 返回类型
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.KeyManagementException
	 * @category 获取httpclient对象和request配置
	 */
	protected static Map createHttpClientDownload(boolean bcs) {
		if(cmDownload==null||httpclientDownload==null){
			if(cmDownload==null){
				instancePoolDownload();
			}
			if(httpclientDownload==null){
				httpclientDownload = HttpClients.custom().setConnectionManager(cmDownload).build();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RequestConfig  defaultRequestConfig= null;
		if(bcs==false){
			unbRequestConfig.setCookieSpec(CookieSpecs.STANDARD_STRICT);
		}

		defaultRequestConfig = unbRequestConfig.build();
		Map p=new HashMap();
		p.put("RequestConfig", defaultRequestConfig);
		p.put("httpclient", httpclientDownload);
		return p;
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:45:17
	 * @Title releaseAndClose
	 * @param httpClient
	 * @param hr
	 * @throws java.io.IOException void 返回类型
	 * @category 释放连接
	 */
	public static void releaseMethod(HttpRequestBase hr) {
		if(hr!=null){
			hr.releaseConnection();
		}
	}

	/***
	 *
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午4:32:58
	 * @Title closeClient
	 * @throws java.io.IOException void 返回类型
	 * @category 执行循环请求或多线程请求后必须手动关闭client和连接池 待下次请求后创建
	 */
	public static void closeClient(){
		try {
			if(cm!=null){
				PoolStats ps=cm.getTotalStats();
				int leased=ps.getLeased();
				int pending=ps.getPending();
				if(leased==0&&pending==0){
					cm.closeIdleConnections(2, TimeUnit.MINUTES);
					cm.close();
					cm=null;
					if(httpclient!=null){
						httpclient.close();
						httpclient=null;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("关闭client",e.toString());
		}
	}

	/***
	 *
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午4:32:58
	 * @Title closeClient
	 * @throws java.io.IOException void 返回类型
	 * @category 执行循环请求或多线程请求后必须手动关闭client和连接池 待下次请求后创建
	 */
	public static void closeClientDownload(){
		try{
			if(cmDownload!=null){
				PoolStats ps=cmDownload.getTotalStats();
				int leased=ps.getLeased();
				int pending=ps.getPending();
				if(leased==0&&pending==0){
					cmDownload.closeIdleConnections(2, TimeUnit.MINUTES);
					cmDownload.close();
					cmDownload=null;
					if(httpclientDownload!=null){
						httpclientDownload.close();
						httpclientDownload=null;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("关闭client",e.toString());
		}
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午4:36:08
	 * @Title releaseAndClose
	 * @param hr
	 * @throws java.io.IOException void 返回类型
	 * @category 单一请求调用的关闭方法
	 */
	private static void releaseAndClose(HttpRequestBase hr) throws IOException{
		releaseMethod(hr);
		closeClient();
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:39:51
	 * @Title setHeaders
	 * @param m void 返回类型
	 * @category 设置请求方法的header
	 */
	protected static void setHeaders(HttpRequestBase m,Map<String, String> newheaders) {
		Iterator it = headers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			m.setHeader(key, value);
		}
		if(newheaders!=null){
			it = newheaders.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				m.setHeader(key, value);
			}
		}
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午2:22:12
	 * @Title setParames
	 * @param param
	 * @return
	 * @throws java.net.URISyntaxException URI 返回类型
	 * @category 设置请求参数
	 */
	private static URI setParames(Map<String, String> param,String url) throws URISyntaxException {
		URIBuilder uribuilder=new URIBuilder(url);
		Iterator it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			uribuilder.setParameter(key, value);
		}
		uribuilder.setParameter("http.protocol.content-charset","UTF-8");
		return uribuilder.build();
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:44:30
	 * @Title getEntity
	 * @param httpClient
	 * @param hr
	 * @return
	 * @throws java.io.IOException
	 * @throws ClientProtocolException
	 * @throws Exception HttpEntity 返回类型
	 * @category 获取CloseableHttpResponse 并根据状态码抛出异常
	 */
	protected static CloseableHttpResponse getResponse(CloseableHttpClient httpClient,HttpRequestBase hr) throws Exception{
		CloseableHttpResponse response = httpClient.execute(hr);
		if (response.getStatusLine().getStatusCode() >= 400) {
			response.close();
			throw new IOException("Got bad response, error code = "+ response.getStatusLine().getStatusCode());
		}
		return response;
	}

	public static String getReHost(String url,Map<String, String> newheaders) throws Exception {
		Map p=createHttpClient(false);
		CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
		HttpGet httpget = new HttpGet(url);
		HttpContext httpContext = new BasicHttpContext();
		setHeaders(httpget,newheaders);
		httpget.setConfig((RequestConfig) p.get("RequestConfig"));
		CloseableHttpResponse response=httpClient.execute(httpget, httpContext);
		HttpHost targetHost = (HttpHost)httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
		response.close();
		releaseMethod(httpget);
		return targetHost.getHostName();
	}

	public static void getByNormal(String url) throws Exception {
		// Get请求
		HttpGet httpget = new HttpGet(url);
		// 发送请求
		CloseableHttpClient httpClient=HttpClients.custom().build();
		CloseableHttpResponse response=httpClient.execute(httpget);
		response.close();
		releaseMethod(httpget);
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午10:45:37
	 * @Title getFileName
	 * @param response
	 * @param entity
	 * @return String 返回类型
	 * @category 获取应用类型的文件名称
	 */
	protected static String getFileName(CloseableHttpResponse response,HttpEntity entity){
		String filename=null;
		String ctype=entity.getContentType().getValue();
		if(!ctype.contains("text/html")){
			Header hd= response.getFirstHeader("Content-Disposition");
			if(hd!=null){
				filename=hd.toString().split(";")[1];
				filename=filename.substring(filename.indexOf("\"")+1, filename.lastIndexOf("\""));
				filename=DateKit.getStringTodayB()+"_"+filename;
			}else{
				filename=DateKit.getStringTodayB()+"_"+DateKit.buildRandom(5);
				String filetype=".*";
				try {
					Prop p=PropKit.getProp("contenttype.properties");
					filetype= p.get(ctype);
				} catch (Exception e) {
					// TODO: handle exception
				}
				filename=filename+filetype;
			}
		}
		return filename;
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:46:20
	 * @Title read
	 * @param entity
	 * @return
	 * @throws Exception String 返回类型
	 * @category 判断返回的数据是否gzip压缩 并选择相应的read方法
	 */
	private static String read(CloseableHttpResponse response,String filename) throws Exception {
		HttpEntity entity=response.getEntity();
		if (entity == null) {
			return "";
		}
		Header header = entity.getContentEncoding();
		if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
			return readGzip(entity.getContent(), encode);
		} else {
			return read(entity.getContent(), encode);
		}

	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:48:12
	 * @Title read
	 * @param inputStream
	 * @param encode
	 * @return
	 * @throws Exception String 返回类型
	 * @category 读取没有gzip的数据
	 */
	private static String read(InputStream inputStream, String encode)
			throws Exception {
		String res=IOUtils.toString(inputStream, encode);
		if(inputStream!=null){
			inputStream.close();
		}
		return res;
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午12:49:49
	 * @Title readGzip
	 * @param inputStream
	 * @param encode
	 * @return
	 * @throws Exception
	 * @throws java.io.IOException String 返回类型
	 * @category 读取gzip的数据
	 */
	private static String readGzip(InputStream inputStream, String encode) throws Exception, IOException {
		InputStream is = null;
		GZIPInputStream gzin = null;
		InputStreamReader isr = null;
		StringBuffer sb = new StringBuffer();
		try {
			is = inputStream;
			gzin = new GZIPInputStream(is);
			isr = new InputStreamReader(gzin, encode);
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			String tempbf;
			while ((tempbf = br.readLine()) != null) {
				sb.append(tempbf);
				sb.append("\r\n");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (isr != null) {
				isr.close();
			}
			if (gzin != null) {
				gzin.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return sb.toString();
	}

	/**
	 *
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午9:28:11
	 * @Title readGzipStream
	 * @param inputStream
	 * @param encode
	 * @return
	 * @throws Exception
	 * @throws java.io.IOException InputStream 返回类型
	 * @category 把gzip流转成普通流
	 */
	private static InputStream readGzipStream(InputStream inputStream, String encode) throws Exception, IOException {
		String str=readGzip( inputStream,  encode) ;
		return IOUtils.toInputStream(str);
	}
	
	public static Map getLengthAngName(String url) throws Exception{
		Map p= MultitHttpClient.createHttpClientDownload(false);
		CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
		HttpGet httpHead  = new HttpGet(url);
		setHeaders(httpHead, null);
		httpHead.setConfig((RequestConfig) p.get("RequestConfig"));
		CloseableHttpResponse response = getResponse(httpClient,httpHead );  
		HttpEntity entity=response.getEntity();
		String ctype=entity.getContentType().getValue();
		String filename= getFileName(response, entity);
		
		//Content-Length   		
		long contentLength=entity.getContentLength();   
		releaseMethod(httpHead);
		
		Map mp=new HashMap();
		mp.put("contentLength", contentLength);
		mp.put("filename", filename);
		mp.put("ctype", ctype);
		return mp;
	}
	
	/***
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:33
	 * @Title getCookie
	 * @param url
	 * @param params
	 * @param newheaders
	 * @return
	 * @throws Exception BasicCookieStore 返回类型
	 * @category 获取地址的所有返回的cookie
	 */
	public static String getCookie(String url,Map<String, String> params,Map<String, String> newheaders) throws Exception {
		Map p=createHttpClient(true);
		CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
		HttpGet httpget = new HttpGet(url);
		setHeaders(httpget,newheaders);
		httpget.setConfig((RequestConfig) p.get("RequestConfig"));
		
		if(params!=null){
			httpget.setURI(setParames(params,url));
		}
		CloseableHttpResponse response=getResponse(httpClient,httpget);
	
		//BasicCookieStore cookieStore = new BasicCookieStore();
		StringBuffer sb=new StringBuffer();
	    Header[] setCookie = response.getHeaders("Set-Cookie");
	    for(Header ck:setCookie){		    
	    	String cook=ck.getValue();
	    	String keyval=cook.split(";")[0];
	    	String key=keyval.split("=")[0];
	    	String val=keyval.split("=")[1];
	    	
	    	sb.append(key+"="+val+";");
	    }    
	    releaseMethod(httpget);
	    return sb.toString();
	}
	
	/***
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:33
	 * @Title getCookie
	 * @param url
	 * @param params
	 * @param newheaders
	 * @return
	 * @throws Exception BasicCookieStore 返回类型
	 * @category 获取地址的所有返回的cookie 不更新header
	 */
	public static String getCookie(String url,Map<String, String> params) throws Exception {
		return getCookie(url, params,null);
	}
	
	/***
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:33
	 * @Title getCookie
	 * @param url
	 * @param params
	 * @param newheaders
	 * @return
	 * @throws Exception BasicCookieStore 返回类型
	 * @category 获取地址的所有返回的cookie 不更新header 不传参数
	 */
	public static String getCookie(String url) throws Exception {
		return getCookie(url, null,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:48
	 * @Title get
	 * @param url
	 * @param params
	 * @param bcs
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httpget
	 */
	public static String get(String url,Map<String, String> params,boolean bcs,Map<String, String> newheaders) throws Exception {
		Map p=createHttpClient(bcs);
		CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
		HttpGet httpget = new HttpGet(url);
		setHeaders(httpget,newheaders);
		httpget.setConfig((RequestConfig) p.get("RequestConfig"));
		
		if(params!=null){
			httpget.setURI(setParames(params,url));
		}
		CloseableHttpResponse response=getResponse(httpClient,httpget);
		HttpEntity entity = response.getEntity();
		String filename=getFileName(response, entity);
		
		String res="";
		if(filename!=null){
			res = FileDownload( response, fileroot, filename, 1);
		}else{
			res = read(response,filename);
		}
		releaseMethod(httpget);
		return res;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:48
	 * @Title get
	 * @param url
	 * @param params
	 * @param bcs
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httpget 
	 */
	public static String getInParams(String url,Map<String, String> params) throws Exception {
		return get( url, params,true,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:48
	 * @Title get
	 * @param url
	 * @param params
	 * @param bcs
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httpget
	 */
	public static String getInHeaders(String url,Map<String, String> newheaders) throws Exception {
		return get( url, null,false,newheaders);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:48
	 * @Title get
	 * @param url
	 * @param params
	 * @param bcs
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httpget 不更新header 不传cookie
	 */
	public static String getInHeadersAndParams(String url,Map<String, String> newheaders,Map<String, String> params) throws Exception {
		return get( url, params,true,newheaders);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:27:48
	 * @Title get
	 * @param url
	 * @param params
	 * @param bcs
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httpget 只传url
	 */
	public static String get(String url) throws Exception {
		return get( url, null,false,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost
	 */
	public static String post(String url,Map<String, String> params,String strStream,Map<String, String> newheaders) throws Exception {
		Map p=createHttpClient(false);
		CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
		HttpPost httppost = new HttpPost(url);
		setHeaders(httppost,newheaders);
		httppost.setConfig((RequestConfig) p.get("RequestConfig"));
		
		if(params!=null){
			httppost.setURI(setParames(params,url));
		}
		if(StringUtils.isNotBlank(strStream)){
			InputStream is=IOUtils.toInputStream(strStream, encode);
			InputStreamEntity reqEntity = new InputStreamEntity(is);
			//reqEntity.setContentType("binary/octet-stream");
			reqEntity.setContentType("application/x-www-form-urlencoded");
			reqEntity.setChunked(true);
			httppost.setEntity(reqEntity);
		}
		
		CloseableHttpResponse response=getResponse(httpClient,httppost);
		String res = read(response,null);

		releaseMethod(httppost);
		return res;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 
	 */
	public static String postInStreamAndParam(String url,Map<String, String> params,String strStream) throws Exception {
		return post( url, params, strStream,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 
	 */
	public static String postInParams(String url,Map<String, String> params) throws Exception {
		return post( url, params, null,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 
	 */
	public static String postInHeaders(String url,Map<String, String> newheaders) throws Exception {
		return post( url, null, null,newheaders);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 
	 */
	public static String postInHeadersAndParams(String url,Map<String, String> newheaders,Map<String, String> params) throws Exception {
		return post( url, params, null,newheaders);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 
	 */
	public static String postInStream(String url,String strStream) throws Exception {
		return post( url, null, strStream,null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost
	 */
	public static String postInStreamAndHeaders(String url,String strStream,Map<String, String> newheaders) throws Exception {
		return post( url, null, strStream,newheaders);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:00
	 * @Title post
	 * @param url
	 * @param params
	 * @param strStream
	 * @param newheaders
	 * @return
	 * @throws Exception String 返回类型
	 * @category httppost 只传url
	 */
	public static String post(String url) throws Exception {
		return post( url, null, null,null);
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:35
	 * @Title getFileDownByPath
	 * @param url
	 * @param filedest 文件路径
	 * @param isdir filedest是否是文件夹
	 * @param newheaders
	 * @return String 返回类型
	 * @category get方法下载文件
	 */
	public static String getFileDownByPath(String url,String filedest,int isdir,Map<String, String> newheaders) {
		Map resp=new HashMap();
		String res="";
		HttpGet httpget=null;
		try {
			Map p=createHttpClient(false);
			CloseableHttpClient httpClient = (CloseableHttpClient) p.get("httpclient");
			httpget = new HttpGet(url);
			setHeaders(httpget,newheaders);
			httpget.setConfig((RequestConfig) p.get("RequestConfig"));
	
			CloseableHttpResponse response = getResponse(httpClient,httpget);
			HttpEntity entity = response.getEntity();
			String filename=getFileName(response, entity);
			if(StringUtils.isNotBlank(filename)){
				res=FileDownload( response, filedest, filename, isdir);
			}else{
				String ctype=entity.getContentType().getValue();
				resp.put("status", -2);
				resp.put("errmsg", ctype+"_"+"不是可下载文件类型");
				res=JsonKit.map2JSON(resp);
			}			
			
		} catch (Exception e) {
			// TODO: handle exception
			if(!e.toString().contains("404")) {
				logger.error("下载文件" + e.toString());
			}
			resp.put("status", -1);
			resp.put("errmsg", e.toString());
			res=JsonKit.map2JSON(resp);
		}  catch(Throwable t) {
			logger.error("下载文件"+t.toString());
			resp.put("status", -1);
			resp.put("errmsg", t.toString());
			res=JsonKit.map2JSON(resp);
		}
		
		releaseMethod(httpget);
		return res;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:35
	 * @Title getFileDownByPath
	 * @param url
	 * @param filedest 文件路径
	 * @param isdir filedest是否是文件夹
	 * @param newheaders
	 * @return String 返回类型
	 * @category get方法下载文件 不更新header
	 */
	public static String getFileDownByPath(String url,String filedest,int isdir) {
		 return getFileDownByPath( url, filedest, isdir, null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:35
	 * @Title getFileDownByPath
	 * @param url
	 * @param filedest 文件路径
	 * @param isdir filedest是否是文件夹
	 * @param newheaders
	 * @return String 返回类型
	 * @category get方法下载文件 传文件夹路径，默认文件命名
	 */
	public static String getFileDownByPathDir(String url,String filedest) {
		 return getFileDownByPath( url, filedest, 1, null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午2:28:35
	 * @Title getFileDownByPath
	 * @param url
	 * @param filedest 文件路径
	 * @param isdir filedest是否是文件夹
	 * @param newheaders
	 * @return String 返回类型
	 * @category get方法下载文件 传完整文件路径
	 */
	public static String getFileDownByPathFull(String url,String filedest) {
		 return getFileDownByPath( url, filedest, 0, null);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月11日 下午1:31:04
	 * @Title FileDownload
	 * @param url
	 * @param filedest 根目录
	 * @return
	 * @throws Exception String 返回类型
	 * @category 下载application/image类型的对象
	 */
	private static String FileDownload(CloseableHttpResponse response,String filedest,String filename,int isdir) {
		HttpEntity entity=response.getEntity();		
		Map res=new HashMap();
		String fullpath=null;
		if(isdir==1){
			fullpath=filedest+"/"+filename;
		}else{
			fullpath=filedest;
		}
		try {
			File f=new File(fullpath);
			if(!f.exists()){
				InputStream is=null;
				Header header =  entity.getContentEncoding();
				if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
					is=readGzipStream(entity.getContent(), encode);
				}else{
					is=entity.getContent();
				}
				FileUtils.copyInputStreamToFile(is, f);				
			}			
			res.put("status", 0);
			res.put("filepath", f.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			res.put("status", -1);
			res.put("errmsg", e.toString());
		}
		return JsonKit.map2JSON(res);
	}

	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月14日 下午4:05:22
	 * @Title fechPage
	 * @param httpParams void 返回类型
	 * @category 抓取页面并输出log
	 */
	public static String fechPage(HttpParams httpParams){
		if(!valiCrawlParam(httpParams)){
			return "请求参数异常";
		}
		String responseInfo="";
		String url=httpParams.getUrl();
		Map param=httpParams.getParamMap();
		Map head=httpParams.getHead();
		
		try {
			if(httpParams.isPost()==false){
				if(param!=null&&head!=null){
					responseInfo=getInHeadersAndParams(url, head, param);
				}else if(param!=null){
					responseInfo=getInParams(url, param);
				}else if(head!=null){
					responseInfo=getInHeaders(url, head);
				}else{
					responseInfo=get(url);
				}
			}else{			
				String strStream=httpParams.getStreamStr();
				if(param!=null&&head!=null){
					if(StringUtils.isNotBlank(strStream)){
						responseInfo=post(url, param, strStream, head);
					}else{
						responseInfo=postInHeadersAndParams(url, head, param);
					}
				}else if(param!=null){
					if(StringUtils.isNotBlank(strStream)){
						responseInfo=postInStreamAndParam(url, param, strStream);
					}else{
						responseInfo=postInParams(url, param);
					}
				}else if(head!=null){
					if(StringUtils.isNotBlank(strStream)){
						responseInfo=postInStreamAndHeaders(url, strStream, head);
					}else{
						responseInfo=postInHeaders(url, head);
					}
				}else{
					if(StringUtils.isNotBlank(strStream)){
						responseInfo=postInStream(url, strStream);
					}else{
						responseInfo=post(url);
					}
				}
			}			
			httpParams.setResponseInfo(responseInfo);
		} catch (Exception e) {
			// TODO: handle exception
			handleError(e.toString(),httpParams);
		} catch (Throwable t){
			handleError(t.toString(),httpParams);
		}
		log(httpParams);
		return responseInfo;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月8日 下午9:59:18
	 * @Title valiCrawlParam
	 * @param crawlParam
	 * @return boolean 返回类型
	 * @category 校验操作参数是否为空
	 */
	private static boolean valiCrawlParam(HttpParams httpParams){
		if(httpParams!=null){
			if (StringUtils.isBlank(httpParams.getUrl())) {
				handleError("请求目标主机的地址为空", httpParams);
				return false;
			}
			if (StringUtils.isBlank(httpParams.getSiteName())) {
				handleError("请求站点名称不能为空", httpParams);
				return false;
			}
			if (StringUtils.isBlank(httpParams.getDesc())) {
				handleError("操作目的描述不能为空", httpParams);
				return false;
			}
		}else{
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月8日 下午10:17:31
	 * @Title log
	 * @param content
	 * @param crawlParam void 返回类型
	 * @category 打印执行方法后的日志
	 */
	private synchronized static void log(HttpParams httpParams) {
		try {
			if (true) {
				String ml = System.getProperty("catalina.home") + "/javlog/crawl/";
				//String ml = "d:/" + "logs/crawl/";
				File f0 = new File(ml);
				if (!f0.exists()) {
					f0.mkdir();
				}
				ml=ml + StringUtils.trimToEmpty(httpParams.getSiteName())+"_"+ DateKit.getStringDateShort() + ".txt";
				File f = new File(ml);
				if (!f.exists()) {
					f.createNewFile();
				}
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(ml,true),"UTF-8");
				out.write(DateKit.getStringDate());
				out.write(JsonKit.toJsonPretty(httpParams));
				out.write("\r\n");
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error("打印抓取页面log",e);
		}
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月8日 下午10:00:22
	 * @Title handleError
	 * @param e
	 * @param crawlParam void 返回类型
	 * @category 处理错误
	 */
	private static void handleError(String e, HttpParams httpParams) {
		httpParams.setRequestSuccess(false);
		httpParams.setRequestErrorInfo(e);
		//System.out.println(e);
	}

}

class AnyTrustStrategy implements TrustStrategy {
	@Override
	public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate,
			String paramString) throws CertificateException {
		// TODO Auto-generated method stub
		return true;
	}
}

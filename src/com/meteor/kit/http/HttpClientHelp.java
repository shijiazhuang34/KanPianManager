package com.meteor.kit.http;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.PropKit;

/**
 * httpclient 帮助类
 */
public class HttpClientHelp {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelp.class);
	/**
	 * The constant TEXT_MIME.
	 */
	public static final String TEXT_MIME = "text/plain";
	/**
	 * The constant HTML_MIME.
	 */
	public static final String HTML_MIME = "text/html";
	/**
	 * The constant XML_MIME.
	 */
	public static final String XML_MIME = "application/xml";
	/**
	 * The constant JSON_MIME.
	 */
	public static final String JSON_MIME = "application/json";
	/**
	 * The constant JSON_FORM.
	 */
	public static final String JSON_FORM = "application/x-www-form-urlencoded";

	private static final int DEFAULT_CONN_TIMEOUT_MILLISECONDS = 30 * 1000;
	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 30 * 1000;
	
	private static final int CLOSE_INACTIVE_CONNECTIONS_SECONDS = 30;

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 500;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 75;

	public static String HTTP_ENCODING = "UTF-8";

	private static PoolingHttpClientConnectionManager connectionManager;

	static {
	
		try {
			// 需要通过以下代码声明对https连接支持
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
			HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslsf).build();
			connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 将最大连接数增加到128
			connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
			// 将每个路由基础的连接增加到50
			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
		} catch (Exception e) {
			logger.error("初始化cm线程池异常",e);
		} 
	   
	}
	
	public static String doPost(String url) throws Exception {
		return doPost(url, Boolean.FALSE);
	}
	
	public static String doPost(String url, boolean keepAlive) throws Exception {
		return doPost(url, null, null, null, null, HTTP_ENCODING,HTTP_ENCODING, Boolean.FALSE, keepAlive);
	}
	
	public static String doPost(String url, final String entities, String mimeType, boolean keepAlive) throws Exception {
		return doPost(url, entities, mimeType, HTTP_ENCODING, keepAlive);
	}

	public static String doPost(String url, final String entities, String mimeType, String encoding, boolean keepAlive) throws Exception {
		return doPost(url, entities, mimeType, encoding, Boolean.FALSE, keepAlive);
	}
	
	public static String doPost(String url, final String entities, String mimeType, String encoding, boolean needCookie, boolean keepAlive) throws Exception {
		return doPost(url, entities, mimeType, null, encoding, needCookie, keepAlive);
	}
	
	public static String doPost(String url, final String entities, String mimeType, Map<String, String> headers, String encoding, boolean needCookie, boolean keepAlive) throws Exception {
		return doPost(url, entities, mimeType, null, headers, encoding,encoding, needCookie, keepAlive);
	}
	
	public static String doPost(String url, Map<String, String> params, boolean keepAlive) throws Exception {
		return doPost(url, params,HTTP_ENCODING, keepAlive);
	}
	
	public static String doPost(String url, Map<String, String> params, String encoding, boolean keepAlive) throws Exception {
		return doPost(url, params,encoding, Boolean.FALSE, keepAlive);
	}
	
	public static String doPost(String url, Map<String, String> params, String encoding, boolean needCookie, boolean keepAlive) throws Exception {
		return doPost(url, params, null,encoding, needCookie, keepAlive);
	}
	
	public static String doPost(String url, Map<String, String> params, Map<String, String> headers, String encoding, boolean needCookie, boolean keepAlive) throws Exception {
		return doPost(url, null, null, params, headers, encoding,encoding, needCookie, keepAlive);
	}
	
	public static String doGet(String url) throws Exception {
		return doGet(url, Boolean.FALSE);
	}
	
	public static String doGet(String url, boolean keepAlive) throws Exception {
		return doGet(url, null, null, HTTP_ENCODING,HTTP_ENCODING, Boolean.FALSE, keepAlive);
	}
	
	public static String doGet(String url, Map<String, String> params, boolean keepAlive) throws Exception {
		return doGet(url, params,HTTP_ENCODING, keepAlive);
	}

	public static String doGet(String url, Map<String, String> params, String encoding, boolean keepAlive) throws Exception {
		return doGet(url, params,encoding, Boolean.FALSE, keepAlive);
	}
	
	public static String doGet(String url, Map<String, String> params, String encoding, boolean needCookie, boolean keepAlive) throws Exception {
		return doGet(url, params, null,encoding,encoding, needCookie, keepAlive);
	}
	
	
	private static void logUrl(HttpRequestBase requestBase) throws MalformedURLException {
		logger.info(String.format("发送请求:%s:%s | %s ", requestBase.getURI().getHost(), requestBase.getURI().getPort(), requestBase.getURI().toURL()));
	}

	private static void validateResponse(HttpResponse response, HttpRequestBase requestBase, Long timeSpan) throws MalformedURLException {
		StatusLine status = response.getStatusLine();
		logger.info(String.format(" %s:%s | %s | %s | %s", requestBase.getURI().getHost(), requestBase.getURI().getPort(), status.getStatusCode(), timeSpan, requestBase.getURI().toURL()));
	}
	
	private static String getResult(String url, String encoding, HttpResponse response) throws Exception {
		String responseContent = "";
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			logger.warn(String.format("Http entity is null! 请求地址 {%s},response status is {%s}", url, response.getStatusLine()));
		} else {
			responseContent = EntityUtils.toString(entity, encoding);
			EntityUtils.consume(entity);
		}
		return responseContent;
	}


	private static CloseableHttpClient getHttpClient() {
		connectionManager.closeExpiredConnections();
		// 可选的, 关闭自定义秒内不活动的连接
		connectionManager.closeIdleConnections(CLOSE_INACTIVE_CONNECTIONS_SECONDS, TimeUnit.SECONDS);
		
		CloseableHttpClient httpclient=null;
		if(PropKit.get("isproxy").equals("1")){
			String host= PropKit.get("host");
			int port=PropKit.getInt("port");
			httpclient = HttpClients.custom().setProxy(new HttpHost(host, port)).setConnectionManager(connectionManager).build();
		}else{
			httpclient = HttpClients.custom().setConnectionManager(connectionManager).build();
		}
		return httpclient;
	}
	
	private static RequestConfig getRequestConfig(boolean needCookie){
		Builder unbRequestConfig = RequestConfig.custom()
				.setMaxRedirects(3)//设置最大跳转数
				.setSocketTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS)
				.setConnectTimeout(DEFAULT_CONN_TIMEOUT_MILLISECONDS)
				.setConnectionRequestTimeout(DEFAULT_CONN_TIMEOUT_MILLISECONDS);
		if(needCookie){
			unbRequestConfig.setCookieSpec(CookieSpecs.STANDARD_STRICT);
		}
		return unbRequestConfig.build();
	}

	private static String doRequest(String url, HttpRequestBase request, String resultEncoding, boolean keepAlive) throws Exception {
		try {
			logUrl(request);
			if (!keepAlive) {
				request.setHeader("Connection", "close");
			}
			StopWatch watch = new StopWatch();
			watch.start();
			CloseableHttpClient httpClient = getHttpClient();
			HttpResponse response = httpClient.execute(request);
			watch.stop();
			validateResponse(response, request, watch.getTime());
			return getResult(url, resultEncoding, response);
		} catch (ConnectTimeoutException e) {
			logger.error(String.format("%s %s:%s | %s | %s | %s",e.getMessage() ,request.getURI().getHost(), request.getURI().getPort(), 408.0, DEFAULT_CONN_TIMEOUT_MILLISECONDS, request.getURI().toURL()));
			throw e;
		} catch (SocketTimeoutException e) {
			logger.error(String.format("%s %s:%s | %s | %s | %s",e.getMessage(), request.getURI().getHost(), request.getURI().getPort(), 408.1, DEFAULT_READ_TIMEOUT_MILLISECONDS, request.getURI().toURL()));
			throw e;
		} finally {
			request.abort();
			request.releaseConnection();
		}
	}
	
	/**
	 * 
	 * @Title doPost
	 * @param url
	 * @param entities
	 * @param mimeType
	 * @param params
	 * @param headers
	 * @param encoding
	 * @param resultEncoding
	 * @param needCookie
	 * @param keepAlive
	 * @return
	 * @throws Exception String 返回类型
	 * @category post方法请求url，可传流(或传参数)，header，编码方式(请求和响应)，是否签名，是否保持长连接
	 */
	public static String doPost(String url, final String entities, String mimeType, Map<String, String> params, Map<String, String> headers, String encoding, String resultEncoding, boolean needCookie, boolean keepAlive) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		//要么传字符串，要么传参数
		if (StringUtils.isNotBlank(entities) && StringUtils.isNotBlank(mimeType)){
			StringEntity entity = new StringEntity(entities, ContentType.create(mimeType, encoding));
			httpPost.setEntity(entity);
		}else if (params != null && !params.isEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
		}
		//重设或新增header
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		httpPost.setConfig(getRequestConfig(needCookie));
		return doRequest(url, httpPost, resultEncoding, keepAlive);
	}

	/**
	 * 
	 * @Title doGet
	 * @param url
	 * @param params
	 * @param headers
	 * @param encoding
	 * @param resultEncoding
	 * @param needCookie
	 * @param keepAlive
	 * @return
	 * @throws Exception String 返回类型
	 * @category get方法请求url，可传参数，header，编码方式(请求和响应)，是否签名，是否保持长连接
	 */
	public static String doGet(String url, Map<String, String> params, Map<String, String> headers, String encoding, String resultEncoding, boolean needCookie, boolean keepAlive) throws Exception {
		//增加参数
		URIBuilder builder = new URIBuilder(url);
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			builder.setCustomQuery(URLEncodedUtils.format(nameValuePairs, encoding));
		}
		HttpGet httpget = new HttpGet(builder.build());
		//重设或新增header
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpget.setHeader(entry.getKey(), entry.getValue());
			}
		}
		httpget.setConfig(getRequestConfig(needCookie));
		return doRequest(url, httpget, resultEncoding, keepAlive);
	}
}
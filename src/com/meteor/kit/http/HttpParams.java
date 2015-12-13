package com.meteor.kit.http;

import java.util.Map;

public class HttpParams {
	/**
	 * 站点名称
	 */
	private String siteName;
	
	/**
	 * 操作描述
	 */
	private String desc;

	/**
	 * 主机 请求头
	 */
	private String host;

	/**
	 * 请求来源
	 */
	private String reffer;

	/**
	 * 站点地址,目标请求,必须有值
	 */
	private String url;

	/**
	 * 请求信息
	 */
	private Map<String, String> paramMap;
	
	/**
	 * 头部信息
	 */
	private Map<String, String> head;

	/**
	 * 请求方式,默认get
	 */
	private boolean post = false;
	
	/**
	 * cookie
	 */
	private String cookies;

	/**
	 * 请求的数据流信息
	 * 
	 */
	private String streamStr;

	/**
	 * 抓取返回的原始信息
	 */
	private String responseInfo;

	/**
	 * 请求是否成功
	 */
	private boolean requestSuccess = true;

	/**
	 * 请求错误信息
	 */
	private String requestErrorInfo;

	public HttpParams(){}

	public HttpParams(String url,String sitename,String desc){
		this.url=url;
		this.siteName=sitename;
		this.desc=desc;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getReffer() {
		return reffer;
	}

	public void setReffer(String reffer) {
		this.reffer = reffer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public Map<String, String> getHead() {
		return head;
	}

	public void setHead(Map<String, String> head) {
		this.head = head;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public String getStreamStr() {
		return streamStr;
	}

	public void setStreamStr(String streamStr) {
		this.streamStr = streamStr;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public boolean isRequestSuccess() {
		return requestSuccess;
	}

	public void setRequestSuccess(boolean requestSuccess) {
		this.requestSuccess = requestSuccess;
	}

	public String getRequestErrorInfo() {
		return requestErrorInfo;
	}

	public void setRequestErrorInfo(String requestErrorInfo) {
		this.requestErrorInfo = requestErrorInfo;
	}
}

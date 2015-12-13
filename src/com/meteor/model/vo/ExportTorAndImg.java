package com.meteor.model.vo;

import java.util.ArrayList;
import java.util.List;

public class ExportTorAndImg {
	private String filedirName;//文件目录名称
	private List<String> filename;//文件名称
	private List<String> filepath;//文件路径
	public String getFiledirName() {
		return filedirName;
	}
	public void setFiledirName(String filedirName) {
		this.filedirName = filedirName;
	}
	public List<String> getFilename() {
		if(filename==null){
			filename =new ArrayList();
		}
		return filename;
	}
	public void setFilename(List<String> filename) {
		this.filename = filename;
	}
	public List<String> getFilepath() {
		if(filepath==null){
			filepath =new ArrayList();
		}
		return filepath;
	}
	public void setFilepath(List<String> filepath) {
		this.filepath = filepath;
	}
	
}

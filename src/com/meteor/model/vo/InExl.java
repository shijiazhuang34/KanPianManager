/**
 * 
 */
package com.meteor.model.vo;

/**
 * @author justlikemaki
 *
 */
public class InExl {
	private String parentDirMc;//父目录名称
	private String pparentDirMc;//上上级目录名称
	private String[] filePath;//当前文件绝对路径
	private String[] filename;//当前文件名称
	private String type;
	private String isdown;
	private String typeDirMc;
	
	public String getTypeDirMc() {
		return typeDirMc;
	}
	public void setTypeDirMc(String typeDirMc) {
		this.typeDirMc = typeDirMc;
	}
	public String getIsdown() {
		return isdown;
	}
	public void setIsdown(String isdown) {
		this.isdown = isdown;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPparentDirMc() {
		return pparentDirMc;
	}
	public void setPparentDirMc(String pparentDirMc) {
		this.pparentDirMc = pparentDirMc;
	}
	public String getParentDirMc() {
		return parentDirMc;
	}
	public void setParentDirMc(String parentDirMc) {
		this.parentDirMc = parentDirMc;
	}
	public String[] getFilePath() {
		return filePath;
	}
	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}
	public String[] getFilename() {
		return filename;
	}
	public void setFilename(String[] filename) {
		this.filename = filename;
	}

}

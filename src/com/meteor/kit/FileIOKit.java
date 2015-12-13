package com.meteor.kit;

import com.meteor.model.vo.ExportTorAndImg;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOKit {
	public static void main(String[] args) {
		removebyName("D:\\DownloadDir\\BaiduYunDownload\\M067-086 - 副本","AISS",1);
		System.out.println("well done!");
	}
	/**
	 * 
	 * @param readPath
	 * @param name
	 * @param type 文件夹1，文件0
	 */
	public static void removebyName(String readPath,String name,int type){
		File dir = new File(readPath); 
        File[] files = dir.listFiles(); 

        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
//            	String dirname=files[i].getName();
//            	if(dirname.contains(" ")){
//            		files[i].renameTo(new File(readPath+"\\"+dirname.replace("Vol.Vol", "Vol")));
//            		//files[i].renameTo(new File(readPath+"\\"+dirname.substring(0, 11)+dirname.substring(22)));
//            	}
            	if(type==1&&files[i].getName().indexOf(name)>-1){
            		deleteDir(files[i]);
            	}else{
            		removebyName(files[i].getAbsolutePath(),name,1); 
            	}
            } else {   
            	if(type==0&&files[i].getName().indexOf(name)>-1){
            		files[i].delete();
            	}
            } 
        }
	}
	
	public static void deleteDir(File dir){
		  if(dir.isDirectory()){
		   File[] files = dir.listFiles();
		   for(int i=0; i<files.length; i++) {
		    deleteDir(files[i]);
		   }
		  }
		  dir.delete();
	}
	
	public static void exportFiles(String readPath,String writePath) throws IOException{
			List<ExportTorAndImg> list=new ArrayList();
			List<ExportTorAndImg> newlist=readFileList(list,readPath);
			for(int i=0;i<newlist.size();i++){
				for(int j=0;j<newlist.get(i).getFilename().size();j++){
					String thename=newlist.get(i).getFilename().get(j);
					String thepath=newlist.get(i).getFilepath().get(j);
					String rewritePath=writePath+"/"+newlist.get(i).getFiledirName()+"/"+thename;
					
					File f=new File(rewritePath);
					File src=new File(thepath);
					FileUtils.copyFile(src, f);
				}
			}
	}
	
	public static List<ExportTorAndImg> readFileList(List<ExportTorAndImg> list,String strPath) {
        File dir = new File(strPath); 
        File[] files = dir.listFiles(); 
        if (files == null){ 
            return list;
        }
        String parentName=files[0].getParentFile().getName();
        ExportTorAndImg ti=new ExportTorAndImg();
        ti.setFiledirName(parentName);
        for (int i = 0; i < files.length; i++) { 
            if (files[i].isDirectory()) { 
            	readFileList(list,files[i].getAbsolutePath()); 
            } else {   
            	if(isTorAndImg(files[i].getAbsolutePath().toLowerCase())){
	            	String thisName=files[i].getName();            	
	                String strFilePath = files[i].getAbsolutePath();
	                ti.getFilename().add(thisName);
	                ti.getFilepath().add(strFilePath);
            	}
            } 
        }
        list.add(ti);
		return list; 
	} 
	
	public static boolean isTorAndImg(String path){
		if((path.indexOf(".torrent")>-1)||(path.indexOf(".jpg")>-1)||(path.indexOf(".jpeg")>-1)||(path.indexOf(".png")>-1)){
			return true;
		}else{
			return false;
		}
	}
}

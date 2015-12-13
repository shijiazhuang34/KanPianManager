package com.meteor.kit;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * JsonUtil
 * 
 * @author lishanliang
 * 
 */
public class JsonKit {
	private static Gson gson;
	static{
		GsonBuilder builder = new GsonBuilder();
		//builder.setPrettyPrinting();//数据格式美化
		builder.excludeFieldsWithModifiers(Modifier.PROTECTED);//不转换保护类型的属性
		builder.disableHtmlEscaping();//不允许html转意字符串
		gson = builder.create();
	}
	
	private static Gson begson(){		
		return gson;
	}
	
	public static String toJsonPretty(Object bean){
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();//数据格式美化
		builder.excludeFieldsWithModifiers(Modifier.PROTECTED);//不转换保护类型的属性
		builder.disableHtmlEscaping();//不允许html转意字符串
		gson = builder.create();
		return gson.toJson(bean);
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:23:45
	 * @Title bean2JSON
	 * @param bean
	 * @return String 返回类型
	 * @category 对象转换为JSON并用gzip压缩
	 */
	public static String bean2JSON(Object bean) {
		Gson gson=begson();
		return gson.toJson(bean);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:24:21
	 * @Title json2Bean
	 * @param json
	 * @param clazz
	 * @return T 返回类型
	 * @category JSON转换为对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2Bean(String json, Class<T> clazz) {
		Gson gson = begson();
		return gson.fromJson(json, clazz);
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:24:48
	 * @Title json2Map
	 * @param json
	 * @return Map<String,String> 返回类型
	 * @category JSON转换为Map
	 */
	@SuppressWarnings("serial")
	public static Map<String, String> json2Map(String json){
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();  
		return gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:25:07
	 * @Title map2JSON
	 * @param map
	 * @return String 返回类型
	 * @category Map转换为JSON
	 */
	public static String map2JSON(Map<String, String> map) {
		Gson gson=begson();
		return gson.toJson(map);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:25:44
	 * @Title listToJson
	 * @param list
	 * @param allcount
	 * @param allpage
	 * @return String 返回类型
	 * @category List转换为JSON
	 */
	public static String listToJson(List list,int allcount,int allpage) {
		if (list == null || list.size() < 1) {
			return "{\"total\":" + 0 + ",\"rows\":[{}]}";
		}
		Gson gson=begson();
		String json=gson.toJson(list);
		String a="";
		if(allcount>0 && allpage>0){
			a = "{\"allcount\":" + allcount + ",\"allpage\":" + allpage + ",\"total\":" + list.size() + ",\"rows\":" + json + "}";
		}else{
		    a = "{\"total\":" + list.size() + ",\"rows\":" + json + "}";
		}
		return a;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午10:25:58
	 * @Title json2List
	 * @param jsonlist
	 * @param typeOfT
	 * @return T 返回类型
	 * @category JSON转换为List
	 * 示例：new TypeToken<List<String>>(){}.getType()
	 */
	public static <T> T json2List(String jsonlist,Type typeOfT){
		Gson gson = begson();
		return gson.fromJson(jsonlist, typeOfT);		
	}

	public static List<String> json2List(String jsonlist) {
		List<String> list = JsonKit.json2List(jsonlist, new TypeToken<List<String>>() {
		}.getType());
		return list;
	}
}

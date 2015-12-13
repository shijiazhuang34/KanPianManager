package com.meteor.kit;

import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * @ClassName BeanUtil
 * @author Meteor
 * @date 2015年8月8日 下午10:49:50
 * @category 用于复制两个bean的值
 */
public class BeanKit {
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月8日 下午10:50:21
	 * @Title copyTo
	 * @param source 来源list
	 * @param destinationClass 目的bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws InstantiationException List<E> 返回类型
	 * @category 复制list
	 */
	public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) throws Exception{
	    if (source.size()==0) return Collections.emptyList();  
	    List<E> res = new ArrayList<E>(source.size());  
	    for (Object o : source) {  
	        E e = destinationClass.newInstance();  
	        copyProperties(o, e);  
	        res.add(e);  
	    }  
	    return res;  
	}

	public static <E> List<E> copyRec(List<Record> source, Class<E> destinationClass) throws Exception{
		if (source.size()==0) return Collections.emptyList();
		List<E> res = new ArrayList<E>(source.size());
		for (Record o : source) {
			E e = destinationClass.newInstance();
			copyProperties(o.getColumns(), e);
			res.add(e);
		}
		return res;
	}


	private static void copyProperties(Object source,Object target) throws Exception {
		BeanUtils.copyProperties(target,source);
	}
}

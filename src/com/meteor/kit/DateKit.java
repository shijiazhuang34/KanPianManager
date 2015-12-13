package com.meteor.kit;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;


/**
 * 
 * @ClassName DateUtil
 * @author Meteor
 * @date 2015年8月10日 下午2:51:29
 * @category 日期工具类
 */
public class DateKit {

	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:44:55
	 * @Title Date2Str
	 * @param d
	 * @param format
	 * @return String 返回类型
	 * @category 日期转字符串 sqldate
	 */
	public static String Date2Str(Date d, String format) {
		if (null == d) {
			// d = new Date();
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		java.sql.Date sDate = new java.sql.Date(d.getTime());
		String strDate = df.format(sDate);
		return strDate;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:55:28
	 * @Title dateToString
	 * @param date
	 * @param pattern
	 * @return String 返回类型
	 * @category 日期转字符串
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:23:33
	 * @Title dateToStr
	 * @param dateDate
	 * @return String 返回类型
	 * @category 将短时间格式时间转换为字符串 yyyy-MM-dd
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:29:20
	 * @Title dateToStrLong
	 * @param dateDate
	 * @return String 返回类型
	 * @category 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToStrLong(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:14:58
	 * @Title strToDate 
	 * @param strDate  yyyy-MM-dd
	 * @return Date 返回类型
	 * @category 将短时间格式字符串转换为时间
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:27:47
	 * @Title strToDateLong
	 * @param strDate	yyyy-MM-dd HH:mm:ss
	 * @return Date 返回类型  
	 * @category 将长时间格式字符串转换为时间   
	 */
	public static Date strToDateLong(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:29:22
	 * @Title strToDateLong
	 * @param strDate yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @return Date 返回类型
	 * @category  将长时间格式字符串转换为时间
	 */
	public static Date strToDateLong(String strDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:03:18
	 * @Title getWeekXq
	 * @param sdate yyyy-MM-dd HH:mm:ss
	 * @return String 返回类型
	 * @category 返回日期的星期
	 */
	public static String getWeekXq(String sdate) {
		int w = getWeekNum(sdate);
		String s = "";
		switch (w) {
		case 0:
			s = "星期天";
			break;
		case 1:
			s = "星期一";
			break;
		case 2:
			s = "星期二";
			break;
		case 3:
			s = "星期三";
			break;
		case 4:
			s = "星期四";
			break;
		case 5:
			s = "星期五";
			break;
		case 6:
			s = "星期六";
			break;

		}
		return s;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:06:10
	 * @Title getWeekZhou
	 * @param sdate yyyy-MM-dd HH:mm:ss
	 * @return String 返回类型
	 * @category 返回日期的周数
	 */
	public static String getWeekZhou(String sdate) {
		int w = getWeekNum(sdate);
		String s = "";
		switch (w) {
		case 0:
			s = "周日";
			break;
		case 1:
			s = "周一";
			break;
		case 2:
			s = "周二";
			break;
		case 3:
			s = "周三";
			break;
		case 4:
			s = "周四";
			break;
		case 5:
			s = "周五";
			break;
		case 6:
			s = "周六";
			break;

		}
		return s;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:07:08
	 * @Title getWeekNum
	 * @param sdate	yyyy-MM-dd HH:mm:ss
	 * @return int 返回类型
	 * @category 返回数字星期 星期天是0 星期一是1
	 */
	public static int getWeekNum(String sdate) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(strToDate(sdate));
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:09:34
	 * @Title getWeekEn
	 * @param sdate
	 * @return String 返回类型
	 * @category 返回英文星期
	 */
	public static String getWeekEn(String sdate) {
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("EEE", Locale.ENGLISH).format(c.getTime());
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:14:34
	 * @Title getNextMonth
	 * @param dates
	 * @param count
	 * @return String 返回类型
	 * @category 取得指定日期的下一个月
	 */
	public static String getNextMonth(String dates, int count) {
		Date date = strToDate(dates);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, count);
		return dateToStr(gc.getTime());
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:08:41
	 * @Title getFutureDay
	 * @param appDate 指定日期
	 * @param format  指定日期格式yyyy-MM-dd
	 * @param days    指定天数
	 * @return String 返回类型
	 * @category 用于返回指定日期格式的日期增加指定天数的日期
	 */
	public static String getFutureDay(String appDate, String format, int days) {
		String future = "";
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date date = simpleDateFormat.parse(appDate);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			date = calendar.getTime();
			future = simpleDateFormat.format(date);
		} catch (Exception e) {
		}
		return future;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:09:43
	 * @Title getNextDay
	 * @param nowdate
	 * @param delay
	 * @return String 返回类型  yyyy-MM-dd
	 * @category 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String mdate = "";
		Date d = null;
		if (nowdate == null || "".equals(nowdate)) {
			d = new Date();
		} else {
			d = strToDate(nowdate);
		}

		long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
		d.setTime(myTime * 1000);
		mdate = format.format(d);
		return mdate;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:31:27
	 * @Title getPreTime
	 * @param sj1
	 * @param jj
	 * @return String 返回类型
	 * @category 时间前推或后推分钟,其中JJ表示分钟
	 */
	public static String getNextTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:44:03
	 * @Title getNextTimesec
	 * @param sj1
	 * @param jj
	 * @return String 返回类型
	 * @category 时间前推或后推秒钟,其中JJ表示秒钟.
	 */
	public static String getNextTimesec(String sj1, int jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + jj;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}
	
	

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:29:58
	 * @Title getCurrTime
	 * @return String 返回类型
	 * @category 获取当前时间 yyyyMMddHHmmss
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:31:20
	 * @Title getStrDateFormat
	 * @param strDate
	 * @return String 返回类型 yyyy年MM月dd日
	 * @category 转为中文格式显示
	 */
	public static String getStrDateFormat(String strDate) {
		Date date = strToDateLong(strDate);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:42:30
	 * @Title getTwoHour
	 * @param st1
	 * @param st2
	 * @return String 返回类型
	 * @category 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:45:07
	 * @Title getMinutesFromTwoHour
	 * @param st1
	 * @param st2
	 * @return int 返回类型
	 * @category  二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回分钟 VIP
	 */
	public static int getMinutesFromTwoHour(String st1, String st2) {
		String[] kk = st1.split(":");
		String[] jj = st2.split(":");
		if(kk.length!=2||jj.length!=2){
			return 0;
		}
		int y = NumberUtils.toInt(kk[0],0)*60 + NumberUtils.toInt(kk[1],0) ;
		int u = NumberUtils.toInt(jj[0],0)*60 + NumberUtils.toInt(jj[1],0) ;
		return u - y;
			
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:32:42
	 * @Title DateDiff
	 * @param sDate1 yyyy-MM-dd 最新的时间
	 * @param sDate2 yyyy-MM-dd 相比较的时间
	 * @return int 返回类型
	 * @category 相差天数
	 */
	public static int DateDiff(String sDate1, String sDate2) { // sDate1和sDate2是2002-12-18格式
		String[] aDate;
		Integer iDays;
		aDate = sDate1.split("-");
		Integer jsrq = Integer.valueOf(aDate[0]) * 31 * 24 + Integer.valueOf(aDate[1]) * 31 + Integer.valueOf(aDate[2]); // 转换为12-18-2002格式
		aDate = sDate2.split("-");
		Integer ksrq = Integer.valueOf(aDate[0]) * 31 * 24 + Integer.valueOf(aDate[1]) * 31 + Integer.valueOf(aDate[2]);
		iDays = jsrq - ksrq; // 把相差天数
		return iDays;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:06:16
	 * @Title twoDayWeeks
	 * @param ksrq
	 * @param jsrq
	 * @return int 返回类型
	 * @category 2个日期间所跨的周数
	 */
	public static int twoDayWeeks(String ksrq, String jsrq) {
		int allDate = NumberUtils.toInt(getTwoDay(jsrq, ksrq)) + 1;
		String sd = ksrq;
		int count = 1;
		for (int i = 1; i < allDate; i++) {
			String d = getNextDay(ksrq, i + "");
			if (!isSameWeekDates(strToDate(sd), strToDate(d))) {
				count++;
			}
			sd = d;
		}
		return count;
	}
	
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:21:25
	 * @Title monthsBetween
	 * @param pLatterStr
	 * @param pFormerStr
	 * @return int 返回类型
	 * @category 给定两个时间相差的月数,String版
	 */
	public static int monthsBetween(String pLatterStr, String pFormerStr) {
		GregorianCalendar vFormer = parse2Cal(pFormerStr);
		GregorianCalendar vLatter = parse2Cal(pLatterStr);
		return monthsBetween(vLatter, vFormer);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:26:59
	 * @Title getTwoDay
	 * @param sj1
	 * @param sj2
	 * @return String 返回类型
	 * @category 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		if (sj1 == null || sj1.equals(""))
			return "";
		if (sj2 == null || sj2.equals(""))
			return "";
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:27:20
	 * @Title getTwoMil
	 * @param sj1
	 * @param sj2
	 * @return String 返回类型
	 * @category 得到二个日期间的间隔分钟
	 */
	public static String getTwoMil(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:27:31
	 * @Title getTwoSecond
	 * @param sj1
	 * @param sj2
	 * @return String 返回类型
	 * @category 得到二个日期间的间隔秒
	 */
	public static String getTwoSecond(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:33:50
	 * @Title getEndDateOfMonth
	 * @param dat
	 * @return String 返回类型
	 * @category 获取一个月的最后一天
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 7);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "-31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "-30";
		} else {
			if (isLeapYear(dat)) {
				str += "-29";
			} else {
				str += "-28";
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:34:25
	 * @Title getFirstDateOfMonth
	 * @param dat
	 * @return String 返回类型
	 * @category 返回指定日期的第一天 日期为yyyy-MM-dd格式
	 */
	public static String getFirstDateOfMonth(String dat){
		if(StringUtils.isBlank(dat)){
			return null;
		}
		if(dat.length() < 7){
			return null;
		}
		String str = dat.substring(0, 7);
		return str + "-01";
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:35:45
	 * @Title getTheDateOfMonth
	 * @param dat
	 * @param day
	 * @return String 返回类型
	 * @category 返回指定日期所在月份的某天，包括大小月判断，如果指定月的天数小于day，则返回最后一天 日期为yyyy-MM-dd格式
	 */
	public static String getTheDateOfMonth(String dat,int day){
		if(StringUtils.isBlank(dat)){
			return null;
		}
		if(dat.length() < 7){
			return null;
		}
		String str = dat.substring(0, 7);
		String month = dat.substring(5, 7);
		int maxDay = 31;
		int mon = Integer.parseInt(month);
		if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			maxDay = 30;
		} else {
			if(mon==2){
				if (isLeapYear(dat)) {
					maxDay = 29;
				} else {
					maxDay = 28;
				}
			}
		}
		if(day < 1){
			day = 1;
		}
		if(day > maxDay){
			day = maxDay;
		}
		if(String.valueOf(day).length() == 1){
			return str + "-0" + day;
		}else{
			return str + "-" + day;
		}
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:42:33
	 * @Title isLeapYear
	 * @param ddate
	 * @return boolean 返回类型
	 * @category 判断是否润年
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:30:23
	 * @Title getUserDate
	 * @param sformat
	 * @return String 返回类型
	 * @category  根据用户传入的时间表示格式，返回当前时间的格式 如yyyyMMdd，注意字母y不能大写。
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:34:34
	 * @Title getFormatDate
	 * @param str
	 * @param str
	 * @return String 返回类型
	 * @category 传入的时间和时间表示格式，将时间转换为指定格式 如果是yyyyMMdd，注意字母y不能大写。
	 */
	public static String getFormatDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = strToDateLong(str);
		String dateString = formatter.format(date);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:34:34
	 * @Title getFormatDate
	 * @param sformat
	 * @param str
	 * @return String 返回类型
	 * @category 传入的时间和时间表示格式，将时间转换为指定格式 如果是yyyyMMdd，注意字母y不能大写。
	 */
	public static String getFormatDate(String sformat, String  str) {
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		Date date = strToDateLong(str);
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String fmtDateString(String date) {
		try {
			String sformat = "dd/MM/yyyy HH:mm:ss";
			String sformat2 = "yyyy-MM-dd HH:mm:ss";
			Date zhedate = new SimpleDateFormat(sformat).parse(date);
			SimpleDateFormat sf = new SimpleDateFormat(sformat2);
			date=sf.format(zhedate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:48:14
	 * @Title fmtDate
	 * @param date
	 * @return String 返回类型
	 * @category 把一位月日时分秒格式化为2位
	 */
	public static String fmtDate(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		}
		String str = "";
		String[] yyhh = date.split(" ");
		if (yyhh.length > 0) {// yyyy-mm-dd
			String[] yymmddsz = yyhh[0].split("-");
			for (String s : yymmddsz) {
				if (s.length() == 1) {
					s = "0" + s;
				}
				if ("".equals(str)) {
					str = s;
				} else {
					str += "-" + s;
				}
			}
		}

		if (yyhh.length > 1) {// hh:mm:ss
			str += " ";
			String[] hhmmsssz = yyhh[1].split(":");
			for (int i = 0; i < hhmmsssz.length; i++) {
				String s = hhmmsssz[i];
				if (s.length() == 1) {
					s = "0" + s;
				}
				if (i == 0) {
					str += s;
				} else {
					str += ":" + s;
				}
			}
		}
		return str;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:50:09
	 * @Title startDateTime
	 * @param date
	 * @return Date 返回类型
	 * @category 补齐开始日期到秒.使其格式为YYYY-MM-DD HH24:MI:SS
	 */
	public static Date startDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:50:22
	 * @Title endDateTime
	 * @param date
	 * @return Date 返回类型
	 * @category 补齐结束日期到秒.使其格式为YYYY-MM-DD HH24:MI:SS
	 */
	public static Date endDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午11:16:12
	 * @Title KsrqString
	 * @param ksrq
	 * @return String 返回类型
	 * @category 补齐开始日期到秒.使其格式为YYYY-MM-DD HH24:MI:SS
	 */
	public static String KsrqString(String ksrq) {
		String newKsrq = "";
		// 形参字符串长度
		Integer Stringlength = ksrq.trim().length();
		switch (Stringlength) {
		case 10:
			newKsrq = ksrq + " 00:00:00";
			break;
		case 13:
			newKsrq = ksrq + ":00:00";
			break;
		case 16:
			newKsrq = ksrq + ":00";
			break;
		default:
			newKsrq = ksrq;
			break;
		}
		return newKsrq;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 上午11:16:50
	 * @Title JsrqString
	 * @param jsrq
	 * @return String 返回类型
	 * @category 补齐结束日期到秒.使其格式为YYYY-MM-DD HH24:MI:SS
	 */
	public static String JsrqString(String jsrq) {
		String newJsrq = "";
		// 形参字符串长度
		Integer Stringlength = jsrq.trim().length();
		switch (Stringlength) {
		case 10:
			newJsrq = jsrq + " 23:59:59";
			break;
		case 13:
			newJsrq = jsrq + ":59:59";
			break;
		case 16:
			newJsrq = jsrq + ":59";
			break;
		default:
			newJsrq = jsrq;
			break;
		}
		return newJsrq;
	}

	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:52:55
	 * @Title dateTime
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return Date 返回类型
	 * @category 补齐日期的到秒
	 */
	public static Date dateTime(Date date, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return new Timestamp(calendar.getTime().getTime());
	}

	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:53:48
	 * @Title convert
	 * @param date
	 * @param isLDate true时返回日期格式是带有时分秒的
	 * @return Date 返回类型
	 * @category 返回sqlDate日期格式
	 */
	public static Date convert(Date date, boolean isLDate) {

		if (date == null) {
			return null;
		}
		if (isLDate) {
			return new Timestamp(date.getTime());
		} else {
			return new java.sql.Date(date.getTime());
		}
	}

	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:53:59
	 * @Title convert
	 * @param strdate
	 * @param isLDate  true时返回日期格式是带有时分秒的
	 * @return Date 返回类型
	 * @category 返回sqlDate日期格式
	 */
	public static Date convert(String strdate, boolean isLDate) {
		Date date = strToDate(strdate);
		return convert(date, isLDate);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:58:43
	 * @Title isDate
	 * @param strDate
	 * @return boolean 返回类型
	 * @category 验证日期格式 yyyy-mm-dd
	 */
	public static boolean isDate(String strDate) {
		return strDate.matches("((((19|20)\\d{2})-(0?[13578]|1[02])-"
				+ "(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-"
				+ "(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-9])))");

	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:59:04
	 * @Title isDatetime
	 * @param datetime
	 * @return boolean 返回类型
	 * @category 验证日期时间格式  yyyy-mm-dd hh:mm:ss
	 */
	public static boolean isDatetime(String datetime) {
		return datetime.matches("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?"
				+ "((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|"
				+ "(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]"
				+ "((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])"
				+ "((\\s)|(\\:([0-5]?[0-9])))))$");
	}
	
	/**
	 * 
	 * @param sql
	 *            sql
	 * @param params
	 *            params
	 * @param start
	 *            start
	 * @param count
	 *            count
	 * @return String String
	 */
	@SuppressWarnings("unchecked")
	public static String debugSql(String sql, Collection params, int start, int count) {
		if (params != null) {
			Iterator iter = params.iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				String p = "";
				if (key instanceof String) {
					p = "'" + key.toString() + "'";
				} else if (key instanceof Date) {
					p = "to_date('" + key.toString() + "','yyyy-mm-dd hh24:mi:ss')";
				} else if (key instanceof Timestamp) {
					p = "to_date('" + key.toString() + "','yyyy-mm-dd hh24:mi:ss')";
				} else {
					p = key == null ? "null" : key.toString();
				}
				sql = StringUtils.replaceOnce(sql, "?", p);
			}
		}
		if (count > 0) {
			StringBuffer sb = new StringBuffer("select b_table.* from (select a_table.*,rownum as linenum from");
			sb.append("(").append(sql).append(" ) a_table where rownum <= ").append(start + count)
					.append(") b_table where linenum >").append(start);
			sql = sb.toString();
		}

		System.out.println(sql);
		return sql;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:01:27
	 * @Title getMilForDate
	 * @param date
	 * @return String 返回类型
	 * @category 得到指定时间和当前时间的间隔分钟
	 */
	public static String getMilForDate(Date date) {
		long day = 0;
		try {
			Date dt = new Date();
			day = (date.getTime() - dt.getTime()) / (60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:03:15
	 * @Title getMilForDate
	 * @param date
	 * @return String 返回类型
	 * @category 得到指定时间和当前时间的间隔分钟
	 */
	public static String getMilForDate(String date) {
		return getMilForDate(strToDateLong(date));
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:05:57
	 * @Title isRightDate
	 * @param date
	 * @param format
	 * @return boolean 返回类型
	 * @category 判断一个给定的日期字符串是否符合给定的格式，并且是有效的日期
	 */
	public static boolean isRightDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if (sdf.format(sdf.parse(date)).equalsIgnoreCase(date))
				return true;
			else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:15:11
	 * @Title getZeroByDate
	 * @param date
	 * @return Date 返回类型
	 * @category 取传入日期的零时零分零秒
	 */
	public static Date getZeroByDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:16:57
	 * @Title timeToDate
	 * @param time
	 * @return Date 返回类型
	 * @category time为date类型的日期
	 */
	public static Date timeToDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String d = format.format(time);

		try {
			Date date = format.parse(d);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:20:32
	 * @Title getMondayDate
	 * @param date
	 * @return String 返回类型
	 * @category 获得传入日期的本周星期一的日期
	 */
	public static String getMondayDate(String date) {
		String monday = "";
		String strFormat = "yyyy-MM-dd";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat, Locale.CHINA);
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setTimeInMillis(strToDateLong(date, strFormat).getTime());
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			monday = sdf.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monday;
	}


	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:13:33
	 * @Title date2Calendar
	 * @param date
	 * @return Calendar 返回类型
	 * @category Date转Calendar
	 */
	public static Calendar date2Calendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	
	private static int monthsBetween(GregorianCalendar pLatter, GregorianCalendar pFormer) {
		GregorianCalendar vFormer = pFormer, vLatter = pLatter;
		boolean vPositive = true;
		if (pFormer.before(pLatter)) {
			vFormer = pFormer;
			vLatter = pLatter;
		} else {
			vFormer = pLatter;
			vLatter = pFormer;
			vPositive = false;
		}

		int vCounter = 0;
		while (vFormer.get(Calendar.YEAR) != vLatter.get(Calendar.YEAR)
				|| vFormer.get(Calendar.MONTH) != vLatter.get(Calendar.MONTH)) {
			vFormer.add(Calendar.MONTH, 1);
			vCounter++;
		}
		if (vPositive)
			return vCounter;
		else
			return -vCounter;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:22:34
	 * @Title parse2Cal
	 * @param pDateStr
	 * @return GregorianCalendar 返回类型
	 * @category 将字符串格式的日期转换为Calender
	 */
	public static GregorianCalendar parse2Cal(String pDateStr) {
		StringTokenizer sToken;
		sToken = new StringTokenizer(pDateStr, "-");
		int vYear = Integer.parseInt(sToken.nextToken());
		// GregorianCalendar的月份是从0开始算起的，变态！！
		int vMonth = Integer.parseInt(sToken.nextToken()) - 1;
		int vDayOfMonth = Integer.parseInt(sToken.nextToken());
		return new GregorianCalendar(vYear, vMonth, vDayOfMonth);
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:07:24
	 * @Title getDateByCalendar
	 * @param calendar
	 * @return String 返回类型
	 * @category Calendar 转换为字符日期
	 */
	public static String getDateByCalendar(Calendar calendar) {
		String future = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = calendar.getTime();
			future = format.format(date);
		} catch (Exception e) {
		}
		return future;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:33:12
	 * @Title getTime
	 * @return String 返回类型
	 * @category 得到现在分钟
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}
	
	/** 
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午1:55:47
	 * @Title getHour
	 * @return String 返回类型
	 * @category 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:12:21
	 * @Title getNowTime
	 * @return String 返回类型 [HH:mm]
	 * @category 获取当前时间
	 */
	public static String getNowTime() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:07:28
	 * @Title getStringDate
	 * @return String 返回类型 yyyy-MM-dd HH:mm:ss
	 * @category 获取现在时间
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:24:33
	 * @Title getStringDateShort
	 * @return String 返回类型  yyyy-MM-dd
	 * @category 获取现在时间
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:26:29
	 * @Title getStringDateShortmm
	 * @return String 返回类型 返回短时间字符串格式yyyy-MM-dd HHmm
	 * @category 获取现在时间
	 */
	public static String getStringDateShortmm() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:31:51
	 * @Title getStringToday
	 * @return String 返回类型  字符串 yyyyMMdd HHmmss
	 * @category 得到现在时间
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:31:51
	 * @Title getStringToday
	 * @return String 返回类型  字符串 yyyyMMddHHmmss
	 * @category 得到现在时间
	 */
	public static String getStringTodayA() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月12日 下午11:31:17
	 * @Title getStringTodayB
	 * @return String 返回类型
	 * @category 得到现在时间
	 */
	public static String getStringTodayB() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:56:32
	 * @Title getNowDate
	 * @return Date 返回类型
	 * @category 获取现在时间 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return yyyy-MM-dd
	 */
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:56:47
	 * @Title getNowDateShort
	 * @return Date 返回类型
	 * @category 获取现在时间 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(0);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午10:57:40
	 * @Title getNowDateLong
	 * @return Date 返回类型
	 * @category 得到当前日期00:00:00 返回格式 yyyy-MM-dd 00:00:00
	 */
	public static Date getNowDateLong() {
		String currentDate = getStringDateShort();
		ParsePosition pos = new ParsePosition(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(currentDate + " 00:00:00", pos);
	}



	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:28:28
	 * @Title getTimeShort
	 * @return String 返回类型
	 * @category 获取时间 小时:分;秒 HH:mm:ss
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:29:46
	 * @Title getNow
	 * @return Date 返回类型
	 * @category 得到现在时间
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}





	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:47:40
	 * @Title getEDate
	 * @param str
	 * @return String 返回类型
	 * @category 返回美国时间格式 26 Apr 2006
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2]+" "+ k[1]+" "+ k[5].substring(0, 4);
	}


	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:48:08
	 * @Title isSameWeekDates
	 * @param date1
	 * @param date2
	 * @return boolean 返回类型
	 * @category 判断二个时间是否在同一个周
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:48:08
	 * @Title isSameWeekDates
	 * @param strdate1
	 * @param strdate2
	 * @return boolean 返回类型
	 * @category 判断二个时间是否在同一个周
	 */
	public static boolean isSameWeekDates(String strdate1, String strdate2) {
		Date date1=strToDate(strdate1);
		Date date2=strToDate(strdate2);
		return isSameWeekDates(date1, date2);
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:51:01
	 * @Title getSeqWeek
	 * @return String 返回类型
	 * @category 产生周序列即得到当前时间所在的年度是第几周
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:52:14
	 * @Title getWeek
	 * @param sdate
	 * @param num
	 * @return String 返回类型
	 * @category 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:52:55
	 * @Title getWeek
	 * @param sdate
	 * @return String 返回类型
	 * @category 根据一个日期，返回是星期几的字符串
	 */
	public static String getWeek(String sdate) {
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:53:53
	 * @Title getWeekz1
	 * @param sdate
	 * @return String 返回类型
	 * @category 返回日期的周几
	 */
	public static String getWeekz1(String sdate) {
		int w = getWeekNum(sdate);
		String s = "";
		switch (w) {
		case 0:
			s = "日";
			break;
		case 1:
			s = "一";
			break;
		case 2:
			s = "二";
			break;
		case 3:
			s = "三";
			break;
		case 4:
			s = "四";
			break;
		case 5:
			s = "五";
			break;
		case 6:
			s = "六";
			break;

		}
		return s;
	}


	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:55:07
	 * @Title getNowMonth
	 * @param sdate
	 * @return String 返回类型
	 * @category 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间 此函数返回该日历第一行星期日所在的日期
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";

		// 得到这个月的1号是星期几
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:55:26
	 * @Title formatLongToTimeStr
	 * @param l
	 * @return String 返回类型
	 * @category  毫秒转为时分秒
	 */
	public static String formatLongToTimeStr(Long l) {
		long hour = 0;
		long minute = 0;
		long second = 0;

		second = l / 1000;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}

		if (hour == 0 && minute == 0) {
			return second + "秒";
		}
		String s = "";
		if (hour != 0) {
			s = hour + "时";
		}
		return (s + minute + "分" + second + "秒");
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:55:45
	 * @Title formatminuteToTimeStr
	 * @param l
	 * @return String 返回类型
	 * @category 分转为时分秒
	 */
	public static String formatminuteToTimeStr(Long l) {
		long hour = 0;
		long minute = 0;

		if (l > 60) {
			hour = l / 60;
			minute = l % 60;
		} else {
			minute = l;
		}

		String s = "";
		if (hour != 0) {
			s = hour + "时";
		}
		return (s + minute + "分");
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:58:25
	 * @Title getWeekLastDate
	 * @param newdate
	 * @return String 返回类型
	 * @category 获得本周的最后一天
	 */
	@SuppressWarnings("static-access")
	public static String getWeekLastDate(String newdate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calBegin = new GregorianCalendar();
		calBegin.setTime(strToDate(newdate));
		calBegin.add(calBegin.DATE, (7 - calBegin.get(calBegin.DAY_OF_WEEK)) + 1);
		String WeekLastDate = format.format(calBegin.getTime());
		return WeekLastDate;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:59:03
	 * @Title getEndWeekDate
	 * @return String 返回类型
	 * @category 获得下周的最后一天
	 */
	@SuppressWarnings("static-access")
	public static String getEndWeekDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calBegin = new GregorianCalendar();
		calBegin.setTime(new Date());
		calBegin.add(calBegin.DATE, (7 - calBegin.get(calBegin.DAY_OF_WEEK)) + 8);
		String endWeekDate = format.format(calBegin.getTime());

		return endWeekDate;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午11:59:15
	 * @Title getEndQuarter
	 * @param date
	 * @return String 返回类型
	 * @category 传入一个时间,获得下一季度的最后一天
	 */
	public static String getEndQuarter(String date) {// yyyy-mm-dd
		String yy = date.substring(0, 4);
		String month = date.substring(5, 7);
		String dd = date.substring(8);
		if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
			month = "06";
		} else if ("04".equals(month) || "05".equals(month) || "06".equals(month)) {
			month = "09";
		} else if ("07".equals(month) || "08".equals(month) || "09".equals(month)) {
			month = "12";
		} else if ("10".equals(month) || "11".equals(month) || "12".equals(month)) {
			month = "03";
			Integer year = Integer.parseInt(yy);
			year += 1;
			yy = year.toString();
		}
		String ymd = yy + "-" + month + "-" + dd;

		return getEndDateOfMonth(ymd);
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:00:18
	 * @Title stratTime
	 * @param date
	 * @param unionMinute
	 * @return  String 格式_HH:mm:ss.0000 _代表空格
	 * @category 取当前的时间偏移时间的时分秒 与endTime成对使用
	 */
	public static String stratTime(Calendar date, int unionMinute) {
		SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss");
		date.add(Calendar.MINUTE, unionMinute);
		return formatter.format(date.getTime()) + ".0000";
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:00:43
	 * @Title endTime
	 * @param date
	 * @return String 格式_HH:mm:ss.999999 _代表空格
	 * @category 取当前的时间的时分秒 与startTime成对使用
	 * 
	 */
	public static String endTime(Calendar date) {
		SimpleDateFormat formatter = new SimpleDateFormat(" HH:mm:ss");
		return formatter.format(date.getTime()) + ".999999";
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:01:10
	 * @Title getMonthDay
	 * @param dat
	 * @return int 返回类型
	 * @category 获得指定月的天数
	 */
	public static int getMonthDay(String dat) {
		String month = dat.substring(5, 7);
		int monthDay = 0;
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			monthDay = 31;
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			monthDay = 30;
		} else {
			if (isLeapYear(dat)) {
				monthDay = 29;
			} else {
				monthDay = 28;
			}
		}
		return monthDay;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:01:22
	 * @Title getMonthWeek
	 * @param year
	 * @param month
	 * @return int 返回类型
	 * @category 得到指定月的周数
	 */
	public static int getMonthWeek(String year, String month) {
		String getFirst = year + "-" + month + "-01";
		int monthDay = getMonthDay(getFirst); // 这个月有几天
		int firstWeek = getWeekNum(getFirst); // 第一天星期几
		int firsWeekDay = 6 - firstWeek + 1; // 第二周开始日期
		int i = firsWeekDay;
		int monthWeek = 1; // 一个月有几周
		while (i < monthDay) {
			i = i + 7;
			monthWeek++;
		}
		return monthWeek;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:01:34
	 * @Title getWeekByDay
	 * @param sdate
	 * @return int 返回类型
	 * @category 根据日期得到是这个月的第几周
	 */
	public static int getWeekByDay(String sdate) {
		String getFirst = getFirstDateOfMonth(sdate);
		int firstWeek = getWeekNum(getFirst); // 第一天星期几
		int firsWeekDay = 7 - firstWeek; // 第一周有几天

		int td = Integer.valueOf(sdate.substring(8, 10)); // 这天是这个月的第几天

		if (td - firsWeekDay <= 0) {
			return 1;
		} else {
			int a = td - firsWeekDay;
			a = a / 7 + (a % 7 == 0 ? 0 : 1);
			return a + 1;
		}
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:01:48
	 * @Title getWeekDat
	 * @param year
	 * @param month
	 * @param week
	 * @return String[] 返回类型
	 * @category 得到指定年月周的日期第一天和最后一天
	 */
	public static String[] getWeekDat(String year, String month, int week) {
		String[] re = new String[2];
		String firstDay = year + "-" + month + "-01";
		int monthDay = getMonthDay(firstDay);
		int firstWeekEnd = 6 - getWeekNum(firstDay) + 1;

		if (week == 1) {
			re[0] = firstDay;
			re[1] = year + "-" + month + "-0" + firstWeekEnd;
		} else {
			int startWeek = firstWeekEnd + 1 + (week - 2) * 7;
			int endWeek = startWeek + 6; // 第几天
			String start = startWeek + "";
			if (start.length() <= 1) {
				start = "0" + start;
			}
			if (endWeek > monthDay) {
				endWeek = monthDay;
			}
			String end = endWeek + "";
			if (end.length() <= 1) {
				end = "0" + end;
			}
			re[0] = year + "-" + month + "-" + start;
			re[1] = year + "-" + month + "-" + end;
		}
		return re;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:35:45
	 * @Title getRandom
	 * @param i 生成的随机数有几位
	 * @return String 返回类型
	 * @category  返回一个随机数
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();

		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:44:10
	 * @Title buildRandom
	 * @param length
	 * @return int 返回类型
	 * @category  取出一个指定长度大小的随机正整数.
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	

	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:09:57
	 * @Title getStrRandom
	 * @param j
	 * @return String 返回类型
	 * @category 获取大写字母随机数
	 */
	public static String getStrRandom(int j){
	   String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
       char[] c = s.toCharArray();   
       Random random = new Random();   
       String ss="";
       for( int i = 0; i < j; i ++) {   
           ss=ss+c[random.nextInt(c.length)];   
       }
	   return ss;
   }
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月9日 下午2:34:09
	 * @Title getUUID
	 * @return String 返回类型
	 * @category 生成随机的且不重复的id（重复几率很小）
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 
	 * @author Meteor
	 * @Cdate 2015年8月10日 上午12:13:51
	 * @Title pageTimeFormat
	 * @param date
	 * @return String 返回类型
	 * @category 格式化web页面显示时间样式
	 */
	public static String pageTimeFormat(String date){
		String timeout="";
		String nowdate=getStringDate();
		int days=Integer.valueOf(getTwoDay(nowdate, date));
		if(days<=6){
			if(days==0){
				timeout=getFormatDate("HH:mm:ss",date);
			}else{
				timeout=days+"天前";
			}
		}else{
			if(days/7<=4 || days<=30){
				timeout=days/7+"周前";
			}else{
				timeout=days/30+"月前";
			}
		}
		return timeout;
	}

	/**
	 * 测试使用，计算方法执行时间
	 */
	private long bgtime=0;
	public void getbgtime(){
		bgtime=new Date().getTime();
	}
	public void getedtime(String name){
		long edtime=new Date().getTime();
		System.out.println(name+"方法执行时间："+(edtime-bgtime)+"毫秒");
		bgtime=0;
	}
	
	public long getedtimenoout(){
		long edtime=new Date().getTime();
		edtime=(edtime-bgtime)/1000+1;
		bgtime=0;
		return edtime;
	}
	
	public static String getTimeOff(long time){
		long c=time;
		SimpleDateFormat format =null;
		Date d=new Date(c);
		c=c/1000;
		if(c<60)
			format = new SimpleDateFormat("s秒");
		else if(c<3600)
			format = new SimpleDateFormat("mm:ss");
		else if(c<86400)
			format = new SimpleDateFormat("HH:mm:ss");
		else
			format = new SimpleDateFormat("d天  HH:mm:ss");
		
		d.setHours(d.getHours()-1);
		format.setTimeZone(TimeZone.getTimeZone("GMT-23"));
		return format.format(d);
	}
	
	public static String getTimeOff(long timeed,long timebg){
		long c=timeed-timebg;
		return getTimeOff(c);
	}

	/**
	 * 得到文件修改时间
	 * @param filepath
	 * @return
	 */
	public static String getRealtime(String filepath){
		File file = new File(filepath);
		//毫秒数
		long modifiedTime = file.lastModified();
		//通过毫秒数构造日期 即可将毫秒数转换为日期
		Date d = new Date(modifiedTime);
		String shorttime=DateKit.Date2Str(d, "yyyy-MM-dd HH:mm:ss");
		//System.out.println(shorttime);
		return shorttime;
	}
}
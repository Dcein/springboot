package dcein.springboot.demo.utils;

import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * @Author: DingCong
 * @Description:  时间日期功能工具类
 * @CreateDate: 2018/7/25 10:39
 * @param:
 */
@Slf4j
public class DateTimeUtil {

	/**
	 * 将两个时间进行比较，如果dateTwo大于或者等于dateOne，则返回true，否则返回false
	 * @param dateOne 被比较时间
	 * @param dateTwo 比较时间
	 * @return
	 */
	public static boolean compareTime(Date dateOne, Date dateTwo){
		Long timestampOne = dateOne.getTime();
		Long timestampTwo = dateTwo.getTime();
		
		if(timestampTwo >= timestampOne){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将两个时间进行比较，判断时间差是否在一定的区间内，如果在，则返回true，否则返回false
	 * 计算公式为：如果intervalStart <= dateTwo - dateOne <= intervalEnd，则返回true，否则返回false
	 * @param dateOne 被比较时间
	 * @param dateTwo 比较时间
	 * @param intervalStart 区间开始值（包含），单位：毫秒
	 * @param intervalEnd 区间结束值（包含），单位：毫秒
	 * @return
	 */
	public static boolean compareTime(Date dateOne, Date dateTwo, Long intervalStart, Long intervalEnd){
		Long timestampOne = dateOne.getTime();
		Long timestampTwo = dateTwo.getTime();
		log.info("比较两个时间戳dateOne为" + timestampOne + "，dateTwo为" + timestampTwo + "，区间为" + intervalStart + "~" + intervalEnd);
		if((timestampTwo - timestampOne) >= intervalStart && (timestampTwo - timestampOne) <= intervalEnd){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将给定的时间字符串，按照转换格式，转换成Date类型时间
	 * @param timeStr 时间字符串
	 * @param pattern 时间字符串的时间格式，如yyyyMMdd
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDate(String timeStr, String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);//小写的mm表示的是分钟  
		return sdf.parse(timeStr);
	}
    /***
     * 根据格式获取String类型的系统当前时间
     */
    public static String getStringDate(String pattern) {
        return getFormatDate(new Date(),pattern) ;
    }
	/**
	 * 获得当前的系统时间
	 * 
	 * @return 当前的系统日期
	 */
	public static Date getCurrentTime()
	{
		return new Date();
	}

	/**
	 * 获得当前的系统日期，不带有时分秒
	 * 
	 * @return 当前的系统日期
	 */
	public static Date getCurrentDate()
	{

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.clear(Calendar.HOUR);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MILLISECOND);

		date = c.getTime();
		return date;
	}

	/**
	 * 根据字符串格式日期，返回Date
	 * 
	 * @param d
	 *            Date
	 * @return 当前的系统日期
	 */
	public static Date getDate(String d)
	{
		Timestamp ts = Timestamp.valueOf(d);
		return ts;
	}

	/**
	 * 输出字符串类型的格式化日期
	 * 
	 * @param dt Date
	 * @param pattern 时间格式
	 * @return sDate
	 */
	public static String getFormatDate(Date dt, String pattern) {
		String sDate;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		sDate = formatter.format(dt);
		return sDate;
	}
	
	/**
	 * 字符串格式转化为日期格式(通用格式)
	 * @param dt
	 * @param frormat
	 * @return
	 */
	public static Date stringToDate(String dt,String frormat) {
		Date dd = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(frormat);
		try {
			dd = simpleDateFormat.parse(dt.trim());
		} catch (Exception e) {
			dd = null;
		}
		return dd;
	}
	
	
	/**
	 * 得到指定日期的月份,格式：yyyy-mm-dd
	 * 
	 * @return String
	 */
	public static String getDateMonth(Date date) {

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy'-'MM'-'dd");
		format1.setLenient(false);
		String dateStr = format1.format(date);
		int begin = dateStr.indexOf('-') + 1;
		int end = dateStr.lastIndexOf('-');
		String month = dateStr.substring(begin, end);
		return month;
	}

	/**
	 * 得到某一天的开始时间，精确到毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 某一天的0时0分0秒0毫秒的那个Date
	 */
	public static Date beginOfDay(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		date = c.getTime();
		return date;
	}

	/**
	 * 得到某一天的最后时间，精确到毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 某一天的下一天的0时0分0秒0毫秒的那个Date减去1毫秒所得到的Date
	 */
	public static Date endOfDay(Date date)
	{
		date = beginOfDay(date);
		return endOfDayByBeginOfDate(date);
	}

	/**
	 * 根据某一天的开始时间，得到某一天的最后时间，精确到毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 某一天的下一天的0时0分0秒0毫秒的那个Date减去1毫秒所得到的Date
	 */
	public static Date endOfDayByBeginOfDate(Date date)
	{
		date = nextDay(date);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, -1);
		date = c.getTime();
		return date;
	}

	/**
	 * 得到指定日期后若干天的日期 by Yin Jian
	 * 
	 * @param date
	 *            指定日期
	 * @param days
	 *            天数
	 * @return 自指定日期后的若干天的日期
	 */
	public static Date afterDaysSinceDate(Date date, int days)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		date = c.getTime();
		return date;
	}

	/**
	 * 判断两个Date是否在同一天 殷剑
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @return 如果两个Date在同一天，则返回true，否则false
	 */
	public static boolean isTwoDatesInSameDay(Date date1, Date date2)
	{
		Date preDate1 = preDay(date1);
		Date nextDate1 = nextDay(date1);
		if (date2.after(preDate1) && date2.before(nextDate1))
		{
			return true;
		}
		return false;
	}

	/**
	 * 得到指定日期的下一天的开始时间
	 * 
	 * @param date
	 *            指定Date
	 * @return 下一天的开始时间
	 */
	public static Date beginOfNextDay(Date date)
	{
		date = nextDay(date);
		return beginOfDay(date);
	}

	/**
	 * 得到指定日期的下一天
	 * 
	 * @param date
	 *            日期
	 * @return 传入日期的下一天
	 */
	public static Date nextDay(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		return date;
	}
	
	/**
	 * 得到指定日期的前一天
	 * 
	 * @param date
	 *            日期
	 * @return 传入日期的前一天
	 */
	public static Date preDay(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		return date;
	}

	/**
	 * 得到当前月份的下一个月份
	 * 
	 * @return String
	 */
	public static Date addMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		date = c.getTime();
		return date;
	}

	/**
	 * 得到当前月的最后一天
	 * 
	 * @return String
	 */
	public static Date getLastDayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		date = c.getTime();
		return date;
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @return String
	 */
	public static Date getFirstDayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		date = c.getTime();
		return date;
	}

	/**
	 * 判断一个日期是否在指定的时间段内
	 * 
	 * @return String
	 */
	public static boolean inTimeSegment(Date start, Date end, Date date)
	{
		start = preDay(start);
		end = nextDay(end);
		if (date.after(start) && date.before(end))
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断当前日期是否在指定的时间段内
	 * 
	 * @param start
	 *            时间段开始时间
	 * @param end
	 *            时间段结束时间
	 * @return 如果当前日期在指定时间段内，则为true，否则为false
	 */
	public static boolean isCurrentDateInTimeSegment(Date start, Date end)
	{
		Date date = getCurrentDate();
		if (inTimeSegment(start, end, date))
		{
			return true;
		}
		return false;
	}

	/**
	 * 得到同一个月内两个日期的间隔天数 备注：可能需要提交到框架作统一处理
	 * 
	 * @param start
	 * @param end
	 * @param date
	 * @return
	 */
	public static int betweenDaysInOneMonth(Date start, Date end)
	{
		String startStr = getFormatDate(start, "yyyyMMdd");
		String endStr = getFormatDate(end, "yyyyMMdd");
		int days = Integer.parseInt(endStr) - Integer.parseInt(startStr) + 1;
		return days;
	}

	/**
	 * @author YuQiang 得到两个日期的间隔天数
	 * @param start
	 * @param end
	 * @param date
	 * @return -1说明开始日期大于结束日期
	 */
	public static int getBetweenDays(Date start, Date end)
	{
		if (start.after(end))
		{
			return -1;
		}
		Calendar startC = Calendar.getInstance();
		startC.setTime(start);
		Calendar endC = Calendar.getInstance();
		endC.setTime(end);
		endC.add(Calendar.DAY_OF_YEAR, 1);
		int days = 0;
		do
		{
			days++;
			startC.add(Calendar.DAY_OF_YEAR, 1);
		} while (startC.before(endC));
		return days;
	}
	
	/**
	 * 得到指定月份的天数
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static int daysInMonth(Date date)
	{
		Date start = getFirstDayOfMonth(date);
		Date end = getLastDayOfMonth(date);
		int days = betweenDaysInOneMonth(start, end);
		return days;
	}

	/**
	 * 判断两个时间段是否存在重叠
	 * 
	 * @param start1
	 *            第一个时间段的开始时间
	 * @param end1
	 *            第一个时间段的结束时间
	 * @param start2
	 *            第二个时间段的开始时间
	 * @param end2
	 *            第二个时间段的结束时间
	 * @return 如果存在重叠返回true，否则false
	 */
	public static boolean isTimeOverlap(Date start1, Date end1, Date start2,
			Date end2)
	{
		if (inTimeSegment(start1, start2, end2)
				|| inTimeSegment(end1, start2, end2))
		{
			return true;
		}
		return false;
	}

	/**
	 * 把传入的时间和当前的系统时间进行比较，精度控制在天。
	 * 
	 * @param formmartTimeString
	 *            格式化的时间字符串 年月日 ********8位的字符串
	 * @return comparedResult 1 传入的时间大于系统当前时间，0相等，-1是小于。
	 */
	public static int compareCurrentTime(String formmartTimeString)
	{
		// TODO 得到当前系统的时间函数需要变更
		Calendar currentTime = Calendar.getInstance();
		Calendar comparedTime = (Calendar) currentTime.clone();
		comparedTime.set(Calendar.YEAR, Integer.parseInt(formmartTimeString
				.substring(0, 4)));
		comparedTime.set(Calendar.MONTH, Integer.parseInt(formmartTimeString
				.substring(4, 6)) - 1);
		comparedTime.set(Calendar.DAY_OF_MONTH, Integer
				.parseInt(formmartTimeString.substring(6, 8)));
		int comparedResult = comparedTime.compareTo(currentTime);
		return comparedResult;
	}
	
	public static Date getDateFromFormattingString(String dateString)
	{
		Calendar currentTime = Calendar.getInstance();
		currentTime.set(Calendar.YEAR, Integer.parseInt(dateString.substring(0,
				4)));
		currentTime.set(Calendar.MONTH, Integer.parseInt(dateString.substring(
				5, 7)) - 1);
		currentTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString
				.substring(8, 10)));
		currentTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateString
				.substring(11, 13)));
		currentTime.set(Calendar.MINUTE, Integer.parseInt(dateString.substring(
				14, 16)));
		currentTime.set(Calendar.SECOND, Integer.parseInt(dateString.substring(
				17, 19)));
		return currentTime.getTime();
	}

	public static Date getFormatDate(String str) throws ParseException
	{
		if (str == null || "".equals(str))
			return null;
		if (str.length() <= 10)
		{
			return getDateByString(str, "yyyy-MM-dd");
		} else
		{
			return getDateByString(str, "yyyy-MM-dd HH:mm");
		}
	}

	public static Date getDateByString(String str, String pattern) throws ParseException{
		SimpleDateFormat df3 = new SimpleDateFormat();
		df3.applyPattern(pattern);
		return df3.parse(str);
	}

	/**
	 * 在指定的时间上，增加给定的天数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days){
		return addMillisecond(date, days * (long) 24 * 3600 * 1000);
	}
	
	/**
	 * 在指定的时间上，增加给定的分钟数
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinute(Date date, int minutes){
		return addMillisecond(date, minutes * (long) 60 * 1000);
	}

	/**
	 * 在指定的时间上，增加给定的小时数
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addHour(Date date, int hours){
		return addMinute(date, hours * 60);
	}
	
	/**
	 * 在指定的时间上，增加给定的毫秒数
	 * @param date
	 * @param milliseconds
	 * @return
	 */
	public static Date addMillisecond(Date date, Long milliseconds){
		return new Date(date.getTime() + milliseconds);
	}
	
    /**
     * 符合本业务的获取时间方法,通过年月日小时生成时间
     *
     * @param date
     * @param hour
     * @return
     * @throws ParseException 
     */
    public static Date createDate(Date date, Long hour){
    	Date result = null;
    	if (date != null)
    	{
			try {
				result = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
        if (result != null && hour != null)
        {
            result.setHours(hour.intValue());
        }
        return result;
    }

    /**
     * 符合本业务的获取时间方法,通过年月日小时生成时间
     *
     * @param date
     * @param hour
     * @return
     * @throws ParseException 
     */
    public static Date createDate(Date date, Long hour, Long minute)
    {
    	Date result = null;
    	if (date != null)
    	{
			try {
				result = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
        if (result != null )
        {
            if(hour != null)
                result.setHours(hour.intValue());
            if(minute != null)
                result.setMinutes(minute.intValue());
        }
        return result;
    }

    /**
     * SQL语句小于时间的转换 比如小于1月1日，sql语句中其实是小于1月2日
     *
     * @param resourceDate
     * @return
     */
    public static Date getSqlLessDate(Date resourceDate)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(resourceDate);
        c.add(Calendar.DATE, 1);
        c.clear(Calendar.HOUR);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTime();
    }
    

    /**
     * 在一个日期推后 days 天 的日期，不包含星期六和星期天
     * @param date
     * @param days
     * @return
     */
    public static Date addDateIgnoreSaturdaySunday(Date date, long days)
    {
        if (date != null)
        {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            for (int i = 0; i < days;)
            {
                c.add(Calendar.DATE, 1);
                if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                {
                    i ++;
                }
            }
            date = c.getTime();
        }
        return date;

    }
    
    /**
     * 获取两个时间直接相差的秒数
     * @param date1
     * @param date2
     * @return
     */
    public static long getSecondsBetweenDay(Date date1,Date date2) {
    	return (date1.getTime() - date2.getTime())/1000;
    }
    
    /**
     * 获取指定日期所在的周的开始和结束日期
     * @return 开始日期和结束日期
     */
    public static Date[] getDaysOfWeek(Date current){
    	if(null == current){
    		current = getCurrentTime();
    	}
    	Date[] dateArr = new Date[2];
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(current);
    	int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
    	cal.add(Calendar.DATE, -day_of_week);
    	dateArr[0] = cal.getTime();
    	cal.add(Calendar.DATE, 6);
    	dateArr[1] = cal.getTime();
    	return dateArr;
    }
    
    /**
     * 获取当前日期所在季度的开始日期
     * @return
     */
    public static Date getCurrentQuarterStartDate() {
    	Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                 c.set(Calendar.MONTH, 0);
             else if (currentMonth >= 4 && currentMonth <= 6)
                 c.set(Calendar.MONTH, 3);
             else if (currentMonth >= 7 && currentMonth <= 9)
                 c.set(Calendar.MONTH, 6);
             else if (currentMonth >= 10 && currentMonth <= 12)
                 c.set(Calendar.MONTH, 9);
             c.set(Calendar.DATE, 1);
             now = c.getTime();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return now;
    }
    
    /**
     * 获取当前日期所在季度的结束日期
     * @return
     */
    public static Date getCurrentQuarterEndDate() {
    	Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
               c.set(Calendar.MONTH, 11);
               c.set(Calendar.DATE, 31);
           }
           now = c.getTime();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return now;
    }
    
    /***
     * 获取当前时间十位时间戳
     * @return
     */
    public static String getTenTimeStamp(){
    	return String.valueOf(new Date().getTime()/1000); 
    }
    
    /***
     * 获取当前时间十三位时间戳
     * @return
     */
    public static String getThirteenTimeStamp(){
    	return String.valueOf(new Date().getTime()); 
    }
    
    public static String getPreMonthDate()
    {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH,- 1);
    	return DateTimeUtil.getFormatDate(c.getTime(), "yyyyMMdd");
    }

}
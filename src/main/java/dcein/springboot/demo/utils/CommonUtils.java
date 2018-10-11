package dcein.springboot.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
 * @Author: DingCong
 * @Description:  常用工具類
 * @CreateDate: 2018/7/25 10:36
 */
@Slf4j
public class CommonUtils {
	
	/**
	 * 判断一个对象是否为空。
	 * 其中，如果obj为String，则判断字符串是否为null或者trim后为"";
	 * @return
	 */
	public static boolean isNullOrEmptyOfObj(Object obj){
		if(null == obj){
			return true;
		}
		if(obj instanceof String){
			return StringUtils.isEmpty((String)obj);
		}else if(obj instanceof Character){
			return StringUtils.isEmpty(String.valueOf(obj));
		}else{
			return false;
		}
	}

    /**
     * 从本地文件中读取文件内容
     * @param filePath
     * @return
     * @throws Exception
     */
	public static String getFileFromNative(String filePath) throws Exception {
	    File file = new File(filePath);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"utf-8");
        BufferedReader read = new BufferedReader(isr);
        String data = null;
        while((data = read.readLine()) != null){
            return data;
        }
        log.info("读取文件内容为:" + data);
        read.close();
	    return data;
    }

    public static void main(String[] args) throws Exception {
        getFileFromNative("F://b.txt");
    }

	public static String getFormatDate(Date dt, String pattern) {
		String sDate;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		sDate = formatter.format(dt);
		return sDate;
	}

	public static String getCharAndNum(int length) {
		StringBuffer val = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val.append((char) (choice + random.nextInt(26)));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val.append(String.valueOf(random.nextInt(10)));
			}
		}
		return val.toString();
	}




}

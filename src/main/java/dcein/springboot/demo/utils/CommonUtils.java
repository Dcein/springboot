package dcein.springboot.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

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




}

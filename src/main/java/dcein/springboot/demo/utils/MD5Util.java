package dcein.springboot.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;


/*
 * @Author: DingCong
 * @Description:  MD5加密工具类
 * @CreateDate: 2018/7/25 10:39
 * @param:
 */
@Slf4j
public class MD5Util {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
 
        return resultSb.toString();
    }
 
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * md5加密
     * @param origin
     * @param charsetname
     * @return
     */
    public static String md5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        	exception.printStackTrace();
        }
        return resultString;
    }


    /**
     * 对入参字符串进行MD5加密
     *
     * @param origin
     * @return
     */
    public static String encode(String origin) {
        String resultString = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // There won't these kinds of exception throw, so catch it.
            log.error("", e);
            return null;
        }
        byte[] by = md.digest(origin.getBytes());
        resultString = byteArrayToString(by);
        return resultString;
    }

    private static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            // Return 32 bits Hex value.
            resultSb.append(byteToHexString(b[i]));

            // Retirm 32 bits Oct value.
            // resultSb.append(byteToNumString(b[i]));
        }
        return resultSb.toString();
    }



    /**
     * 默认utf-8编码
     * @param origin
     * @return
     */
    public static String md5Encode(String origin) {
        return md5Encode(origin,"UTF-8");
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}

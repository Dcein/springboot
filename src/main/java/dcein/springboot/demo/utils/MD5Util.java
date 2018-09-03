package dcein.springboot.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: DingCong
 * @Description: MDS加密
 * @@Date: Created in 14:19 2018/8/9
 */
@Slf4j
public class MD5Util {


    public static String encryption(String plain, String algorithm) {
        if (StringUtils.isEmpty(plain))
            return plain;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(plain.getBytes("utf-8"));
            return toHexString(bytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return plain;
        }
    }

    private static String toHexString(byte bytes[]) {
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        for (byte b : bytes) {
            s = Integer.toHexString(b & 0xff);
            if (s.length() == 1)
                stringBuilder.append("0").append(s);
            else
                stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

}

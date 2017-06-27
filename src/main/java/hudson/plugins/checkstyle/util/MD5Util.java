package hudson.plugins.checkstyle.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能描述：md5编码工具
 */
public class MD5Util {

    /**
     * 功能描述：md5编码
     *
     * @param src 源字符串内容
     * @return
     * @author <a href="mailto:zxf89949@alibaba-inc.com">张晓凡</a>
     * @create on 2016-10-26
     * @version 1.0.0
     */
    public static String digest(String src) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            byte byteDataArr[] = md.digest();
            StringBuilder hexBuilder = new StringBuilder();
            for (byte byteData : byteDataArr) {
                String hex = Integer.toHexString(0xff & byteData);
                if (hex.length() == 1) hexBuilder.append('0');
                hexBuilder.append(hex);
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}

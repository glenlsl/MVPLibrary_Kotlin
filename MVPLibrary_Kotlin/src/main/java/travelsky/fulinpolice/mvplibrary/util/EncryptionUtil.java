package travelsky.fulinpolice.mvplibrary.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具
 * Created by Solin on 2017/2/10.
 */
public class EncryptionUtil {

    /**
     * 对字符串进行MD5编码
     */
    public static String encryptMD5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String hmacSha1(String base, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = Mac.getInstance(type);
        mac.init(secret);
        byte[] digest = mac.doFinal(base.getBytes());
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }
}

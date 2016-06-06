package ml.kanfa.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Kanfa.
 */

public class Encryt {

    private Encryt(){}

    public static String md5(String password){
        MessageDigest messageDigest;
        StringBuffer buffer = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            for (byte b : bytes){
                buffer.append(Integer.toHexString(b & 0xfff).toString());
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}

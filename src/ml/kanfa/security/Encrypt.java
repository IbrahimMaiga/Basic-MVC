package ml.kanfa.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ibrahim Maïga.
 */
public class Encrypt {

    private Encrypt(){}

    @SuppressWarnings("UnusedDeclaration")
    public static String md5(String password){
        return md5(password, 0xfff);
    }

    public static String md5(final String password, int hex){
        return impl(password, "MD5", hex);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static String sha1(final String password){
        return sha1(password, 0xffff);
    }

    public static String sha1(final String password, int hex){
        return impl(password, "SHA-1", hex);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static String sha256(String password){
        return sha256(password, 0xffff);
    }

    private static String sha256(final String password, int hex){
        return impl(password, "SHA-256", hex);
    }

    private static final String impl(final String password, final String algorithm, int hex){
        final MessageDigest messageDigest;
        final StringBuffer buffer = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            for (final byte b : bytes){
                buffer.append(Integer.toHexString(b & hex));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
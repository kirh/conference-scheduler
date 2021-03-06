package by.epam.tc.conference.commons;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Util methods related to md5 encoding
 */
public class Md5Util {

    private static final int RADIX = 16;

    /**
     * Encodes string to md5
     * @param text to encode
     * @return string of encoded text
     */
    public static String encode(String text) {
        try {
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md5Encoder.digest(bytes);
            BigInteger bigInteger = new BigInteger(1, digest);
            return bigInteger.toString(RADIX);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }
}

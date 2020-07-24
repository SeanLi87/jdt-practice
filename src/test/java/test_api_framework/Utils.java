package test_api_framework;


import org.junit.jupiter.api.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Base64;

public class Utils {

    private static BASE64Encoder b64Encoder = new BASE64Encoder();
    private static BASE64Decoder b64Decoder = new BASE64Decoder();


    public static String encodeB64(String str) {
        return b64Encoder.encodeBuffer(str.getBytes());
    }
    public String decodeB64(String str) throws IOException {
        byte[] bytes = b64Decoder.decodeBuffer(str);
        return new String(bytes);
    }

    public static String encodeUtilB64(String str) {
        byte[] encode = Base64.getEncoder().encode(str.getBytes());
        return new String(encode);
    }

}

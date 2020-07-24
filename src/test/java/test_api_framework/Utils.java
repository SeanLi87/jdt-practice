package test_api_framework;


import org.junit.jupiter.api.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Utils {

    private static BASE64Encoder b64Encoder = new BASE64Encoder();
    private static BASE64Decoder b64Decoder = new BASE64Decoder();

    public String encodeB64(String str) {
        return b64Encoder.encodeBuffer(str.getBytes());
    }
    public String decodeB64(String str) throws IOException {
        byte[] bytes = b64Decoder.decodeBuffer(str);
        return new String(bytes);
    }
    public String doubleDecodeB64(String str) throws IOException {
        System.out.println("str："+str);
        byte[] bytes = b64Decoder.decodeBuffer(str);
        byte[] bytes1 = b64Decoder.decodeBuffer(new String(bytes));
        System.out.println("decode:"+new String(bytes1));
        return new String(bytes1);
    }
}

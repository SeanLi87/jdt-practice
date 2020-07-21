package test_restAssured.wechart.utils;

import java.util.Date;
import java.util.Random;

public class FakerUtils {
    public static String getTimeStamp() {
        long currentTime = new Date().getTime();
        String timeStamp = Thread.currentThread().getId() + "" + currentTime;
        System.out.println("currentTime: " + currentTime + "timeStamp: " + timeStamp);
        return timeStamp;
    }

    public static String generateMobile() {

        Random r = new Random();
        String[] headers = {"13", "15", "16", "18", "17", "19"};
        String[] body = {"0","1","2","3","4","5","6","7","8","9"};
        String mobile = headers[r.nextInt(6)];;
        for (int i = 0; i < 9; i++) {
            mobile += body[r.nextInt(10)];
        }
        return mobile;
    }

    public static void main(String[] args) {
        int a= 12;
        String b = String.valueOf(a);
        System.out.println(b);
        System.out.println(Integer.toString(10));
        Integer.valueOf("123");
    }

}

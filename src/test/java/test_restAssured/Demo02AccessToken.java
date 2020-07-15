package test_restAssured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class Demo02AccessToken {

    private static String accessToken;

    @BeforeAll
    static void getAccessToken() {
        accessToken = given()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwf463180fbf61bcc9&corpsecret=" +
                        "mbSyavXxGkqxbW_AoK5iHegqD4hDJvrzcA-Y9KeOE20")
                .then()
                .extract().response().path("access_token");

        System.out.println(accessToken);
    }

    @Test
    void postWithAccessToken() {

        given()
                .contentType("application/json;charset=utf-8")
                .body("{\n" +
                        "   \"touser\" : \"@all\",\n" +
                        "   \"msgtype\" : \"text\",\n" +
                        "   \"agentid\" : 1000003,\n" +
                        "   \"text\" : {\n" +
                        "       \"content\" : \"你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\\"http://work.weixin.qq.com\\\">邮件中心视频实况</a>，聪明避开排队。\"\n" +
                        "   }\n" +
                        "}")
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+accessToken)
                .then()
                .log()
                .all();
    }
}

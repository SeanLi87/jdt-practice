package test_restAssured.wechart.api_object;

import static io.restassured.RestAssured.*;

public class TokenHelper {
    public String getToken() {
        String accessToken = given()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wwf463180fbf61bcc9&corpsecret=" +
                        "i9ZSvrhju10SVzEl7-SIOFNRCK3m_Wq_FDyYRgnpP84")
                .then()
                .extract().response().path("access_token");
        return accessToken;
    }
}

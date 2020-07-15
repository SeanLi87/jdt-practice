package test_restAssured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class Demo01GetTest {
    @Test
    void fun() {
        given()
                .get("https://www.baidu.com")
                .then()
                .statusCode(200)
                .log().all();
    }
}

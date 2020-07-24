package test_api_framework;


import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredFilterTest {

    private Utils utils = new Utils();

    @Test
    void encodePractice() {
        given().filter((req, res, ctx) -> {
            //返回的Response不具备set方法，无法修改body
            Response originResponse = ctx.next(req, res);
            //ResponseBuilder的作用主要是在Response的基础上建设出来一个新的可以修改body的对象
            ResponseBuilder responseBuilder = new ResponseBuilder().clone(originResponse);
            //解密
            String body = originResponse.body().asString();
            byte[] decode1 = Base64.getDecoder().decode(body);
            byte[] decode2 = Base64.getDecoder().decode(decode1);
            String decode = new String(decode2);
            //ResponseBuilder在最后通过build方法直接创建一个用于返回的不可修改的Response
            Response responseNew=responseBuilder.setBody(decode).build();
            return responseNew;
        })
                .when()
                .get("http://localhost:8000/raw.json")
                .then()
                .body("category_list.categories[0].name",equalTo("霍格沃兹测试学院公众号"));

    }
}

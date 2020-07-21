package test_restAssured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class Demo03 {

    @Test
    public void getJsonTest() {
        given().
                when().get("http://localhost:8000/lotto.json")
                .then()
                .body("store.book[1].author",equalTo("Evelyn Waugh"))
                .log()
                .all();

    }

    @Test
    public void getXmlTest() {

        given()
                .when()
                .get("http://localhost:8000/lotto.xml")
                .then()
                .body("shopping.category.item[0].name",equalTo("Chocolate"))
                .log()
                .all();
    }

}

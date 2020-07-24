package test_restAssured.wechart.test_case;

import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class Demo {
    @Test
    void demo() {
         Filter filter = new Filter() {
             @Override
             public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
                     Response originResponse = ctx.next(req, res);
//                     ResponseBuilder responseBuilder = new ResponseBuilder().clone(originResponse);
                     System.out.println("开始解密");
                     System.out.println(req.getURI());
                     System.out.println(res.getStatusCode());
//                     Response newResponse = responseBuilder.build();
                     return null;

             }
         };
        given().filter(filter).when()
                .get("http://shell.ceshiren.com:8000/raw.json")
                .then()
                .log()
                .body();

    }




}

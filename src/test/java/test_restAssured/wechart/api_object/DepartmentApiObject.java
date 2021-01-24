package test_restAssured.wechart.api_object;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

import io.restassured.filter.Filter.*;

public class DepartmentApiObject {

    public Response createDepartment(String name, String name_en, String parentid, String order, String id, String accessToken) {
        String body = "{\n" +
                "\"name\": \""+name+"\",\n" +
                "\"name_en\": \""+name_en+"\"";
        if (!"empty".equals(parentid)){
            body = body +
                    ",\n\"parentid\": "+parentid;
        }
        if (!"empty".equals(order)){
            body = body +
                    ",\n\"order\": "+order;
        }
        if (!"empty".equals(id)){
            body = body +
                    ",\n\"id\": "+id;
        }
        body = body + "\n}";
        System.out.println(body);

        Response createResponse = given()
                .contentType("application/json;charset=utf-8")
                .body(body)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken)
                .then()
                .extract()
                .response();
        return createResponse;
    }

    public Response deleteDepartment(int id, String accessToken) {
        Response deleteResponse = given()
                .when()
                .param("access_token",accessToken)
                .contentType("application/json;charset=utf-8")
                .param("id",id)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then()
                .extract()
                .response();
        return deleteResponse;
    }

    public Response updateDepartment(String id, String name, String name_en, String accessToken) {
        String body = "{\n" +
                "   \"id\": 2,\n" +
                "   \"name\": \""+name+"\",\n" +
                "   \"name_en\": \""+name_en+"\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1\n" +
                "}";
        Response updateResponse = given()
                .contentType("application/json;charset=utf-8")
                .when()
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+accessToken)
                .then()
                .extract()
                .response();
        return updateResponse;
    }

    public Response getDepartmentList(String id, String accessToken) {
        Response getListResponse = given()
                .when()
                .contentType("application/json;charset=utf-8")
                .param("access_token",accessToken)
                .param("id",id)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .extract()
                .response();
        return getListResponse;
    }

    public Response getDepartmentList(String accessToken) {
        Response getListResponse = given()
                .when()
                .contentType("application/json;charset=utf-8")
                .param("access_token",accessToken)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then()
                .extract()
                .response();
        return getListResponse;
    }
}

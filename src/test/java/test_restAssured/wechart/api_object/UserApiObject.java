package test_restAssured.wechart.api_object;

import io.restassured.response.Response;

import java.io.StringReader;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class UserApiObject {

    public Response createUser(String userId, String name, String departmentId, String Mobile, String AccessToken) {

        String body = "{\n" +
                "    \"userid\": \""+userId+"\",\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"department\": ["+departmentId+"],\n" +
                "    \"mobile\": \""+Mobile+"\",\n" +
                "    \"is_leader_in_dept\": [1]\n" +
                "}";
        Response response = given()
                .when()
                .contentType("application/json;charset=utf-8")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=" + AccessToken)
                .then()
                .extract()
                .response();
        return response;
    }

    public Response getUser(String userId, String AccessToken) {

        Response response = given()
                .when()
                .param("access_token",AccessToken)
                .param("userid",userId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then()
                .extract()
                .response();
        return response;
    }

    public Response updateUser(String userId, String name, String AccessToken) {

        String body = "{\n" +
                "    \"userid\": \""+userId+"\",\n" +
                "    \"name\": \""+name+"\"\n" +
                "}";

        Response response = given()
                .when()
                .body(body)
                .contentType("application/json;charset=utf-8")
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+AccessToken)
                .then()
                .extract()
                .response();
        return response;
    }

    public Response deleteUser(String userId, String AccessToken) {

        Response response = given()
                .when()
                .param("access_token",AccessToken)
                .param("userid",userId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then()
                .extract()
                .response();
        return response;
    }

    public Response batchDelete(String userList, String AccessToken) {

        String body = "{\n" +
                "    \"useridlist\": ["+userList+"]\n" +
                "}";

        Response response = given()
                .when()
                .body(body)
                .contentType("application/json;charset=utf-8")
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token="+AccessToken)
                .then()
                .extract()
                .response();
        return response;
    }

    public Response getDepartmentUser(String departmentId, String AccessToken) {

        Response response = given()
                .when()
                .param("access_token",AccessToken)
                .param("department_id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then()
                .extract()
                .response();
        return response;
    }


}

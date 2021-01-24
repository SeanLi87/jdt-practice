package test_restAssured.wechart.test_case;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import junit5.User;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import test_restAssured.wechart.api_object.TokenHelper;
import test_restAssured.wechart.api_object.UserApiObject;
import test_restAssured.wechart.env_task.EnvTask;
import test_restAssured.wechart.utils.FakerUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

@Epic("企业微信服务端-通讯录接口测试")
@Feature("用户管理接口测试")
public class TestCase02 {
    private static String accessToken;
    private static final Logger logger = Logger.getLogger(TestCase02.class);
    private static String name = "接口测试用户";
    private static String departmentId = "1";
    private static String mobile;
    private static String userID;
    private static UserApiObject userApiObject;


    @BeforeAll
    static void init() {
        logger.info("初始化环境");
        accessToken = new TokenHelper().getToken();
        EnvTask.envCleanDepartment(accessToken);
        userApiObject = new UserApiObject();

    }


//    @AfterAll
//    static void cleanEnv() {
//        logger.info("清理环境");
//        EnvTask.envCleanDepartmentUser(departmentId,accessToken);
//        EnvTask.envCleanDepartment(accessToken);
//
//    }

    @BeforeEach
    void initEach() {
        mobile = FakerUtils.generateMobile();
        userID = FakerUtils.getTimeStamp();//构建不重复的userID
    }

    @AfterEach
    void clearEach() {
        logger.info("清理环境");
        logger.info("删除用户返回消息" + userApiObject.deleteUser(userID, accessToken).path(".").toString());
    }

    @Test
    void createUser() {
        logger.info("创建用户");
        Response createResponse = userApiObject.createUser(userID, name + userID, departmentId, mobile, accessToken);//调用创建用户接口
        logger.info("返回消息" + createResponse.path(".").toString());

        assertAll("返回值检查",
                () -> assertThat(createResponse.path("errcode"), equalTo(0)),
                () -> assertThat(createResponse.path("errmsg"), equalTo("created")));
    }

    @Test
    void deleteUser() {
        logger.info("删除用户测试");
        logger.info("1.创建用户");
        Response createResponse = userApiObject.createUser(userID, name + userID, departmentId, mobile, accessToken);
        logger.info("返回消息" + createResponse.path(".").toString());
        logger.info("2.删除用户");
        Response deleteResponse = userApiObject.deleteUser(userID, accessToken);
        logger.info("返回消息" + deleteResponse.path(".").toString());

        assertAll("返回值检查",
                () -> assertThat(createResponse.path("errcode"), equalTo(0)),
                () -> assertThat(createResponse.path("errmsg"), equalTo("created")),
                () -> assertThat(deleteResponse.path("errcode"), equalTo(0)),
                () -> assertThat(deleteResponse.path("errmsg"), equalTo("deleted"))
        );
    }

    @Test
    void updateUser() {
        logger.info("更新用户测试");
        logger.info("1.创建用户");
        Response createResponse = userApiObject.createUser(userID, name + userID, departmentId, mobile, accessToken);
        logger.info("2.更新用户");
        Response updateReponse = userApiObject.updateUser(userID, name + userID + "updated", accessToken);
        logger.info("返回消息: " + updateReponse.path(".").toString());

        assertAll("用户更新校验",
                () -> assertThat(updateReponse.path("errcode"), equalTo(0)),
                () -> assertThat(updateReponse.path("errmsg"), equalTo("updated"))
        );

    }

    @Test
    void getUser() {

        logger.info("获取用户测试");
        logger.info("1.创建用户");
        Response createResponse = userApiObject.createUser(userID, name + userID, departmentId, mobile, accessToken);
        logger.info("2.获取用户");
        Response getResponse = userApiObject.getUser(userID, accessToken);
        logger.info("返回消息： " + getResponse.path(".").toString());
        assertAll("用户读取校验",
                () -> assertEquals("0", getResponse.path("errcode").toString()),
                () -> assertEquals(userID, getResponse.path("userid").toString()),
                () -> assertEquals(name + userID, getResponse.path("name").toString())
        );
    }

    @Test
    void batchDelete() {
        logger.info("批量删除用户测试");
        logger.info("1.创建用户");
        Response createResponse1 = userApiObject.createUser(userID + "1", name + userID + "1", departmentId, mobile, accessToken);
        Response createResponse2 = userApiObject.createUser(userID + "2", name + userID + "2", departmentId, mobile, accessToken);
        logger.info("2.组装用户ID列表");
        String useridlist_str = userID + "1" + "\"" + "," + "\"" + userID + "2";
        logger.info("3.批量删除用户");
        Response batchDeleteResponse = userApiObject.batchDelete(useridlist_str, accessToken);
        logger.info("4.读取用户");
        Response getResponse1 = userApiObject.getUser(userID + "1", accessToken);
        Response getResponse2 = userApiObject.getUser(userID + "2", accessToken);
        logger.info("返回消息： " + getResponse1.path(".").toString());
        logger.info("返回消息： " + getResponse2.path(".").toString());

        assertAll("返回值校验",
                () -> assertThat(batchDeleteResponse.path("errcode"), equalTo(0)),
                () -> assertThat(getResponse1.path("errcode"), equalTo(60111)),
                () -> assertThat(getResponse2.path("errcode"), equalTo(60111))
        );


    }


}

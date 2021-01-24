package test_restAssured.wechart.test_case;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test_restAssured.wechart.api_object.DepartmentApiObject;
import test_restAssured.wechart.api_object.TokenHelper;
import test_restAssured.wechart.env_task.EnvTask;
import io.restassured.matcher.RestAssuredMatchers.*;
import test_restAssured.wechart.utils.FakerUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("企业微信服务端-通讯录接口测试")
@Feature("部门管理接口测试")

public class TestCase01 {

    private static String accessToken;
    private static EnvTask envTask = new EnvTask();
    private static final Logger logger = Logger.getLogger(TestCase01.class);

    @BeforeAll
    static void getAccessToken() {
        accessToken = new TokenHelper().getToken();
    }

    @BeforeEach
    void init() {
        envTask.envCleanDepartment(accessToken);
    }

    @AfterEach
    void tearDown() {
        envTask.envCleanDepartment(accessToken);
    }

    @Description("创建部门")
    @DisplayName("创建部门")
    @CsvFileSource(resources = "/wechart_department.csv", numLinesToSkip = 1)
    @ParameterizedTest
    @Order(1)
    void createDepartment(String Name, String NameEn, String ParentId, String Order, String Id) {
        Response createResponse = new DepartmentApiObject().createDepartment(Name, NameEn, ParentId, Order, Id, accessToken);
        Response getDepartmentResponse = new DepartmentApiObject().getDepartmentList(accessToken);
        getDepartmentResponse.then().body("errmsg", equalTo(5));

        assertAll("返回值校验",
                () -> assertEquals(200, createResponse.statusCode()),//状态码校验
                () -> assertEquals("created", createResponse.path("errmsg").toString()),//返回消息校验
                () -> assertEquals("0", createResponse.path("errcode").toString()),//返回错误码校验
                () -> assertEquals(new Integer(Id), createResponse.path("id")),//返回消息校验
                //读取部门列表校验
                () -> assertEquals(200, getDepartmentResponse.statusCode()),//状态码校验
                () -> assertEquals("ok", getDepartmentResponse.path("errmsg").toString()),//返回消息校验
                () -> assertEquals("0", getDepartmentResponse.path("errcode").toString())//返回错误码校验
        );


    }

    @Test
    @Description("更新部门")
    @Order(2)
    void updateDepartment() {
        //创建部门
        Response createResponse = new DepartmentApiObject().createDepartment("测试部门", "test partment", "1", "empty", "empty", accessToken);
        createResponse.then().log().all();
        //更新部门
        Response updateResponse = new DepartmentApiObject().updateDepartment(createResponse.path("id").toString(), "测试部门更新", "update name", accessToken);
        updateResponse.then().log().all();

        //获取部门
        Response getResponse = new DepartmentApiObject().getDepartmentList(createResponse.path("id").toString(), accessToken);
        getResponse.then().log().all();

        //断言
        assertAll("hamcrest断言",
                () -> assertThat(createResponse.path("errcode"), equalTo(0)),//创建部门errcode正确
                () -> assertThat(createResponse.path("errmsg"), equalTo("created")),//创建部门errmsg正确
                () -> assertThat(createResponse.path("id"), equalTo(2)),//创建部门ID正确
                () -> assertThat(updateResponse.path("errcode"), equalTo(0)),//更新部门ID正确
                () -> assertThat(updateResponse.path("errmsg"), equalTo("updated")),//更新部门errmsg正确
                () -> assertThat(getResponse.path("department[0].id"), equalTo(2)),//获取部门ID正确
                () -> assertThat(getResponse.path("department[0].name"), equalTo("测试部门更新")),//name正确
                () -> assertThat(getResponse.path("department[0].parentid"), equalTo(1)),//parentid
                () -> assertThat(getResponse.path("department[0].order"), notNullValue()),//order
                () -> assertThat(getResponse.path("department[0].name_en"), equalTo("update name"))//name_en
        );
    }

    @Test
    void deleteDepartment() {
        //创建部门
        Response createResponse = new DepartmentApiObject().createDepartment("测试部门", "test partment", "1", "empty", "empty", accessToken);
        createResponse.then().log().all();
        //删除部门
        Response deleteResponse = new DepartmentApiObject().deleteDepartment(createResponse.path("id"), accessToken);
        deleteResponse.then().log().all();
        //获取部门
        Response getResponse = new DepartmentApiObject().getDepartmentList(createResponse.path("id").toString(), accessToken);
        getResponse.then().log().all();
        assertAll(
                () -> assertThat(createResponse.path("id"), equalTo(2)),
                () -> assertThat(deleteResponse.path("errcode"), equalTo(0)),
                () -> assertThat(deleteResponse.path("errmsg"), equalTo("deleted")),
                () -> assertThat(getResponse.path("errcode"), equalTo(60123))

        );
    }

    @Test
    @Description("获取部门列表")
    @DisplayName("获取部门列表")
    @Order(4)
    void getDepartmentList() {
        Response getDepartmentResponse = new DepartmentApiObject().getDepartmentList(accessToken);
        getDepartmentResponse.then().log().all();
        System.out.println("===========================================================");
        List<HashMap<String, Object>> departmentList = getDepartmentResponse.path("department");
        for (HashMap<String, Object> department : departmentList) {
            System.out.println(department.get("name"));
        }
        assertAll("返回值校验",
                () -> assertEquals(new Integer(0), getDepartmentResponse.path("errcode")),//返回值类型为数字的校验
                () -> assertEquals("1", getDepartmentResponse.path("department[1].name")),//返回值类型为字符串的校验
                () -> assertEquals(200, getDepartmentResponse.statusCode()),//返回状态码校验
                () -> assertEquals("243", getDepartmentResponse.getHeader("Content-Length"))//返回头校验
        );
        //使用hamcrest进行断言
        assertAll(
                () -> assertThat(getDepartmentResponse.path("department[1].name"), equalTo(1))
        );
    }

    //    @RepeatedTest(50)
//    @Execution(CONCURRENT)
    @Description("Description测试")
    @Story("Story测试")
    @DisplayName("DisplayName测试")
    @Severity(SeverityLevel.BLOCKER)
    @Issue("issue123")
    @TmsLink("Test-1")
    @Test
    void test() {
        assertEquals("1", "1");
        logger.info("info" + FakerUtils.getTimeStamp());
        logger.debug("debug" + FakerUtils.getTimeStamp());
        logger.error("error" + FakerUtils.getTimeStamp());
        logger.trace("trace" + FakerUtils.getTimeStamp());
        logger.fatal("fatal" + FakerUtils.getTimeStamp());
        logger.warn("warn" + FakerUtils.getTimeStamp());


    }
}

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

import static org.junit.jupiter.api.Assertions.*;

@Epic("企业微信服务端接口测试")
@Feature("部门接口测试")

public class TestCase01 {

    private static String accessToken;
    private static EnvTask envTask = new EnvTask();
    private static final Logger logger = Logger.getLogger(TestCase01.class);
    @BeforeAll
    static void getAccessToken() {
        accessToken = new TokenHelper().getToken();
    }

//    @BeforeEach
    void init() {
        envTask.envCleanDepartment(accessToken);
    }
//    @AfterEach
    void tearDown(){
        envTask.envCleanDepartment(accessToken);
    }
    @Description("创建部门")
    @DisplayName("创建部门")
    @CsvFileSource(resources = "/wechart_department.csv", numLinesToSkip = 1)
    @ParameterizedTest
    @Order(1)
    void createDepartment(String Name, String NameEn) {

        Response createResponse = new DepartmentApiObject().createDepartment(Name,NameEn,"2",accessToken);
        assertAll("返回值校验",
                ()->assertEquals("0",createResponse.path("errcode").toString()),
                ()->assertEquals("1",createResponse.path("errcode").toString()),
                ()->assertEquals("2",createResponse.path("errcode").toString()),
                ()->assertEquals("0",createResponse.path("errcode").toString()));


    }

    @Test
    @Description("更新部门")
    @Order(2)
    void updateDepartment() {
        Response updateResponse = new DepartmentApiObject().updateDepartment("", "", accessToken);

        assertEquals("0",updateResponse.path("errcode").toString());
    }

    @Test
    void deleteDepartment() {

        Response deleteResponse = new DepartmentApiObject().deleteDepartment(1, accessToken);
        assertEquals("0",deleteResponse.path("errcode").toString());



    }

    @Test
    @Description("获取部门列表")
    @DisplayName("获取部门列表")
    @Order(4)
    void getDepartmentList() {
        Response getDepartmentResponse = new DepartmentApiObject().getDepartmentList(accessToken);
        assertEquals("0",getDepartmentResponse.path("errcode").toString());
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
        assertEquals("1","1");
//        logger.info("info"+FakerUtils.getTimeStamp());
//        logger.debug("debug"+FakerUtils.getTimeStamp());
//        logger.error("error"+FakerUtils.getTimeStamp());

    }




}

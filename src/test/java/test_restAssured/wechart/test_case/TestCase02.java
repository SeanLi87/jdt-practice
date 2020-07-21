package test_restAssured.wechart.test_case;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import test_restAssured.wechart.api_object.DepartmentApiObject;
import test_restAssured.wechart.api_object.TokenHelper;
import test_restAssured.wechart.api_object.UserApiObject;
import test_restAssured.wechart.env_task.EnvTask;
import test_restAssured.wechart.utils.FakerUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;

@Epic("企业微信服务端接口测试")
@Feature("用户接口测试")
public class TestCase02 {
    private static String accessToken;
    private static final Logger logger = Logger.getLogger(TestCase02.class);
    private static String departmentId = "2";
    private static String mobile;



    @BeforeAll
    static void init() {
        logger.info("初始化环境");
        accessToken = new TokenHelper().getToken();
        EnvTask.envCleanDepartment(accessToken);
        Response response = new DepartmentApiObject()
                .createDepartment("测试部门", "testDepartment", departmentId, accessToken);
    }


    @AfterAll
    static void cleanEnv() {
        logger.info("清理环境");
        EnvTask.envCleanDepartmentUser(departmentId,accessToken);
        EnvTask.envCleanDepartment(accessToken);

    }

    @BeforeEach
    void initEach(){
        mobile = FakerUtils.generateMobile();
        logger.info(mobile);
    }

    @Test
    void createUser() {
        logger.info("创建用户测试");
        String timeStamp = FakerUtils.getTimeStamp();
        Response response = new UserApiObject().createUser(timeStamp, "用户" + timeStamp, departmentId, mobile, accessToken);
        response.then().log().body();
        assertEquals("0", response.path("errcode").toString());

    }

    @Test
    void deleteUser() {
        String fakeId = FakerUtils.getTimeStamp();
        new UserApiObject().createUser(fakeId, "用户" + fakeId, departmentId, mobile, accessToken);
        Response response = new UserApiObject().deleteUser(fakeId, accessToken);

        assertEquals("0", response.path("errcode").toString());
    }

    @Test
    void updateUser() {
        String fakeId = FakerUtils.getTimeStamp();
        new UserApiObject().createUser(fakeId, "用户" + fakeId, departmentId, mobile, accessToken);
        Response response = new UserApiObject().updateUser(fakeId, "用户", accessToken);

        assertAll("用户更新校验",
                () -> assertEquals("0", response.path("errcode").toString())
                /*()->assertEquals("0",response.path("errcode").toString())*/
        );

    }

    @Test
    void getUser() {
        String fakeId = FakerUtils.getTimeStamp();
        new UserApiObject().createUser(fakeId, "用户" + fakeId, departmentId, mobile, accessToken);
        Response response = new UserApiObject().getUser(fakeId, accessToken);
        assertAll("用户读取校验",
                () -> assertEquals("0", response.path("errcode").toString()),
                () -> assertEquals(fakeId, response.path("userid").toString()),
                () -> assertEquals("用户" + fakeId, response.path("name").toString())

        );
    }

    @Test
    void batchDelete() {
        String fakeId = FakerUtils.getTimeStamp();
        new UserApiObject().createUser(fakeId, "用户" + fakeId, departmentId, mobile, accessToken);
        Response getUserResponse = new UserApiObject().getDepartmentUser(departmentId, accessToken);
        ArrayList<String> useridlist = getUserResponse.path("userlist.userid");
        String useridlist_str = "";
        for (int i = 0; i < useridlist.size(); i++) {
            if (i == useridlist.size() - 1) {
                useridlist_str += "\"" + useridlist.get(i) + "\"";

            }else {
                useridlist_str += "\"" + useridlist.get(i) + "\",";
            }
        }
        System.out.println(useridlist_str);
        Response batchDeleteResponse = new UserApiObject().batchDelete(useridlist_str,accessToken);
        assertEquals("0", batchDeleteResponse.path("errcode").toString());
    }


}

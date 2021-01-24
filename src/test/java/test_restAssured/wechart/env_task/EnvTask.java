package test_restAssured.wechart.env_task;

import io.restassured.response.Response;
import test_restAssured.wechart.api_object.DepartmentApiObject;
import test_restAssured.wechart.api_object.UserApiObject;
import test_restAssured.wechart.utils.FakerUtils;

import java.util.ArrayList;

public class EnvTask {

    public static void envCleanDepartment(String accessToken) {
        //获取所有的测试部门列表
        Response departmentList = new DepartmentApiObject().getDepartmentList(accessToken);
        ArrayList<Integer> departmentIdList = departmentList.path("department.id");
        //循环删除
        for (int id: departmentIdList) {
            if (1 == id){//不删除第一个父类部门
                continue;
            }
            Response deleteResponse = new DepartmentApiObject().deleteDepartment(id, accessToken);

        }
    }

    public static void envCleanDepartmentUser(String departmentId, String accessToken){
        //获取部门用户
        //批量删除
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
        new UserApiObject().batchDelete(useridlist_str,accessToken);
    }
}

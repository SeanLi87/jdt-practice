package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TodoPage extends BasePage{
    //todo:多版本app、多平台的app 定位符通常有差别
//    private final By taskName = By.id("b2k");
//    private final By save = byText("保存");
//    private final By taskList = By.id("gg_");
//    private By add = By.id("gym");
//    private final By selectDate = By.id("afk");
//    private String dayOnEvent = "//*[@class='android.view.View'][@index='day']";
//    private String dayOnCalendar = "//*[@class='android.widget.LinearLayout'][@content-desc='day']";
//    private final By deleteConfirm = By.id("b_o");
//    private final By confirm = By.id("bq9");
//    private final By deleteButton = By.id("bfi");

    public TodoPage(AppiumDriver<MobileElement> driver) {
        super(driver);

    }

    //todo 新增待办事项
    public TodoPage add(String name){
        click(By.id("gym"));//点击添加
        sendKeys(By.id("b2k"), name);//输入待办名称
        click(By.id("gxq"));//点击保存
        return this;
    }

    public List<String> getTodoList(){
        List<String> list = driver.findElements(By.id("gw9")).stream().map(x->x.getText()).collect(Collectors.toList());
        System.out.println("获取的待办事项列表："+list);
        return list;//获取所有未完成待办事项
    }

    //todo 完成待办事项
    public TodoPage doneTodo(String name){
        List todoList = getTodoList();
        System.out.println("待删除的待办事项列表："+todoList);
//        if(name!=null){//完成指定的待办事项
//            click(name);
//            click(By.id("gwt"));//点击完成按钮
//        }
//        else {
//            for (Object todo:todoList){
//                click(By.id("gwt"));//点击完成按钮
//            }
//        }
        for (Object todo:todoList){
            click(By.id("gwt"));//点击完成按钮
            }
        System.out.println("删除完成");
        return this;
    }

    //todo 查看完成待办事项
    public List<String> getDoneList(){
        click("显示已完成事项"); //点击显式待办事项
        return driver.findElements(By.id("gw9")).stream().map(x->x.getText()).collect(Collectors.toList());//获取所有未完成待办事项
    }

    //todo 删除待办事项
    public TodoPage deleteTodo(String name){
        List todoList = getTodoList();
        longPress(By.id("gw9"));//长按
        for (Object todo:todoList){
            click(By.id("gwt"));//点击删除按钮
            }
//        if(name!=null){
//            isElementExist(By.id("gym"));//判断是否长按成功
//            click(By.id("gwt"));//点击删除按钮
//        }
//        else{
//            for (Object todo:todoList){
//                click(By.id("gwt"));//点击完成按钮
//            }
//        }
        return this;
    }
}

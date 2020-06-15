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
    private final By add = By.id("gym");
    private final By inputBox = By.id("b2k");
    private final By save = By.id("gxq");
    private final By done = By.id("gwt");
    private final By todo = By.id("gw9");

    public TodoPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public TodoPage add(String name){
        click(add);//点击添加
        sendKeys(inputBox, name);//输入待办名称
        click(save);//点击保存
        return this;
    }

    public List<String> getTodoList(){
        List<String> list = findEls(todo).stream().map(x->x.getText()).collect(Collectors.toList());
        return list;//获取所有未完成待办事项
    }

    public TodoPage doneTodo(String name){
        List todoList = getTodoList();
        for (Object todo:todoList){
            click(done);//点击完成按钮
            }
        return this;
    }

    public List<String> getDoneList(){
        click("显示已完成事项"); //点击显示完成的待办事项
        return driver.findElements(By.id("gw9")).stream().map(x->x.getText()).collect(Collectors.toList());//获取所有未完成待办事项
    }

    public TodoPage deleteTodo(String name){
        List todoList = getTodoList();
        longPress(todo);//长按
        for (Object todo:todoList){
            click(done);//点击删除按钮
            }
        return this;
    }
}

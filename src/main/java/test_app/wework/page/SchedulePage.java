package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class SchedulePage extends BasePage{
    //todo:多版本app、多平台的app 定位符通常有差别
    private final By taskName = By.id("b2k");
    private final By save = byText("保存");
    private final By taskList = By.id("gg_");
    private By add = By.id("gym");
    private final By selectDate = By.id("afk");
    private String dayOnEvent = "//*[@class='android.view.View'][@index='day']";
    private String dayOnCalendar = "//*[@class='android.widget.LinearLayout'][@content-desc='day']";
    private final By deleteConfirm = By.id("b_o");
    private final By confirm = By.id("bq9");
    private final By deleteButton = By.id("bfi");


    public SchedulePage(AppiumDriver<MobileElement> driver) {
        super(driver);

    }

    public SchedulePage add(String name, String day){
        click(add);
        sendKeys(taskName, name);
        click(selectDate);
        click(By.xpath(dayOnEvent.replace("day",day)));
        click(confirm);
        click(save);
        return this;
    }

    public List<String> getSchedule(String day){
        if(day!=null){
            click(By.xpath(dayOnCalendar.replace("day",day)));
        }
        return driver.findElements(taskList).stream().map(x->x.getText()).collect(Collectors.toList());
    }

    public SchedulePage delete(String day){
        List scheduleList = getSchedule(day);
        for (Object schedule:scheduleList){
            click(taskList);
            click(deleteButton);
            click(deleteConfirm);
        }
        return this;
    }
}

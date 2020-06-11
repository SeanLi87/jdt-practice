package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class 日程Page extends BasePage{
    //todo:多版本app、多平台的app 定位符通常有差别
    private final By taskName = By.id("b2k");
    private final By save = byText("保存");
    private final By taskList = By.id("gg_");
    private By add = By.id("gym");
    private final By selectDate = By.id("afk");
    private String dayOnEvent = "//*[@class='android.view.View'][@index='day']";
    private String dayOnCalendar = "//*[@class='android.widget.LinearLayout'][@content-desc='day']";
    private final By confirm = By.id("bq9");
    private final By deleteButton = By.id("bfi");
    public 日程Page(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    //todo 增加构造方法初始化day

    public 日程Page add(String name, String day){
        click(add);
        sendKeys(taskName, name);
        click(selectDate);
        click(By.xpath(dayOnEvent.replace("day",day)));
        click(confirm);
        click(save);
        return this;//this 代表啥？
    }

    public List<String> getSchedule(String day){
        if(day!=null){
            click(By.xpath(dayOnCalendar.replace("day",day)));
        }
        return driver.findElements(taskList).stream().map(x->x.getText()).collect(Collectors.toList());
        //stream的写法暂时不明白,collect的用法暂时不明白，输出一个schedule的tittle string list
    }

    public 日程Page delete(String day){
        if(day!=null){
            //todo 指定日期删除
            click(By.xpath(dayOnCalendar.replace("day",day)));
            System.out.println("指定日期: "+dayOnCalendar.replace("day",day));
        }
//        List scheduleList = getSchedule(day);
        List<String> scheduleList = driver.findElements(taskList).stream().map(x->x.getText()).collect(Collectors.toList());
        System.out.println("日程列表: "+scheduleList);
        while (scheduleList.toArray().length>0){
            System.out.println("开始删除: "+scheduleList);
            click(By.xpath("//*[@class='android.view.ViewGroup'][@index='0']"));
            click(deleteButton);
            click(By.id("b_o"));
            scheduleList = driver.findElements(taskList).stream().map(x->x.getText()).collect(Collectors.toList());
            System.out.println("删除完毕"+scheduleList);
        }
        return this;
    }
}

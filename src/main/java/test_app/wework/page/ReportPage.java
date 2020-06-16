package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ReportPage extends BasePage{
    //todo:多版本app、多平台的app 定位符通常有差别
    private final By  back = By.id("gyb");
    private final By todayInput = By.xpath("//*[@index='1'][@class='android.widget.EditText']");
    private final By tomorrowInput = By.xpath("//*[@index='3'][@class='android.widget.EditText']");
    private final By othersInput = By.xpath("//*[@index='5'][@class='android.widget.EditText']");

    public ReportPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ReportPage addDailyReport(String today, String tomorrow, String others){
        click("日报");//点击日报
        sendKeys(todayInput,today);//输入今日工作
        sendKeys(tomorrowInput,tomorrow);//输入明日工作
        sendKeys(othersInput,others);//输入其他事项
        swipe(new int[]{767,1279}, new int[] {767,143});//滑动屏幕
        click(By.xpath("//*[@content-desc='提交']"));//点击提交
        click("确定");//点击确定
        System.out.println("点击确定");
        click("详情");
        System.out.println("点击详情");
        click(back);
        System.out.println("点击返回");
        return this;
    }

    public List<String> getDailyReport(String text){
        click("记录");
        click("我提交的");
        return findEls(text).stream().map(x->x.getText()).collect(Collectors.toList());
    }
}

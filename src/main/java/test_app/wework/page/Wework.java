package test_app.wework.page;

import org.openqa.selenium.By;

public class Wework extends BasePage{
    public Wework() {
        super("com.tencent.wework", ".launch.LaunchSplashActivity");
    }

    public SchedulePage schedule(){
        click(By.xpath("//*[@text='日程']"));
        return new SchedulePage(driver);
    }

    public TodoPage todo(){
        click(By.id("gwk"));
        return new TodoPage(driver);
    }

    public ReportPage report(){
        click("工作台");
        //todo 往下滑动
        swipe("效率工具","工作台");
        click("汇报");
        return new ReportPage(driver);
    }


}

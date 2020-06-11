package test_app.wework.page;

import org.openqa.selenium.By;

public class Wework extends BasePage{
    public Wework() {
        super("com.tencent.wework", ".launch.LaunchSplashActivity");
    }

    public 日程Page schedule(){
        click(By.xpath("//*[@text='日程']"));
        return new 日程Page(driver);
    }


}

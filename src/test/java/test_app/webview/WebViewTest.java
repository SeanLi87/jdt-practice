package test_app.webview;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class WebViewTest {

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "hogwarts");
        desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
        desiredCapabilities.setCapability("appActivity", ".view.WelcomeActivityAlias");
        desiredCapabilities.setCapability("noReset", "true");
//        desiredCapabilities.setCapability("dontStopAppOnReset", "true");
        //使用“chromedriverExecutableDir”时，将所有版本放到目录中，appium可以自动搜索对应版本
        desiredCapabilities.setCapability("chromedriverExecutable", "/Users/xin.li/chromedriver/69/chromedriver");

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        //todo: 等待优化

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@text='交易']"));
    }

    @Test
    public void test() {

    }

    @Test
    public void webview_native() {
        driver.findElement(By.xpath("//*[@text='交易']")).click();
        driver.findElement(By.xpath("//*[@text='基金开户']")).click();
    }

    @Test
    public void webview_web() throws InterruptedException {
        //todo 等待优化
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElement(By.xpath("//*[@text='交易']")).click();
        for (int i = 0; i < 2; i++) {
            driver.getContextHandles().forEach(context -> System.out.println(context.toString()));
            Thread.sleep(500);
        }
        driver.context(driver.getContextHandles().toArray()[1].toString());

        //切换实盘交易窗口
        Object[] array = driver.getWindowHandles().toArray();
        for (int i = 0; i < array.length; i++) {
            driver.switchTo().window(array[i].toString());
            if ("实盘交易".equals(driver.getTitle())){
                System.out.println("已经切换到实盘交易窗口");
                break;
            }
        }
//        点击A股开户,跳转到页面
        driver.findElement(By.cssSelector(".trade_home_agu_3ki")).click();
        System.out.println("完成点击A股开户");
        //下面是Xpath定位
//        driver.findElement(By.xpath("//div[@class='trade_home_trade-home_2gR undefined']//ul[@class='trade_home_open-list_2A9']//li[@class='trade_home_agu_3ki']")).click();
        //切换雪球股票窗口(开户前的页面）
        array = driver.getWindowHandles().toArray();
        for (int i = 0; i < array.length; i++) {
            driver.switchTo().window(array[i].toString());
            if ("平安证券 极速开户".equals(driver.getTitle())){
                System.out.println("已经切换到A股开户广告窗口");
                break;
            }
        }

//        //点击立即开户
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".open_more_sliderWrap_3d6"))).click();
////        driver.findElement(By.cssSelector(".open_more_sliderWrap_3d6")).click();
//        //切换当前窗口“中信证券”
//        array = driver.getWindowHandles().toArray();
//        for (int i = 0; i < array.length; i++) {
//            driver.switchTo().window(array[i].toString());
//            if ("中信证券".equals(driver.getTitle())){
//                System.out.println("已经切换到对应窗口");
//                break;
//            }
//        }
        //输入手机号
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='phone-number']")))
                .sendKeys("13333333333");
//        MobileElement el1 = (MobileElement) driver.findElement(By.xpath("//input[@placeholder='请输入您的手机号']"));
//        el1.sendKeys("13333333333");
        //输入验证码
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='code']")))
                .sendKeys("111111");
//        MobileElement el2 = (MobileElement) driver.findElement(By.xpath("//input[@placeholder='请输入短信验证码']"));
//        el2.sendKeys("111111");
        //点击开户
        driver.findElement(By.xpath("//*[@class='btn-submit']"));

    }

    @Test
    public void sampleTest() {
        MobileElement el4 = (MobileElement) driver.findElementById("com.xueqiu.android:id/home_search");
        el4.click();
        MobileElement el5 = (MobileElement) driver.findElementById("com.xueqiu.android:id/search_input_text");
        el5.sendKeys("alibaba");
        MobileElement el6 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.LinearLayout/android.widget.TextView[1]");
        el6.click();
    }

    //    @After
//    public void tearDown() throws InterruptedException {
//        Thread.sleep(20000);
//        driver.quit();
//    }
}


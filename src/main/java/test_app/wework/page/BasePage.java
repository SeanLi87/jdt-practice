package test_app.wework.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.text.SimpleDateFormat;


public class BasePage {
    private final int timeOutInSecondsDefault = 5; //显式等待时间
    AppiumDriver<MobileElement> driver;

    //    IOSDriver
    WebDriverWait wait;
    String packageName;
    String activityName;
    WaitOptions waitOption;
    TouchAction action;

    public BasePage(String packageName, String activityName) {
        this.packageName = packageName;
        this.activityName = activityName;
        startApp(this.packageName, this.activityName);

    }

    public BasePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, timeOutInSecondsDefault,200);
        waitOption = WaitOptions.waitOptions(Duration.ofMillis(200));
        action = new TouchAction(driver);
    }

    public void startApp(String packageName, String activityName){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("deviceName", "hogwarts");
        desiredCapabilities.setCapability("appPackage", packageName);
        desiredCapabilities.setCapability("appActivity", activityName);
        desiredCapabilities.setCapability("noReset", "true");
        desiredCapabilities.setCapability("udid", "");
        desiredCapabilities.setCapability("dontStopAppOnReset", "true");
        desiredCapabilities.setCapability("skipLogcatCapture", "true");

        URL remoteUrl = null;
        try {
            remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");//appium server的地址
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        //todo: 等待优化
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);//隐式等待时间
        wait = new WebDriverWait(driver, timeOutInSecondsDefault);
        action = new TouchAction(driver);
        waitOption = WaitOptions.waitOptions(Duration.ofMillis(200));
    }


    public void quit() {
        driver.quit();
    }


    public By byText(String text){
        return By.xpath("//*[@text='"+ text + "']");
    }
    public MobileElement find(By by) {
        return driver.findElement(by);
    }

    public MobileElement find(String text) {
        return driver.findElement(byText(text));
    }

    public List<MobileElement> findEls(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return driver.findElements(by);
        }
        catch (org.openqa.selenium.TimeoutException ex){
            return Collections.emptyList();
        }
    }

    public List<MobileElement> findEls(String text) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(byText(text)));
            return driver.findElements(byText(text));
        }
        catch (org.openqa.selenium.TimeoutException ex){
            return Collections.emptyList();
        }

    }

    public void click(By by) {
        //todo: 异常处理
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();

    }

    public void click(String text) {
        //todo: 异常处理
        wait.until(ExpectedConditions.visibilityOfElementLocated(byText(text))).click();
    }


    public void sendKeys(By by, String content) {
        driver.findElement(by).sendKeys(content);
    }

    //todo
    public void waitElement() {

    }

//    public boolean isElementExist(By by){
//        try{
//            driver.findElements(by);
//            return true;
//        }
//        catch (org.openqa.selenium.NoSuchElementException ex){
//            return false;
//        }
//        driver.findElements(by);
//        return true;
//    }
//
//    public boolean isElementExist(String text){
//        try{
//            driver.findElements(byText(text));
//            return true;
//        }
//        catch (org.openqa.selenium.NoSuchElementException ex){
//            return false;
//        }
//    }

    public void longPress(String text){
        PointOption startPoint = PointOption.point(find(text).getLocation());
        action.longPress(startPoint).waitAction(waitOption).perform();
    }

    public void longPress(By by){
        PointOption startPoint = PointOption.point(find(by).getLocation());
        action.longPress(startPoint).waitAction(waitOption).perform();
    }

//    public void refresh(){
//        driver.resetApp();
//    }

    public void swipe(By from, By to){
        System.out.println("startpoint"+find(from).getLocation());
        System.out.println("endpoint"+find(to).getLocation());
        PointOption startPoint = PointOption.point(find(from).getLocation());
        PointOption endPoint = PointOption.point(find(to).getLocation());
        action.press(startPoint).waitAction(waitOption).moveTo(endPoint).release().perform();
    }
    public void swipe(String from, String to){
        System.out.println("startpoint"+find(from).getLocation());
        System.out.println("endpoint"+find(to).getLocation());
        PointOption startPoint = PointOption.point(find(from).getLocation());
        PointOption endPoint = PointOption.point(find(to).getLocation());
        action.press(startPoint).waitAction(waitOption).moveTo(endPoint).release().perform();

    }

    public void swipe(int [] from, int [] to){
        PointOption startPoint = PointOption.point(from[0],from[1]);
        PointOption endPoint = PointOption.point(to[0],to[1]);
        action.press(startPoint).waitAction(waitOption).moveTo(endPoint).release().perform();

    }

}

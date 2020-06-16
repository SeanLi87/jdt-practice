package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportPageTest {

    private static Wework wework;

    @BeforeAll
    static void beforeAll(){
        wework = new Wework();
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        wework.driver.closeApp();//todo 需要针对不同case设置回到主页面的操作，减少程序打开时间
    }

    @Test//添加日报
    void addDailyReport() {
        assertTrue(wework.report().addDailyReport("完成作业06","完成作业07","暂无").getDailyReport("今日工作：完成作业06").contains("今日工作：完成作业06"));
    }

    @Test//获取日报列表
    void getDailyReport(){
        assertTrue(wework.report().getDailyReport("今日工作：完成作业06").contains("今日工作：完成作业06"));
    }

}
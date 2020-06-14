package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

class 日程PageTest {

    private static Wework wework;
    private static String day = "13";//todo 暂时只能测试当月日期,其他月份需要添加滑动操作

    @BeforeAll
    static void beforeAll(){
        wework = new Wework();
        wework.schedule().delete(day);//清理指定日期上已有日程
    }
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test//添加日程，day参数为空时添加当前页面的日程
    void addCalendarToDate() {
        assertTrue(wework.schedule().add("上班打卡"+day, day).getSchedule(day).contains("上班打卡"+day));
    }

    @Test//获取日程，day参数为空时获取当前页面的日程
    void getSchedule() {
        assertTrue(wework.schedule().getSchedule(day).contains("上班打卡"+day));
    }

    @Test//删除日程,day参数为空时删除当前页面的日程
    void delete() {
        assertFalse(wework.schedule().delete(day).getSchedule(day).contains("上班打卡"+day));
    }

}
package test_app.wework.page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodoPageTest {

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

    @Test//添加todo
    void addTodo() {
        assertTrue(wework.todo().add("完成作业06").getTodoList().contains("完成作业06"));
    }

    @Test//获取未完成的todo列表
    void getTodo(){
        assertTrue(wework.todo().getTodoList().contains("完成作业06"));
    }

    @Test//完成todo
    //todo done掉todo之后会出现不能获取到cache里的dom元素,还未解决
    void doneTodo() {
        assertFalse(wework.todo().doneTodo("完成作业06").getTodoList().contains("完成作业06"));
    }

    @Test//获取完成todo列表
    void getDoneTodo(){
        assertTrue(wework.todo().getDoneList().contains("完成作业06"));
    }

    @Test//删除todo
    void deleteTodo(){
        assertFalse(wework.todo().deleteTodo("完成作业06").getTodoList().contains("完成作业06"));
    }

    //todo 如何降低case之间的耦合？如何快捷地为每个case增加单独的数据构造

}
package test_mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.mitm.TrustSource;
import net.lightbody.bmp.mitm.manager.ImpersonatingMitmManager;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.getAllStackTraces;
import static java.lang.Thread.sleep;


public class Demo01BrowserMobProxy {

    private static BrowserMobProxy proxyServer;
    private static String[] replaceNum = {"-10","-1","-0.5","0","0.5","1","10"};
    private static String[] replaceString = {"","中文","English","&&&&","null"};
    private Random num = new Random();


    @BeforeAll
    public static void startProxyServer() {

        proxyServer = new BrowserMobProxyServer();
//        proxyServer.setMitmDisabled(true);//加上后，请求能访问https，但是代理捕获不到响应内容
        proxyServer.start(8089);
    }


    @Test
    public void demoTest() throws InterruptedException {

        proxyServer.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse httpResponse, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo) {


                if (httpMessageContents.getContentType().contains("application/json")) {
                    System.out.println("打印contentTYPE=============");
                    System.out.println(httpMessageContents.getContentType());
                    System.out.println("替换前打印contents================");
                    System.out.println(httpMessageContents.getTextContents());
                    //替换方法
//                    httpMessageContents.setTextContents(httpMessageContents.getTextContents().replace("奇安信", "mock股票"));
//                    mockResponseJson(httpMessageContents.getTextContents());
                    String mockResponse =
                            mockResponseJson(httpMessageContents.getTextContents()
                                    ,replaceNum[num.nextInt(replaceNum.length)],"num");

                    httpMessageContents.setTextContents(mockResponse);

                }


            }
        });

        Thread.sleep(10000000);
    }

    @Test
    public void test() {

//        String str = "{\"data\":{\"count\":1521,\"list\":[{\"symbol\":\"SH603169\",\"net_profit_cagr\":null,\"ps\":1.783413830309362,\"type\":11,\"percent\":10.1,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":3.206,\"float_shares\":1043676438,\"current\":5.45,\"amplitude\":9.9,\"pcf\":6.121134059774692,\"current_year_percent\":-6.68,\"float_market_capital\":5688036587,\"market_capital\":5730688767,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":-0.2667033728058901,\"total_percent\":339.34,\"percent5m\":0,\"income_cagr\":-5.196084290485958,\"amount\":237081529,\"chg\":0.5,\"issue_date_ts\":1412784000000,\"main_net_inflows\":-64213191,\"volume\":44203307,\"volume_ratio\":1.76,\"pb\":3.206,\"followers\":27243,\"turnover_rate\":4.24,\"first_percent\":44.05,\"name\":\"兰石重装\",\"pe_ttm\":null,\"total_shares\":1051502526},{\"symbol\":\"SH600527\",\"net_profit_cagr\":0.649770584883469,\"ps\":5.677192449548559,\"type\":11,\"percent\":10.09,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.547,\"float_shares\":1443760902,\"current\":3.49,\"amplitude\":12.93,\"pcf\":48.14271588379887,\"current_year_percent\":107.05,\"float_market_capital\":5038725548,\"market_capital\":6043845548,\"dividend_yield\":2.388,\"lot_size\":100,\"roe_ttm\":3.3698202609252332,\"total_percent\":801.81,\"percent5m\":0,\"income_cagr\":-17.91222855345366,\"amount\":844400212.81,\"chg\":0.32,\"issue_date_ts\":1069862400000,\"main_net_inflows\":-32632513,\"volume\":258148338,\"volume_ratio\":1.78,\"pb\":2.547,\"followers\":22329,\"turnover_rate\":17.88,\"first_percent\":70.65,\"name\":\"江南高纤\",\"pe_ttm\":74.527,\"total_shares\":1731760902},{\"symbol\":\"SH600551\",\"net_profit_cagr\":-25.21110846517216,\"ps\":0.960069760803592,\"type\":11,\"percent\":10.05,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":1.21,\"float_shares\":505825296,\"current\":11.06,\"amplitude\":10.55,\"pcf\":16.009953700528655,\"current_year_percent\":34.88,\"float_market_capital\":5594427774,\"market_capital\":5594427774,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":3.891441760097214,\"total_percent\":91.97,\"percent5m\":0,\"income_cagr\":-15.383341197388411,\"amount\":280514605.69,\"chg\":1.01,\"issue_date_ts\":1031155200000,\"main_net_inflows\":-12598800,\"volume\":25692532,\"volume_ratio\":2.02,\"pb\":1.21,\"followers\":31251,\"turnover_rate\":5.08,\"first_percent\":428.25,\"name\":\"时代出版\",\"pe_ttm\":31.333,\"total_shares\":505825296},{\"symbol\":\"SH603488\",\"net_profit_cagr\":-6.500851558501964,\"ps\":7.063650353512334,\"type\":11,\"percent\":10.05,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.791,\"float_shares\":291990440,\"current\":8.54,\"amplitude\":0,\"pcf\":45.90356149065163,\"current_year_percent\":40.26,\"float_market_capital\":2493598358,\"market_capital\":2498098596,\"dividend_yield\":2.342,\"lot_size\":100,\"roe_ttm\":9.600971561741554,\"total_percent\":16.05,\"percent5m\":0,\"income_cagr\":-2.2979567070605667,\"amount\":2623658.8,\"chg\":0.78,\"issue_date_ts\":1494864000000,\"main_net_inflows\":0,\"volume\":307220,\"volume_ratio\":0.05,\"pb\":2.791,\"followers\":2880,\"turnover_rate\":0.11,\"first_percent\":43.94,\"name\":\"展鹏科技\",\"pe_ttm\":29.918,\"total_shares\":292517400},{\"symbol\":\"SH600107\",\"net_profit_cagr\":257.85567066208756,\"ps\":10.760281665871185,\"type\":11,\"percent\":10.04,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":7.037,\"float_shares\":360000000,\"current\":11.4,\"amplitude\":9.85,\"pcf\":102.35448452418187,\"current_year_percent\":67.4,\"float_market_capital\":4104000000,\"market_capital\":4104000000,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":4.062180726374176,\"total_percent\":51.32,\"percent5m\":0,\"income_cagr\":-19.214379736460263,\"amount\":867052189.75,\"chg\":1.04,\"issue_date_ts\":878745600000,\"main_net_inflows\":-62558607.80000001,\"volume\":78236290,\"volume_ratio\":1.59,\"pb\":7.037,\"followers\":19461,\"turnover_rate\":21.73,\"first_percent\":0,\"name\":\"美尔雅\",\"pe_ttm\":176.448,\"total_shares\":360000000},{\"symbol\":\"SH600558\",\"net_profit_cagr\":null,\"ps\":1.2100662777297537,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":1.553,\"float_shares\":897604831,\"current\":3.51,\"amplitude\":11.29,\"pcf\":null,\"current_year_percent\":7.67,\"float_market_capital\":3150592957,\"market_capital\":3150592957,\"dividend_yield\":0.855,\"lot_size\":100,\"roe_ttm\":3.627809366657321,\"total_percent\":52.16,\"percent5m\":0,\"income_cagr\":-0.39050959457024303,\"amount\":161755187.78,\"chg\":0.32,\"issue_date_ts\":983203200000,\"main_net_inflows\":2999295,\"volume\":48225530,\"volume_ratio\":2.05,\"pb\":1.553,\"followers\":27569,\"turnover_rate\":5.37,\"first_percent\":123.33,\"name\":\"大西洋\",\"pe_ttm\":43.887,\"total_shares\":897604831},{\"symbol\":\"SH603128\",\"net_profit_cagr\":1.3712276420442482,\"ps\":0.9647837328205557,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.39,\"float_shares\":1012038353,\"current\":9.87,\"amplitude\":11.71,\"pcf\":37.033551281722794,\"current_year_percent\":56.42,\"float_market_capital\":9988818544,\"market_capital\":9988818544,\"dividend_yield\":0.507,\"lot_size\":100,\"roe_ttm\":8.39555423288963,\"total_percent\":169.66,\"percent5m\":0.1,\"income_cagr\":7.248572868749781,\"amount\":1266156581,\"chg\":0.9,\"issue_date_ts\":1338220800000,\"main_net_inflows\":-101289819,\"volume\":130876111,\"volume_ratio\":1.2,\"pb\":2.39,\"followers\":20224,\"turnover_rate\":12.93,\"first_percent\":20.87,\"name\":\"华贸物流\",\"pe_ttm\":29.42,\"total_shares\":1012038353},{\"symbol\":\"SH600127\",\"net_profit_cagr\":-47.35013711334698,\"ps\":1.9863546040620332,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":11.549,\"float_shares\":641783218,\"current\":13.05,\"amplitude\":8.77,\"pcf\":null,\"current_year_percent\":225.44,\"float_market_capital\":8375270995,\"market_capital\":8375270995,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":-5.405429395045742,\"total_percent\":298.95,\"percent5m\":0,\"income_cagr\":18.555394824465353,\"amount\":2863327337.48,\"chg\":1.19,\"issue_date_ts\":894384000000,\"main_net_inflows\":88035273,\"volume\":229262344,\"volume_ratio\":1.73,\"pb\":11.549,\"followers\":65785,\"turnover_rate\":35.72,\"first_percent\":0,\"name\":\"金健米业\",\"pe_ttm\":null,\"total_shares\":641783218},{\"symbol\":\"SH603299\",\"net_profit_cagr\":-8.92945198449382,\"ps\":null,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":null,\"float_shares\":559440000,\"current\":7.46,\"amplitude\":10.91,\"pcf\":null,\"current_year_percent\":9.76,\"float_market_capital\":4173422400,\"market_capital\":5786952171,\"dividend_yield\":1.504,\"lot_size\":100,\"roe_ttm\":4.794717986041399,\"total_percent\":32.9,\"percent5m\":0,\"income_cagr\":7.005516812370138,\"amount\":205480216,\"chg\":0.68,\"issue_date_ts\":1451491200000,\"main_net_inflows\":-25233889,\"volume\":28741469,\"volume_ratio\":2.45,\"pb\":1.432,\"followers\":37712,\"turnover_rate\":5.14,\"first_percent\":43.9,\"name\":\"苏盐井神\",\"pe_ttm\":30.473,\"total_shares\":775730854},{\"symbol\":\"SH600956\",\"net_profit_cagr\":11.711866407969374,\"ps\":4.1622630032797465,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":4.442,\"float_shares\":134750000,\"current\":13.06,\"amplitude\":0,\"pcf\":13.601676102863234,\"current_year_percent\":310.69,\"float_market_capital\":1759835000,\"market_capital\":50279829772,\"dividend_yield\":null,\"lot_size\":100,\"roe_ttm\":11.384499884272694,\"total_percent\":241.88,\"percent5m\":0,\"income_cagr\":20.213959166664154,\"amount\":10000211.78,\"chg\":1.19,\"issue_date_ts\":1593360000000,\"main_net_inflows\":-2912758.74,\"volume\":765713,\"volume_ratio\":1.35,\"pb\":4.442,\"followers\":3805,\"turnover_rate\":0.57,\"first_percent\":44.03,\"name\":\"新天绿能\",\"pe_ttm\":36.818,\"total_shares\":3849910396}]},\"error_code\":0,\"error_description\":\"\"}\n" +
//                "{\"data\":{\"items\":[{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SH000001\",\"code\":\"000001\",\"exchange\":\"SH\",\"name\":\"上证指数\",\"type\":12,\"sub_type\":null,\"status\":1,\"current\":3414.62,\"currency\":\"CNY\",\"percent\":-0.83,\"chg\":-28.67,\"timestamp\":1594710000000,\"time\":1594710000000,\"lot_size\":100,\"tick_size\":0.01,\"open\":3435.0237,\"last_close\":3443.2863,\"high\":3451.2224,\"low\":3366.0828,\"avg_price\":3414.62,\"volume\":54321149200,\"amount\":707327005157.3,\"turnover_rate\":1.52,\"amplitude\":2.47,\"market_capital\":42509622657244.82,\"float_market_capital\":25239885076364.88,\"total_shares\":12449298211,\"float_shares\":3582193959225,\"issue_date\":661536000000,\"lock_set\":0,\"current_year_percent\":11.95},\"tags\":[]},{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SZ399001\",\"code\":\"399001\",\"exchange\":\"SZ\",\"name\":\"深证成指\",\"type\":12,\"sub_type\":null,\"status\":1,\"current\":13996.46,\"currency\":\"CNY\",\"percent\":-1.08,\"chg\":-152.68,\"timestamp\":1594710243000,\"time\":1594710243000,\"lot_size\":100,\"tick_size\":0.01,\"open\":14121.33,\"last_close\":14149.14,\"high\":14151,\"low\":13698.27,\"avg_price\":13996.46,\"volume\":73834211632,\"amount\":997206841399,\"turnover_rate\":4.12,\"amplitude\":3.2,\"market_capital\":32320303793349.2,\"float_market_capital\":10718549912440.8,\"total_shares\":2309177020,\"float_shares\":1794090692286,\"issue_date\":774633600000,\"lock_set\":null,\"current_year_percent\":34.18},\"tags\":[]},{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SH000300\",\"code\":\"000300\",\"exchange\":\"SH\",\"name\":\"沪深300\",\"type\":12,\"sub_type\":\"\",\"status\":1,\"current\":4806.69,\"currency\":\"CNY\",\"percent\":-0.95,\"chg\":-46.27,\"timestamp\":1594710000000,\"time\":1594710000000,\"lot_size\":100,\"tick_size\":0.01,\"open\":4836.1655,\"last_close\":4852.9612,\"high\":4860.4404,\"low\":4729.9258,\"avg_price\":4806.69,\"volume\":29724198500,\"amount\":511211048184.9,\"turnover_rate\":1.08,\"amplitude\":2.69,\"market_capital\":null,\"float_market_capital\":null,\"total_shares\":null,\"float_shares\":2760093466410,\"issue_date\":1104422400000,\"lock_set\":0,\"current_year_percent\":17.33},\"tags\":[]},{\"market\":{\"status_id\":7,\"region\":\"CN\",\"status\":\"已收盘\",\"time_zone\":\"Asia/Shanghai\",\"time_zone_desc\":null},\"quote\":{\"symbol\":\"SZ399006\",\"code\":\"399006\",\"exchange\":\"SZ\",\"name\":\"创业板指\",\"type\":12,\"sub_type\":null,\"status\":1,\"current\":2858.67,\"currency\":\"CNY\",\"percent\":-1.06,\"chg\":-30.76,\"timestamp\":1594710243000,\"time\":1594710243000,\"lot_size\":100,\"tick_size\":0.01,\"open\":2876.85,\"last_close\":2889.43,\"high\":2892.89,\"low\":2789.04,\"avg_price\":2858.67,\"volume\":18022310046,\"amount\":323825036559,\"turnover_rate\":5.45,\"amplitude\":3.59,\"market_capital\":9557094495240.45,\"float_market_capital\":1660019693983.44,\"total_shares\":3343196135,\"float_shares\":330650513858,\"issue_date\":1275235200000,\"lock_set\":null,\"current_year_percent\":58.98},\"tags\":[]}],\"items_size\":4},\"error_code\":0,\"error_description\":\"\"}";
    String str = "{\"data\":{\"count\":1521,\"list\":[{\"symbol\":\"SH603169\",\"net_profit_cagr\":null,\"ps\":1.783413830309362,\"type\":11,\"percent\":10.1,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":3.206,\"float_shares\":1043676438,\"current\":5.45,\"amplitude\":9.9,\"pcf\":6.121134059774692,\"current_year_percent\":-6.68,\"float_market_capital\":5688036587,\"market_capital\":5730688767,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":-0.2667033728058901,\"total_percent\":339.34,\"percent5m\":0,\"income_cagr\":-5.196084290485958,\"amount\":237081529,\"chg\":0.5,\"issue_date_ts\":1412784000000,\"main_net_inflows\":-64213191,\"volume\":44203307,\"volume_ratio\":1.76,\"pb\":3.206,\"followers\":27243,\"turnover_rate\":4.24,\"first_percent\":44.05,\"name\":\"兰石重装\",\"pe_ttm\":null,\"total_shares\":1051502526},{\"symbol\":\"SH600527\",\"net_profit_cagr\":0.649770584883469,\"ps\":5.677192449548559,\"type\":11,\"percent\":10.09,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.547,\"float_shares\":1443760902,\"current\":3.49,\"amplitude\":12.93,\"pcf\":48.14271588379887,\"current_year_percent\":107.05,\"float_market_capital\":5038725548,\"market_capital\":6043845548,\"dividend_yield\":2.388,\"lot_size\":100,\"roe_ttm\":3.3698202609252332,\"total_percent\":801.81,\"percent5m\":0,\"income_cagr\":-17.91222855345366,\"amount\":844400212.81,\"chg\":0.32,\"issue_date_ts\":1069862400000,\"main_net_inflows\":-32632513,\"volume\":258148338,\"volume_ratio\":1.78,\"pb\":2.547,\"followers\":22329,\"turnover_rate\":17.88,\"first_percent\":70.65,\"name\":\"江南高纤\",\"pe_ttm\":74.527,\"total_shares\":1731760902},{\"symbol\":\"SH600551\",\"net_profit_cagr\":-25.21110846517216,\"ps\":0.960069760803592,\"type\":11,\"percent\":10.05,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":1.21,\"float_shares\":505825296,\"current\":11.06,\"amplitude\":10.55,\"pcf\":16.009953700528655,\"current_year_percent\":34.88,\"float_market_capital\":5594427774,\"market_capital\":5594427774,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":3.891441760097214,\"total_percent\":91.97,\"percent5m\":0,\"income_cagr\":-15.383341197388411,\"amount\":280514605.69,\"chg\":1.01,\"issue_date_ts\":1031155200000,\"main_net_inflows\":-12598800,\"volume\":25692532,\"volume_ratio\":2.02,\"pb\":1.21,\"followers\":31251,\"turnover_rate\":5.08,\"first_percent\":428.25,\"name\":\"时代出版\",\"pe_ttm\":31.333,\"total_shares\":505825296},{\"symbol\":\"SH603488\",\"net_profit_cagr\":-6.500851558501964,\"ps\":7.063650353512334,\"type\":11,\"percent\":10.05,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.791,\"float_shares\":291990440,\"current\":8.54,\"amplitude\":0,\"pcf\":45.90356149065163,\"current_year_percent\":40.26,\"float_market_capital\":2493598358,\"market_capital\":2498098596,\"dividend_yield\":2.342,\"lot_size\":100,\"roe_ttm\":9.600971561741554,\"total_percent\":16.05,\"percent5m\":0,\"income_cagr\":-2.2979567070605667,\"amount\":2623658.8,\"chg\":0.78,\"issue_date_ts\":1494864000000,\"main_net_inflows\":0,\"volume\":307220,\"volume_ratio\":0.05,\"pb\":2.791,\"followers\":2880,\"turnover_rate\":0.11,\"first_percent\":43.94,\"name\":\"展鹏科技\",\"pe_ttm\":29.918,\"total_shares\":292517400},{\"symbol\":\"SH600107\",\"net_profit_cagr\":257.85567066208756,\"ps\":10.760281665871185,\"type\":11,\"percent\":10.04,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":7.037,\"float_shares\":360000000,\"current\":11.4,\"amplitude\":9.85,\"pcf\":102.35448452418187,\"current_year_percent\":67.4,\"float_market_capital\":4104000000,\"market_capital\":4104000000,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":4.062180726374176,\"total_percent\":51.32,\"percent5m\":0,\"income_cagr\":-19.214379736460263,\"amount\":867052189.75,\"chg\":1.04,\"issue_date_ts\":878745600000,\"main_net_inflows\":-62558607.80000001,\"volume\":78236290,\"volume_ratio\":1.59,\"pb\":7.037,\"followers\":19461,\"turnover_rate\":21.73,\"first_percent\":0,\"name\":\"美尔雅\",\"pe_ttm\":176.448,\"total_shares\":360000000},{\"symbol\":\"SH600558\",\"net_profit_cagr\":null,\"ps\":1.2100662777297537,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":1.553,\"float_shares\":897604831,\"current\":3.51,\"amplitude\":11.29,\"pcf\":null,\"current_year_percent\":7.67,\"float_market_capital\":3150592957,\"market_capital\":3150592957,\"dividend_yield\":0.855,\"lot_size\":100,\"roe_ttm\":3.627809366657321,\"total_percent\":52.16,\"percent5m\":0,\"income_cagr\":-0.39050959457024303,\"amount\":161755187.78,\"chg\":0.32,\"issue_date_ts\":983203200000,\"main_net_inflows\":2999295,\"volume\":48225530,\"volume_ratio\":2.05,\"pb\":1.553,\"followers\":27569,\"turnover_rate\":5.37,\"first_percent\":123.33,\"name\":\"大西洋\",\"pe_ttm\":43.887,\"total_shares\":897604831},{\"symbol\":\"SH603128\",\"net_profit_cagr\":1.3712276420442482,\"ps\":0.9647837328205557,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":2.39,\"float_shares\":1012038353,\"current\":9.87,\"amplitude\":11.71,\"pcf\":37.033551281722794,\"current_year_percent\":56.42,\"float_market_capital\":9988818544,\"market_capital\":9988818544,\"dividend_yield\":0.507,\"lot_size\":100,\"roe_ttm\":8.39555423288963,\"total_percent\":169.66,\"percent5m\":0.1,\"income_cagr\":7.248572868749781,\"amount\":1266156581,\"chg\":0.9,\"issue_date_ts\":1338220800000,\"main_net_inflows\":-101289819,\"volume\":130876111,\"volume_ratio\":1.2,\"pb\":2.39,\"followers\":20224,\"turnover_rate\":12.93,\"first_percent\":20.87,\"name\":\"华贸物流\",\"pe_ttm\":29.42,\"total_shares\":1012038353},{\"symbol\":\"SH600127\",\"net_profit_cagr\":-47.35013711334698,\"ps\":1.9863546040620332,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":11.549,\"float_shares\":641783218,\"current\":13.05,\"amplitude\":8.77,\"pcf\":null,\"current_year_percent\":225.44,\"float_market_capital\":8375270995,\"market_capital\":8375270995,\"dividend_yield\":0,\"lot_size\":100,\"roe_ttm\":-5.405429395045742,\"total_percent\":298.95,\"percent5m\":0,\"income_cagr\":18.555394824465353,\"amount\":2863327337.48,\"chg\":1.19,\"issue_date_ts\":894384000000,\"main_net_inflows\":88035273,\"volume\":229262344,\"volume_ratio\":1.73,\"pb\":11.549,\"followers\":65785,\"turnover_rate\":35.72,\"first_percent\":0,\"name\":\"金健米业\",\"pe_ttm\":null,\"total_shares\":641783218},{\"symbol\":\"SH603299\",\"net_profit_cagr\":-8.92945198449382,\"ps\":null,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":null,\"float_shares\":559440000,\"current\":7.46,\"amplitude\":10.91,\"pcf\":null,\"current_year_percent\":9.76,\"float_market_capital\":4173422400,\"market_capital\":5786952171,\"dividend_yield\":1.504,\"lot_size\":100,\"roe_ttm\":4.794717986041399,\"total_percent\":32.9,\"percent5m\":0,\"income_cagr\":7.005516812370138,\"amount\":205480216,\"chg\":0.68,\"issue_date_ts\":1451491200000,\"main_net_inflows\":-25233889,\"volume\":28741469,\"volume_ratio\":2.45,\"pb\":1.432,\"followers\":37712,\"turnover_rate\":5.14,\"first_percent\":43.9,\"name\":\"苏盐井神\",\"pe_ttm\":30.473,\"total_shares\":775730854},{\"symbol\":\"SH600956\",\"net_profit_cagr\":11.711866407969374,\"ps\":4.1622630032797465,\"type\":11,\"percent\":10.03,\"has_follow\":false,\"tick_size\":0.01,\"pb_ttm\":4.442,\"float_shares\":134750000,\"current\":13.06,\"amplitude\":0,\"pcf\":13.601676102863234,\"current_year_percent\":310.69,\"float_market_capital\":1759835000,\"market_capital\":50279829772,\"dividend_yield\":null,\"lot_size\":100,\"roe_ttm\":11.384499884272694,\"total_percent\":241.88,\"percent5m\":0,\"income_cagr\":20.213959166664154,\"amount\":10000211.78,\"chg\":1.19,\"issue_date_ts\":1593360000000,\"main_net_inflows\":-2912758.74,\"volume\":765713,\"volume_ratio\":1.35,\"pb\":4.442,\"followers\":3805,\"turnover_rate\":0.57,\"first_percent\":44.03,\"name\":\"新天绿能\",\"pe_ttm\":36.818,\"total_shares\":3849910396}]},\"error_code\":0,\"error_description\":”\"}";

        //        mockResponseJson(str,replaceNum[num.nextInt(replaceNum.length)],"num");
        str = mockResponseJson(str,replaceString[num.nextInt(replaceString.length)],"object");
            System.out.println(str);
    }



    private String mockResponseJson(String originJson, String replaceStr, String type) {
        /*
         * 1.输入数据可能是多个json体，需要拆分处理
         * 当接口数据是json的数字的时候，自动mock为对应的等价类 -10 -0.5 -1 0 0.5 1 10
           当接口数据是json的string的时候，自动mock为对应的等价类 ”“ 中文 英文 特殊字符 null
           当接口数据是json的对象的时候，自动mock为对应的异常数据为 null
           当接口数据是json的array的时候，自动mock为对应的等价类 0 1 10 100
           2.当响应内容类型不是json时，不替换
         * */
        //1.指定数字的匹配pattern，匹配规则
        if (originJson != null && "num".equals(type.toLowerCase())) {
            Pattern p = Pattern.compile("(:[0-9]\\d*\\.?\\d*)|(:(-)?[0-9]\\d*\\.?\\d*)");
            Matcher m = p.matcher(originJson);
            originJson = m.replaceAll(":"+replaceStr);
            System.out.println("使用 "+replaceStr+" 进行替换");
        }else if (originJson != null && "string".equals(type.toLowerCase())){
            Pattern p = Pattern.compile("(:\".+?\")");
            Matcher m = p.matcher(originJson);
            originJson = m.replaceAll((":\""+replaceStr+"\""));
            System.out.println("使用 "+replaceStr+" 进行替换");
        }else if (originJson != null && "object".equals(type.toLowerCase())){

            //用正则搞不定
        }else if (originJson != null && "array".equals(type.toLowerCase())) {
            //用正则搞不定
        }
            return originJson;

    }

}

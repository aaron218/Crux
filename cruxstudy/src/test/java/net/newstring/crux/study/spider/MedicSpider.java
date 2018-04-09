package net.newstring.crux.study.spider;

import com.dtwave.spiluxio.node.LocalSpider;
import com.dtwave.spiluxio.node.Page;
import com.dtwave.spiluxio.node.executor.Context;
import com.dtwave.spiluxio.node.processor.PageProcessor;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MedicSpider
 *
 * @author lic
 * @date 2018/1/30
 */
public class MedicSpider implements PageProcessor {
    private final static String splitter = "@";

    private AtomicInteger num = new AtomicInteger(0);
    private static final String startUrl1 = "http://www.101711.com/daohang/jiankang";
    private static final String startUrl2 = "http://www.2345daohang.com/jiankang/#yiyuan";
    private static final String startUrl3 = "http://www.12345h.com/Yiliao_Baojian/index.html";
    private static final String startUrl4 = "http://123.91.com/jiankang/";
    private static final String startUrl5 = "http://life.hao123.com/health";
    private static final String startUrl6 = "http://www.3456.cc/Yiliao_Baojian";
    private static final String startUrl7 = "http://www.265.com/Yiliao_Baojian/";

    private static Map<String, String> values = new ConcurrentHashMap<>();

    @Override
    public void process(Page page, Context context) throws Exception {
        Logger logger = context.getLogger();
        System.out.println("start parse page url:" + page.getRequest().getUrl() + " task num:" + num.incrementAndGet());
        int selfNum = num.get();
        if (page.getStatusCode() != 200) {
            try {
                System.out.println("页面获取数据失败 code:" + page.getStatusCode());
                context.addRequestDirect(page.getRequest());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            if (page.getRequest().getUrl().contains("http://www.101711.com/daohang/jiankang")) {
                medic360(page, values);
            } else if (page.getRequest().getUrl().contains("http://www.2345daohang.com/jiankang/#yiyuan")) {
                medic2345(page, values);
            } else if (page.getRequest().getUrl().contains("http://www.12345h.com")) {
                medic12345h(page, values);
            } else if (page.getRequest().getUrl().contains("http://123.91.com/jiankang")) {
                medic12391(page, values);
            } else if (page.getRequest().getUrl().contains("http://life.hao123.com/health")) {
                medichao123(page, values);
            } else if (page.getRequest().getUrl().contains("http://www.265.com/Yiliao_Baojian")) {
                medic256(page, values);
            } else if (page.getRequest().getUrl().contains("http://www.3456.cc/Yiliao_Baojian")) {
                medic3456(page, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num.get() == 7) {
            System.out.println(num.get() + "=====================获取到的网址总数：" + values.size());
            values.forEach((key, value) -> System.out.println(key + "@" + value));
        }
    }


    private void medic2345(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        //2345只要前面三类数据
        for (int h = 1; h < 4; h++) {
            String h1 = String.format("html/body/div[6]/h2[%d]", h);
            String text = "2345@";
            if (page.getHtml().xpath(h1).match()) {
                text += page.getHtml().xpath(h1).getText();
                //2345的表格内再次分割
                for (int j = 1; j < 5; j++) {
                    for (int i = 1; i < 100; i++) {
                        String reg = String.format("//html/body/div[6]/table[%d]/tbody/tr[%d]/td[%s]/a", h, j, i);
                        if (page.getHtml().xpath(reg).match()) {
                            String text1 = page.getHtml().xpath(reg).getText();
                            if(values.keySet().contains(text1)){
                                continue;
                            }
                            text1 = text + splitter + text1;
                            String link = page.getHtml().xpath(reg).links().get().split("=")[1];
                            link = URLDecoder.decode(link, "UTF-8");
                            link = link.replaceAll("http://", "");
                            if(values.values().contains(link)){
                                continue;
                            }
                            values.putIfAbsent(text1, link);
                        }
                    }
                }
            }
        }
    }

    private void medic360(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 6; h++) {
            String h1 = String.format("//*[@id=\"content\"]/div/h2[%d]", h);
            String text = "101711@";
            if (page.getHtml().xpath(h1).match()) {
                text += page.getHtml().xpath(h1).getText();
                for (int i = 1; i < 200; i++) {
                    String reg = String.format("//*[@id=\"content\"]/div/ul[%d]/li[%d]/a", h, i);
                    if (page.getHtml().xpath(reg).match()) {
                        String text1 = page.getHtml().xpath(reg).getText();
                        if(values.keySet().contains(text1)){
                            continue;
                        }
                        text1 = text + splitter + text1;
                        String link = page.getHtml().xpath(reg).links().get();
                        link = URLDecoder.decode(link, "UTF-8");
                        link = link.replaceAll("http://", "");
                        if(values.values().contains(link)){
                            continue;
                        }
                        values.putIfAbsent(text1, link);
                    }
                }
            }
        }
    }

    private void medic256(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 6; h++) {
            String h1 = String.format("/html/body/div[2]/div[2]/div/div[2]/div[1]/div/div/div[%d]/div[1]/div[2]/span", h);
            String text = "256@";
            if (page.getHtml().xpath(h1).match()) {
                text += page.getHtml().xpath(h1).getText();
                for (int i = 1; i < 200; i++) {
                    String reg = String.format("/html/body/div[2]/div[2]/div/div[2]/div[1]/div/div/div[%d]/div[2]/div[1]/div/div[%d]/a", h, i);
                    if (page.getHtml().xpath(reg).match()) {
                        String text1 = page.getHtml().xpath(reg).getText();
                        if(values.keySet().contains(text1)){
                            continue;
                        }
                        text1 = text + splitter + text1;
                        String link = page.getHtml().xpath(reg).links().get();
                        link = URLDecoder.decode(link, "UTF-8");
                        link = link.replaceAll("http://", "");
                        if(values.values().contains(link)){
                            continue;
                        }
                        values.putIfAbsent(text1, link);
                    }
                }
            }
        }
    }


    private void medic12345h(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 6; h++) {
            String h1 = String.format("//*[@id=\"listurl\"]/div[%d]/div[1]/ul/li[1]/a", h);
            String text = "12345h@";
            if (page.getHtml().xpath(h1).match()) {
                text += page.getHtml().xpath(h1).getText();
                for (int i = 2; i < 200; i++) {
                    String reg = String.format("//*[@id=\"listurl\"]/div[%d]/div[2]/ul/li[%d]/a", h, i);
                    if (page.getHtml().xpath(reg).match()) {
                        String text1 = page.getHtml().xpath(reg).getText();
                        if(values.keySet().contains(text1)){
                            continue;
                        }
                        text1 = text + splitter + text1;
                        String link = page.getHtml().xpath(reg).links().get();
                        link = URLDecoder.decode(link, "UTF-8");
                        link = link.replaceAll("http://", "");
                        if(values.values().contains(link)){
                            continue;
                        }
                        values.putIfAbsent(text1, link);
                    }
                }
            }
        }
    }


    private void medic3456(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 4; h++) {
            String h1 = String.format("//*[@id=\"listurl\"]/div[%d]/div[1]/ul/li[1]/a", h);
            String text = "3456@";
            if (page.getHtml().xpath(h1).match()) {
                text += page.getHtml().xpath(h1).getText();
                for (int i = 2; i < 200; i++) {
                    String reg = String.format("//*[@id=\"listurl\"]/div[%d]/div[2]/ul/li[%d]/a[2]", h, i);
                    if (page.getHtml().xpath(reg).match()) {
                        String text1 = page.getHtml().xpath(reg).getText();
                        if(values.keySet().contains(text1)){
                            continue;
                        }
                        text1 = text + splitter + text1;
                        String link = page.getHtml().xpath(reg).links().get();
                        link = URLDecoder.decode(link, "UTF-8");
                        link = link.replaceAll("http://", "");
                        if(values.values().contains(link)){
                            continue;
                        }
                        values.putIfAbsent(text1, link);
                    }
                }
            }
        }
    }

    private void medichao123(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 6; h++) {
//            String h1 = String.format("//*[@id=\"listurl\"]/div[%d]/div[1]/ul/li[1]/a", h);
            String text = "hao123@";
            for (int i = 2; i < 200; i++) {
                String reg = String.format("/html/body/div[2]/div/ul[%d]/li[2]/ul/li[%d]/a", h, i);
                if (page.getHtml().xpath(reg).match()) {
                    String text1 = page.getHtml().xpath(reg).getText();
                    if(values.keySet().contains(text1)){
                        continue;
                    }
                    text1 = text + splitter + text1;
                    String link = page.getHtml().xpath(reg).links().get();
                    link = URLDecoder.decode(link, "UTF-8");
                    link = link.replaceAll("http://", "");
                    if(values.values().contains(link)){
                        continue;
                    }
                    values.putIfAbsent(text1, link);
                }
            }
        }
    }

    private void medic12391(Page page, Map<String, String> values) throws UnsupportedEncodingException {
        for (int h = 1; h < 6; h++) {
            String text = "12391@";
            String reg = String.format("//*[@id=\"list\"]/a[%d]", h);
            if (page.getHtml().xpath(reg).match()) {
                String text1 = page.getHtml().xpath(reg).getText();
                if(values.keySet().contains(text1)){
                    continue;
                }
                text1 = text + splitter + text1;
                String link = page.getHtml().xpath(reg).links().get();
                link = URLDecoder.decode(link, "UTF-8");
                link = link.replaceAll("http://", "");
                if(values.values().contains(link)){
                    continue;
                }
                values.putIfAbsent(text1, link);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        LocalSpider spider = LocalSpider.create()
                .domain("www.laws12348.com")
                .bizId("bid_www_laws12348_com")
                .userId("donghui.zdh.zcx")
                .pageProcessor(new MedicSpider())
                .taskNum(100)
                .depth(-1)
                .pageType(1)
                .persist("/Volumes/STUDY/dtwave/projects/spiluxio-parent")
                .startUrls(Arrays.asList(startUrl1, startUrl2, startUrl3, startUrl4,startUrl5,startUrl6,startUrl7));
        spider.run();

        System.out.println("=============================\n 最终结果总数：" + values.size());
    }
}

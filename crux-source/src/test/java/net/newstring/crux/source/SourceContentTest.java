package net.newstring.crux.source;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;

/**
 * SourceContentTest
 *
 * @author lic
 * @date 2018/2/6
 */
public class SourceContentTest {

    @Test
    public void test() throws Exception {
        Document document = Jsoup.parse(new File("C:\\Users\\DELL\\OneDrive\\work\\数澜科技平台以及基本文件\\开发文件\\爬虫\\demo_html\\10.htm"),"GBK");
        News news = ContentExtractor.getNewsByDoc(document);
        System.out.println(news);
    }
}

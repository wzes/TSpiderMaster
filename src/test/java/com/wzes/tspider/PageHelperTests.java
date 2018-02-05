package com.wzes.tspider;

import com.wzes.tspider.module.spider.*;
import com.wzes.tspider.service.listener.OnCrawlListener;
import com.wzes.tspider.service.spider.CommonSpider;
import com.wzes.tspider.service.spider.PageHelper;
import com.wzes.tspider.service.spider.TSpiderProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/3/18
 */
public class PageHelperTests {
    @Test
    public void SseTest() {
        String html = PageHelper.get("http://sse.tongji.edu.cn/Data/List/xyxw");
        //System.out.println(html);
        List<String> XPaths = new ArrayList<>();
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[1]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[2]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[3]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[4]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[5]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[6]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[7]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[8]/a");

        List<String> values = CommonSpider.getValuesFromPage("http://sse.tongji.edu.cn/Data/List/xyxw",
                html, ExtractType.EXTRACT_LINK, XPaths);
        for(String val : values) {
            System.out.println(val);
        }
    }
}

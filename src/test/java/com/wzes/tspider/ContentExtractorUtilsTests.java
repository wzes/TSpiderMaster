package com.wzes.tspider;

import com.wzes.tspider.module.spider.ExtractType;
import com.wzes.tspider.service.spider.ExtractUtils;
import com.wzes.tspider.service.spider.PageUtils;
import com.wzes.tspider.util.ContentExtractorUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/3/18
 */
public class ContentExtractorUtilsTests {
    @Test
    public void SseTest() {
        String html = PageUtils.get("http://sse.tongji.edu.cn/Data/View/3359");
        try {
            System.out.println(ContentExtractorUtils.getContentByHtml(html));
            System.out.println(ContentExtractorUtils.getTitleByHtml(html));
            System.out.println(ContentExtractorUtils.getDateByHtml(html));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

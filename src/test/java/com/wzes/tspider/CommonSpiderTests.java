package com.wzes.tspider;

import com.wzes.tspider.module.spider.*;
import com.wzes.tspider.service.listener.OnCrawlListener;
import com.wzes.tspider.service.spider.TSpiderProcessor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/3/18
 */
public class CommonSpiderTests {
    @Test
    public void SseTest() {
        Task.Builder builder = new Task.Builder();

        // item
        ExtractItem item = new ExtractItem();
        item.setName("title");
        item.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> xpaths = new ArrayList<>();
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[1]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[2]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[3]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[4]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[5]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[6]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[7]/a");
        xpaths.add("/html/body/div[3]/div/div[3]/div/ul/li[8]/a");
        item.setXpaths(xpaths);

        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setXpaths(xpaths);

        List<ExtractItem> items = new ArrayList<>();
        items.add(item);
        items.add(itemLink);
        // extractRule
        ExtractRule extractRule = new ExtractRule();
        extractRule.setExtractItems(items);
        // listener
        extractRule.setOnCrawlListener(new OnCrawlListener() {
            @Override
            public void onNext(Result result) {
                result.show();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        // config
        Config config = new Config();
        config.setTimeout(10000);
        Task task = builder.url("http://sse.tongji.edu.cn/Data/List/xyxw")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=2")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=3")
                .type(PageType.PAGE_COMMON)
                .config(config)
                .rule(extractRule)
                .build();
        // start
        TSpiderProcessor.Builder builder1 = new TSpiderProcessor.Builder();
        builder1.task(task)
                .build()
                .start();
    }
}

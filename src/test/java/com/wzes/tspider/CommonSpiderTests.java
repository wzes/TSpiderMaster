package com.wzes.tspider;

import com.alibaba.fastjson.JSON;
import com.wzes.tspider.module.SpiderConfig;
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
        // --------------------------------------------------------------------//
        // item title
        ExtractItem item = new ExtractItem();
        item.setName("title");
        item.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> XPaths = new ArrayList<>();
        XPaths.add("//*[@id=\"1\"]/h3/a");
        XPaths.add("//*[@id=\"2\"]/h3/a");
        XPaths.add("//*[@id=\"3\"]/h3/a");
        XPaths.add("//*[@id=\"4\"]/h3/a");
        XPaths.add("//*[@id=\"5\"]/h3/a");
        XPaths.add("//*[@id=\"6\"]/h3/a");
        XPaths.add("//*[@id=\"7\"]/h3/a");
        XPaths.add("//*[@id=\"8\"]/h3/a");
        XPaths.add("//*[@id=\"9\"]/h3/a");
        XPaths.add("//*[@id=\"10\"]/h3/a");
        XPaths.add("//*[@id=\"11\"]/h3/a");
        XPaths.add("//*[@id=\"12\"]/h3/a");
        item.setXpaths(XPaths);
        // item title link
        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setXpaths(XPaths);

        ExtractItem itemTime = new ExtractItem();
        itemTime.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> timeXPaths = new ArrayList<>();
        timeXPaths.add("//*[@id=\"1\"]/div/p");
        timeXPaths.add("//*[@id=\"2\"]/div/p");
        timeXPaths.add("//*[@id=\"3\"]/div/p");
        timeXPaths.add("//*[@id=\"4\"]/div/p");
        timeXPaths.add("//*[@id=\"5\"]/div/p");
        timeXPaths.add("//*[@id=\"6\"]/div/p");
        timeXPaths.add("//*[@id=\"7\"]/div/p");
        timeXPaths.add("//*[@id=\"8\"]/div/p");
        timeXPaths.add("//*[@id=\"9\"]/div/p");
        timeXPaths.add("//*[@id=\"10\"]/div/p");
        timeXPaths.add("//*[@id=\"11\"]/div/p");
        timeXPaths.add("//*[@id=\"12\"]/div/p");
        itemTime.setXpaths(timeXPaths);
        itemTime.setName("date");

        List<ExtractItem> items = new ArrayList<>();
        items.add(item);
        items.add(itemLink);
        items.add(itemTime);
        // --------------------------------------------------------------------//
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
        // --------------------------------------------------------------------//
        // config
        Config config = new Config();
        config.setTimeout(10000);
        Task task = builder.url("http://news.baidu.com/ns?word=习近平&tn=news&from=news&cl=2&rn=20&ct=1")
                .url("http://news.baidu.com/ns?word=习近平&pn=20&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .url("http://news.baidu.com/ns?word=习近平&pn=40&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .url("http://news.baidu.com/ns?word=习近平&pn=60&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .url("http://news.baidu.com/ns?word=习近平&pn=80&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .url("http://news.baidu.com/ns?word=习近平&pn=100&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .url("http://news.baidu.com/ns?word=习近平&pn=120&cl=2&ct=1&tn=news&rn=20&ie=utf-8&bt=0&et=0")
                .type(PageType.PAGE_COMMON)
                .config(config)
                .rule(extractRule)
                .numThreads(4)
                .build();

        String data = JSON.toJSONString(task);
        SpiderConfig spiderConfig = new SpiderConfig();
        spiderConfig.setData(data);
        System.out.println(JSON.toJSONString(spiderConfig));
        Task rTask = JSON.parseObject(data, Task.class);


        for (int index = 0; index < rTask.getExtractRules().size(); index++) {
            rTask.getExtractRules().get(index).setOnCrawlListener(new OnCrawlListener() {
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
        }

        //System.out.println(rTask.getNumThreads());
        // --------------------------------------------------------------------//
        // start
//        TSpiderProcessor.Builder builder1 = new TSpiderProcessor.Builder();
//        builder1.task(task)
//                .build()
//                .start();
    }
}

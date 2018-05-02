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
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[1]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[2]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[3]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[4]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[5]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[6]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[7]/a");
        XPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[8]/a");
        item.setXpaths(XPaths);
        // item title link
        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setXpaths(XPaths);

        ExtractItem itemTime = new ExtractItem();
        itemTime.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> timeXPaths = new ArrayList<>();
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[1]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[2]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[3]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[4]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[5]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[6]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[7]/span");
        timeXPaths.add("/html/body/div[3]/div/div[3]/div/ul/li[8]/span");
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
        Task task = builder.url("http://sse.tongji.edu.cn/Data/List/xyxw")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=2")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=3")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=4")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=5")
                .url("http://sse.tongji.edu.cn/Data/List/xyxw?page=6")
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

    @Test
    public void BaiduTest() {
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
        timeXPaths.add("//*[@id=\"8\"]/div/p");
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


    @Test
    public void SinaTest() {
        Task.Builder builder = new Task.Builder();
        // --------------------------------------------------------------------//
        // item title
        ExtractItem item = new ExtractItem();
        item.setName("title");
        item.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> XPaths = new ArrayList<>();
        XPaths.add("//*[@id=\"result\"]/div[1]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[2]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[3]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[4]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[5]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[6]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[7]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[8]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[9]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[10]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[11]/div/h2/a");
        XPaths.add("//*[@id=\"result\"]/div[12]/div/h2/a");
        item.setXpaths(XPaths);
        // item title link
        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setXpaths(XPaths);

        ExtractItem itemTime = new ExtractItem();
        itemTime.setExtractType(ExtractType.EXTRACT_TEXT);
        List<String> timeXPaths = new ArrayList<>();
        timeXPaths.add("//*[@id=\"result\"]/div[1]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[2]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[3]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[4]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[5]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[6]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[7]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[8]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[9]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[10]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[11]/div/h2/span");
        timeXPaths.add("//*[@id=\"result\"]/div[12]/div/h2/span");
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
        Task task = builder.url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=1&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=2&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=3&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=4&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=5&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=6&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=7&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=8&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=9&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=10&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=11&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=12&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=13&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=14&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=15&pf=2131425468&ps=2134309112&dpc=1")
                .url("http://search.sina.com.cn/?q=李琰&c=news&from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&page=16&pf=2131425468&ps=2134309112&dpc=1")
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

    @Test
    public void ZhihuTest() {
        Task.Builder builder = new Task.Builder();
        // item title
        ExtractItem item = new ExtractItem();
        item.setName("title");
        item.setExtractType(ExtractType.EXTRACT_TEXT);
        item.setSelector("body > div.main-content > div > div.main-content-wrap > div > div:nth-child(1) > div:nth-child(3) > div > a");
        // item title link
        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setSelector(item.getSelector());

        ExtractItem itemImg = new ExtractItem();
        itemImg.setExtractType(ExtractType.EXTRACT_IMAGE);

        itemImg.setSelector("body > div.main-content > div > div.main-content-wrap > div > div:nth-child(1) > div:nth-child(3) > div > a > img");
        itemImg.setName("img");

        List<ExtractItem> items = new ArrayList<>();
        items.add(item);
        items.add(itemLink);
        items.add(itemImg);

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
        Task task = builder.url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .url("http://daily.zhihu.com/")
                .config(config)
                .rule(extractRule)
                .numThreads(1)
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
    }


    @Test
    public void SEETest() {
        Task.Builder builder = new Task.Builder();
        // item title
        ExtractItem item = new ExtractItem();
        item.setName("title");
        item.setExtractType(ExtractType.EXTRACT_TEXT);
        item.setSelector("body > div.ny-con > div > div.main-right > div > ul > li:nth-child(1) > a");
        // item title link
        ExtractItem itemLink = new ExtractItem();
        itemLink.setName("title_link");
        itemLink.setExtractType(ExtractType.EXTRACT_LINK);
        itemLink.setSelector(item.getSelector());

        ExtractItem itemDate = new ExtractItem();
        itemDate.setExtractType(ExtractType.EXTRACT_TEXT);

        itemDate.setSelector("body > div.ny-con > div > div.main-right > div > ul > li:nth-child(1) > span");
        itemDate.setName("date");

        List<ExtractItem> items = new ArrayList<>();
        items.add(item);
        items.add(itemLink);
        items.add(itemDate);

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
        Task task = builder
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=1")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=2")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=3")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=4")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=5")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=6")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=7")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=8")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=9")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=10")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=11")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=12")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=13")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=14")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=15")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=16")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=17")
                .url("http://sse.tongji.edu.cn/data/list/xwdt?page=18")
                .config(config)
                .rule(extractRule)
                .numThreads(1)
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
    }
}

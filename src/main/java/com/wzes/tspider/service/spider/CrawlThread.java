package com.wzes.tspider.service.spider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.wzes.tspider.module.spider.ExtractItem;
import com.wzes.tspider.module.spider.ExtractRule;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Create by xuantang
 * @date on 2/4/18
 */
public class CrawlThread implements Runnable {
    private static Logger logger = Logger.getLogger(CrawlThread.class);
    private static final int WATING_TIME = 100;

    private Result[] results;
    private int ruleSize;
    private Task task;
    private CountDownLatch countDownLatch;

    public CrawlThread() {

    }

    /**
     * 根据规则的多少生成结果集
     * @param Task task
     */
    public CrawlThread(Task task, CountDownLatch countDownLatch) {
        this.task = task;
        this.ruleSize = task.getExtractRules().size();
        results = new Result[ruleSize];
        for (int index = 0; index < ruleSize; index++) {
            results[index] = new Result();
        }
        this.countDownLatch = countDownLatch;
    }

    /**
     *
     * @return
     */
    public Result[] getResults() {
        return results;
    }

    /**
     * 爬取
     */
    @Override
    public void run() {
        logger.debug(Thread.currentThread() + " start crawling");
        // 从仓库获取一条url
        String url;

        WebClient webClient = Spider.getCommonSpider(task.getConfig());

        while (!(url = UrlWarehouse.getInstance().getUrl()).isEmpty()) {
            HtmlPage page = null;
            logger.debug(Thread.currentThread() + " get page: " + url);
            //
            for (int index = 0; index < ruleSize; index++) {
                ExtractRule extractRule = task.getExtractRules().get(index);
                try {
                    if (page == null) {
                        page = webClient.getPage(url);
                    }
                } catch (IOException e) {
                    // 异常
                    if (extractRule.getOnCrawlListener() != null) {
                        extractRule.getOnCrawlListener().onError(e.getCause());
                    }
                    break;
                }
                //
                for (int j = 0; j < extractRule.getExtractItems().size(); j++) {
                    ExtractItem extractItem = extractRule.getExtractItems().get(j);
                    // 爬取内容
                    Result.Item item = Spider.getContent(page, extractItem);
                    // 内容追加
                    if (results[index].getItems() == null
                            || results[index].getItems().size() < extractRule.getExtractItems().size()) {
                        results[index].addItem(item);
                    } else {
                        results[index].getItems().get(j).addValues(item.getValues());
                    }
                }
            }
            try {
                Thread.sleep(WATING_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    }
}

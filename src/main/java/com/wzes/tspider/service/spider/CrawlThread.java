package com.wzes.tspider.service.spider;

import com.wzes.tspider.module.spider.ExtractItem;
import com.wzes.tspider.module.spider.ExtractRule;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;
import org.apache.log4j.Logger;

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
        logger.info(Thread.currentThread() + " start crawling");
        // 从仓库获取一条url
        String url;

        while (!(url = UrlWarehouse.getInstance().getUrl(task.getId())).isEmpty()) {
            //HtmlPage page = null;
            logger.info(Thread.currentThread() + " get page: " + url);
            String html = null;
            try {
                html = PageUtils.get(url);
            } catch (Exception e) {
                logger.error(Thread.currentThread() + " get page: " + url);
            }
            //
            for (int index = 0; index < ruleSize; index++) {
                ExtractRule extractRule = task.getExtractRules().get(index);
                // 页面获取失败
                if (html == null || html.isEmpty()) {
                    if (extractRule.getOnCrawlListener() != null) {
                        extractRule.getOnCrawlListener().onError(new NullPointerException());
                    }
                    break;
                }
                //
                for (int j = 0; j < extractRule.getExtractItems().size(); j++) {
                    ExtractItem extractItem = extractRule.getExtractItems().get(j);
                    // 爬取内容
                    Result.Item item = ExtractUtils.getContent(url, html, extractItem);
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
        logger.info(Thread.currentThread() + " end crawling");
        countDownLatch.countDown();
    }
}

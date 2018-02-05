package com.wzes.tspider.service.spider;

import com.wzes.tspider.module.spider.ExtractRule;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
public class ExtractProcessor {

    /**
     * 合并结果
     * @param crawlThreads 开启的线程集合
     * @param results 返回的结果集
     */
    private static void mergeResult(List<CrawlThread> crawlThreads, Result[] results) {
        // 执行完合并结果
        boolean first = true;
        for (CrawlThread crawlThread : crawlThreads) {
            Result[] threadResults = crawlThread.getResults();
            for (int index = 0; index < threadResults.length; index++) {
                if (first) {
                    results[index] = threadResults[index];
                    first = false;
                } else {
                    if (threadResults[index].getItems() != null) {
                        for (int j = 0; j < threadResults[index].getItems().size(); j++) {
                            results[index].getItems().get(j).addValues(
                                    threadResults[index].getItems().get(j).getValues());
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理结果
     * @param extractRules 爬取规则
     * @param results 返回的结果集
     */
    private static void HandleResult(List<ExtractRule> extractRules, Result[] results) {
        for (int index = 0; index < extractRules.size(); index++) {
            ExtractRule extractRule = extractRules.get(index);
            if (extractRule.getOnCrawlListener() != null) {
                extractRule.getOnCrawlListener().onNext(results[index]);
            }
            // 爬取结束
            if (extractRule.getOnCrawlListener() != null) {
                extractRule.getOnCrawlListener().onComplete();
            }
        }
    }


    /**
     * TODO 多线程爬取 边爬边写文件，最佳文件
     * 普通单个任务爬取
     * @param task task
     */
    public static void commonCrawl(Task task) {
        //
        List<ExtractRule> extractRules = task.getExtractRules();
        // init
        Result[] results = new Result[extractRules.size()];
        for (int index = 0; index < results.length; index++) {
            results[index] = new Result();
        }
        // 设置 url 仓库
        UrlWarehouse.getInstance().setUrls(task.getUrls());
        // 线程数
        int numOfThreads = task.getNumThreads();
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        final CountDownLatch countDown = new CountDownLatch(numOfThreads);
        List<CrawlThread> crawlThreads = new ArrayList<>();
        for (int index = 0; index < numOfThreads; index++) {
            // 创建线程
            CrawlThread crawlThread = new CrawlThread(task, countDown);
            crawlThreads.add(crawlThread);
            executorService.execute(crawlThread);
        }
        // 关闭线程池
        executorService.shutdown();
        try {
            countDown.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 清除
        UrlWarehouse.getInstance().clear();
        // 合并结果
        mergeResult(crawlThreads, results);

        // 爬取内容
        HandleResult(extractRules, results);
    }
}

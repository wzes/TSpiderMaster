package com.wzes.tspider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;
import com.wzes.tspider.service.HttpThread;
import com.wzes.tspider.service.listener.OnCrawlListener;
import com.wzes.tspider.service.spider.CrawlThread;
import com.wzes.tspider.service.spider.TSpiderProcessor;
import com.wzes.tspider.service.spider.UrlWarehouse;
import com.wzes.tspider.util.IdUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
@RestController
public class MasterController {

    @GetMapping(value = "/master/task")
    public BasicResponse<Result> build(@RequestParam("data") String data) {
        BasicResponse<Result> res = new BasicResponse<>();

        // get id
        final String id = IdUtils.getUUID();
        Task rTask = JSON.parseObject(data, Task.class);
        List<String> urls = rTask.getUrls();
        UrlWarehouse.getInstance().setUrls(urls);


        List<HttpThread> httpThreads = new ArrayList<>();
        int numOfThreads = 1;
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        final CountDownLatch countDown = new CountDownLatch(numOfThreads);
        for (int index = 0; index < numOfThreads; index++) {
            // 创建线程
            HttpThread httpThread = new HttpThread(id, data, countDown);
            httpThreads.add(httpThread);
            executorService.execute(httpThread);
        }
        // 关闭线程池
        executorService.shutdown();
        try {
            countDown.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        res.setCode(200);
        res.setMessage("success");
        res.setContent(mergeResult(httpThreads));
        return res;
    }

    /**
     * 合并结果
     * @param HttpThreads 开启的线程集合
     * @param result 返回的结果集
     */
    private Result mergeResult(List<HttpThread> httpThreads) {
        Result result = null;
        // 执行完合并结果
        boolean first = true;
        for (HttpThread httpThread : httpThreads) {
            Result threadResult = httpThread.getResult();
            if (first) {
                result = threadResult;
                first = false;
            } else {
                if (threadResult.getItems() != null) {
                    for (int j = 0; j < threadResult.getItems().size(); j++) {
                        result.getItems().get(j).addValues(
                                threadResult.getItems().get(j).getValues());
                    }
                }
            }
        }
        return result;
    }
}

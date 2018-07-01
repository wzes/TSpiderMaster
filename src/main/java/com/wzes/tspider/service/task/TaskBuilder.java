package com.wzes.tspider.service.task;

import com.alibaba.fastjson.JSON;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;
import com.wzes.tspider.service.HttpThread;
import com.wzes.tspider.service.spider.UrlWarehouse;
import com.wzes.tspider.util.IdUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Create by xuantang
 * @date on 2/12/18
 */
@Component
public class TaskBuilder {

    private List<String> hosts;
    private Result result;
    private Task task;

    public Task getTask() {
        return task;
    }

    public Result getResult() {
        return result;
    }

    /**
     * 开始
     */
    public TaskBuilder start() {
//        List<HttpThread> httpThreads = new ArrayList<>();
//        int numOfThreads = 2;
//        // 创建线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
//        final CountDownLatch countDown = new CountDownLatch(numOfThreads);
//        for (int index = 0; index < numOfThreads; index++) {
//            // 创建线程
//            HttpThread httpThread = new HttpThread(hosts[index % hosts.length],
//                    JSON.toJSONString(task), countDown);
//            httpThreads.add(httpThread);
//            executorService.execute(httpThread);
//        }
//        // 关闭线程池
//        executorService.shutdown();
//        try {
//            countDown.await();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        result = mergeResult(httpThreads);
        List<HttpThread> httpThreads = new ArrayList<>();
        int numOfThreads = 2;
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        // clear the urls
        task.setUrls(null);
        //final CountDownLatch countDown = new CountDownLatch(numOfThreads);
        for (int index = 0; index < hosts.size(); index++) {
            // 创建线程
            HttpThread httpThread = new HttpThread(hosts.get(index % hosts.size()),
                    JSON.toJSONString(task));
            httpThreads.add(httpThread);
            executorService.execute(httpThread);
        }
        return this;
    }

    /**
     * 配置
     * @param data
     * @param workers
     * @return
     */
    public TaskBuilder build(String data, List<String> workers) {
        // get id
        String id = IdUtils.getUUID();
        Task rTask = JSON.parseObject(data, Task.class);
        rTask.setId(id);
        List<String> urls = rTask.getUrls();
        UrlWarehouse.getInstance().setUrls(id, urls);
        hosts = workers;
        task = rTask;
        return this;
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

package com.wzes.tspider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;
import com.wzes.tspider.service.listener.OnCrawlListener;
import com.wzes.tspider.service.spider.TSpiderProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
@RestController
public class TaskController {
    @GetMapping(value = "/task")
    public BasicResponse<Result> build(@RequestParam("data") String data) {
        BasicResponse<Result> response = new BasicResponse<>();
        Task rTask = JSON.parseObject(data, Task.class);
        for (int index = 0; index < rTask.getExtractRules().size(); index++) {
            rTask.getExtractRules().get(index).setOnCrawlListener(new OnCrawlListener() {
                @Override
                public void onNext(Result result) {
                    response.setContent(result);
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println(e.getMessage());
                    response.setCode(201);
                    response.setMessage("exception");
                }

                @Override
                public void onComplete() {
                    response.setCode(200);
                    response.setMessage("success");

                }
            });
        }
        TSpiderProcessor.Builder builder1 = new TSpiderProcessor.Builder();
        builder1.task(rTask)
                .build()
                .start();
        return response;
    }
}

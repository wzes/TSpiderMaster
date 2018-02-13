package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.SpiderConfig;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.service.TaskConsumer;
import com.wzes.tspider.service.task.TaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
@RestController
public class MasterController {

    @Value("${workers.address}")
    private String workers;

    @Autowired
    TaskBuilder taskBuilder;

    @PostMapping(value = "/task")
    public BasicResponse<String> build(@RequestBody SpiderConfig spiderConfig) {
        BasicResponse<String> res = new BasicResponse<>();
        res.setCode(200);
        res.setMessage("success");
        System.out.println(spiderConfig.getData());
        TaskBuilder taskBuilder = this.taskBuilder.build(spiderConfig.getData(), workers).start();
        res.setContent(taskBuilder.getTask().getId());
        return res;
    }
}

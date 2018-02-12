package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.service.task.TaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/task")
    public BasicResponse<Result> build(@RequestParam("data") String data) {
        BasicResponse<Result> res = new BasicResponse<>();
        res.setCode(200);
        res.setMessage("success");
        res.setContent(taskBuilder.build(data, workers).start().getResult());
        return res;
    }
}

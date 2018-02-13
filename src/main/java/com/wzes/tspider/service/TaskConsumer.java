package com.wzes.tspider.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import org.springframework.stereotype.Component;


/**
 * @author Create by xuantang
 * @date on 2/13/18
 */
@Component
public class TaskConsumer {

    @Reference
    ITaskService iTaskService;

    /**
     *
     * @param data
     * @return
     */
    public BasicResponse<Result> execute(String data) {
        return iTaskService.execute(data);
    }
}

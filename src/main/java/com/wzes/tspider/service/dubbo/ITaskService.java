package com.wzes.tspider.service.dubbo;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;

/**
 * @author Create by xuantang
 * @date on 2/13/18
 */
public interface ITaskService {
    /**
     *
     * @param data
     * @return
     */
    BasicResponse<Result> execute(String data);
}

package com.wzes.tspider.service.store;

import com.wzes.tspider.module.spider.Result;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 1/31/18
 */
public class PipeLine {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Value("")
    private String hdfs;

    /**
     * 保存
     */
    public void storage() {

    }
}

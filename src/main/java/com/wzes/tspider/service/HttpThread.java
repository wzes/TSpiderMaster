package com.wzes.tspider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.module.spider.Task;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
public class HttpThread implements Runnable {
    private Result result;
    private String data;
    private String host;

    public HttpThread(String host, String data) {
        this.host = host;
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public void run() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String encode = URLEncoder.encode(data);
        RequestBody formBody = new FormEncodingBuilder()
                .add("data", data)
                .build();
        Request request = new Request.Builder()
                .url("http://" + host + "/task")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
//            BasicResponse basicResponse = JSON.parseObject(response.body().string(), BasicResponse.class);
//            JSONObject content = (JSONObject) basicResponse.getContent();
//            this.result = JSON.parseObject(content.toJSONString(), Result.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //countDownLatch.countDown();
    }
}

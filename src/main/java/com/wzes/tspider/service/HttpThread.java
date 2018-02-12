package com.wzes.tspider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
    private String id;

    private CountDownLatch countDownLatch;

    public HttpThread(String id, String data, CountDownLatch countDownLatch) {
        this.id = id;
        this.data = data;
        this.countDownLatch = countDownLatch;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public void run() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String encode = URLEncoder.encode(data);
        Request request = new Request.Builder()
                .url("http://localhost:8080/task/" + id + "?data=" + encode)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
            BasicResponse basicResponse = JSON.parseObject(response.body().string(), BasicResponse.class);
            JSONObject content = (JSONObject) basicResponse.getContent();
            this.result = JSON.parseObject(content.toJSONString(), Result.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }
}

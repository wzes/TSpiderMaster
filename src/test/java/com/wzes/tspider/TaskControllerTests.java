package com.wzes.tspider;

import com.squareup.okhttp.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
public class TaskControllerTests {
    @Test
    public void masterTest() {
        String data = "{\"data\":\"{\\\"config\\\":{\\\"timeout\\\":10000},\\\"extractRules\\\":[{\\\"extractItems\\\":[{\\\"extractType\\\":\\\"EXTRACT_TEXT\\\",\\\"name\\\":\\\"title\\\",\\\"selector\\\":\\\"body > div.main-content > div > div.main-content-wrap > div > div:nth-child(1) > div:nth-child(3) > div > a\\\"},{\\\"extractType\\\":\\\"EXTRACT_LINK\\\",\\\"name\\\":\\\"title_link\\\",\\\"selector\\\":\\\"body > div.main-content > div > div.main-content-wrap > div > div:nth-child(1) > div:nth-child(3) > div > a\\\"},{\\\"extractType\\\":\\\"EXTRACT_IMAGE\\\",\\\"name\\\":\\\"img\\\",\\\"selector\\\":\\\"body > div.main-content > div > div.main-content-wrap > div > div:nth-child(1) > div:nth-child(3) > div > a > img\\\"}],\\\"onCrawlListener\\\":{}}],\\\"numThreads\\\":1,\\\"pageType\\\":\\\"PAGE_COMMON\\\",\\\"urls\\\":[\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\",\\\"http://daily.zhihu.com/\\\"]}\"}";


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/task")
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
            String resq = response.body().string();
            System.out.println(resq);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

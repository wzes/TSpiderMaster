package com.wzes.tspider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.spider.Result;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
public class MasterControllerTests {
    @Test
    public void masterTest() {
        String encode = URLEncoder.encode("{\"config\":{\"timeout\":10000},\"extractRules\":[{\"extractItems\":[{\"extractType\":\"EXTRACT_TEXT\",\"name\":\"title\",\"xpaths\":[\"/html/body/div[3]/div/div[3]/div/ul/li[1]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[2]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[3]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[4]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[5]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[6]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[7]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[8]/a\"]},{\"extractType\":\"EXTRACT_LINK\",\"name\":\"title_link\",\"xpaths\":[\"/html/body/div[3]/div/div[3]/div/ul/li[1]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[2]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[3]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[4]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[5]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[6]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[7]/a\",\"/html/body/div[3]/div/div[3]/div/ul/li[8]/a\"]},{\"extractType\":\"EXTRACT_TEXT\",\"name\":\"date\",\"xpaths\":[\"/html/body/div[3]/div/div[3]/div/ul/li[1]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[2]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[3]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[4]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[5]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[6]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[7]/span\",\"/html/body/div[3]/div/div[3]/div/ul/li[8]/span\"]}],\"onCrawlListener\":{}}],\"numThreads\":4,\"pageType\":\"PAGE_COMMON\",\"urls\":[\"http://sse.tongji.edu.cn/Data/List/xyxw\",\"http://sse.tongji.edu.cn/Data/List/xyxw?page=2\",\"http://sse.tongji.edu.cn/Data/List/xyxw?page=3\",\"http://sse.tongji.edu.cn/Data/List/xyxw?page=4\",\"http://sse.tongji.edu.cn/Data/List/xyxw?page=5\",\"http://sse.tongji.edu.cn/Data/List/xyxw?page=6\"]}");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/master/task?data=" + encode)
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

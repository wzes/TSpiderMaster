package com.wzes.tspider.service.task;

import com.alibaba.fastjson.JSON;
import com.wzes.tspider.module.Progress;
import com.wzes.tspider.module.UrlState;
import com.wzes.tspider.service.Constant;
import com.wzes.tspider.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@Service
public class TaskProgressService {

    @Autowired
    RedisService redisService;

    public Progress getProgress(String id) {
        try {
            List<String> datas = redisService.lrange(id + Constant.KEY_RESULT_BACK);
            int total = redisService.lrange(id).size() + redisService.lrange(id + Constant.KEY_RESULT_BACK).size();
            int crawled = datas.size();
            int success = 0;
            for (String s : datas) {
                UrlState urlState = JSON.parseObject(s, UrlState.class);
                if (urlState.getCode() == 200) {
                    success++;
                }
            }
            return new Progress(total, crawled, success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Progress(0, 0, 0);
    }

    public List<UrlState> getDetailUrlStates(String id) {
        List<UrlState> urlStates = new ArrayList<>();
        try {
            List<String> fUrls = redisService.lrange(id + Constant.KEY_RESULT_BACK);
            List<String> nUrls = redisService.lrange(id);
            for (String state : fUrls) {
                UrlState urlState = JSON.parseObject(state, UrlState.class);
                urlStates.add(urlState);
            }
            for (String state : nUrls) {
                UrlState urlState = new UrlState();
                urlState.setUrl(state);
                urlState.setCode(-1);
                urlState.setState("not started");
                urlStates.add(urlState);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlStates;
    }
}

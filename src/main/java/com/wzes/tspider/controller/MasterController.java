package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.Progress;
import com.wzes.tspider.module.SpiderConfig;
import com.wzes.tspider.module.UrlState;
import com.wzes.tspider.service.redis.RedisService;
import com.wzes.tspider.service.store.HdfsService;
import com.wzes.tspider.service.task.TaskBuilder;
import com.wzes.tspider.service.task.TaskProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
@RestController
public class MasterController {

    @Value("${workers.address}")
    private String workers;

    @Autowired
    TaskProgressService taskProgressService;


    @Value("${hdfs.address}")
    private String hdfs;

    @Autowired
    TaskBuilder taskBuilder;

    @Autowired
    HdfsService hdfsService;

    @Autowired
    RedisService redisService;

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

    /**
     * Returns a JSON configuration file of the task specified by the {@code taskId}.
     * @param taskId The id of the task.
     * @param httpServletResponse The response of the HttpServlet.
     */
    @GetMapping(value = "task/result/{id}", produces = "application/force-download")
    public void downloadConfigJsonFile(@PathVariable("id") String id,
                                       HttpServletResponse httpServletResponse) {
        String content = hdfsService.getFile(id);

        String fileName = id + ".txt";
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(content);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @GetMapping(value = "/task/progress/{id}")
    public BasicResponse<Progress> viewProgress(@PathVariable("id") String id) {
        BasicResponse<Progress> res = new BasicResponse<>();
        try {
            res.setContent(taskProgressService.getProgress(id));
            res.setCode(200);
            res.setMessage("success");
        } catch (Exception e) {
            res.setCode(404);
            res.setMessage("error");
        }
        return res;
    }

    @GetMapping(value = "/task/detail/{id}")
    public BasicResponse<List<UrlState>> viewDetail(@PathVariable("id") String id) {
        BasicResponse<List<UrlState>> res = new BasicResponse<>();
        try {
            res.setContent(taskProgressService.getDetailUrlStates(id));
            res.setCode(200);
            res.setMessage("success");
        } catch (Exception e) {
            res.setCode(404);
            res.setMessage("error");
        }
        return res;
    }
}

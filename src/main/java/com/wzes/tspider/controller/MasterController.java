package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.SpiderConfig;
import com.wzes.tspider.module.spider.Result;
import com.wzes.tspider.service.TaskConsumer;
import com.wzes.tspider.service.store.HdfsUtils;
import com.wzes.tspider.service.task.TaskBuilder;
import org.apache.hadoop.fs.Hdfs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Create by xuantang
 * @date on 2/5/18
 */
@RestController
public class MasterController {

    @Value("${workers.address}")
    private String workers;

    @Value("${hdfs.address}")
    private String hdfs;

    @Autowired
    TaskBuilder taskBuilder;

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
        String content = HdfsUtils.getFile(id);

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
}

package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.module.Progress;
import com.wzes.tspider.module.SpiderConfig;
import com.wzes.tspider.module.UrlState;
import com.wzes.tspider.module.db.DbTask;
import com.wzes.tspider.service.redis.RedisService;
import com.wzes.tspider.service.store.HdfsService;
import com.wzes.tspider.service.task.TaskBuilder;
import com.wzes.tspider.service.task.TaskProgressService;
import com.wzes.tspider.service.task.TaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
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
@RequestMapping("task/")
@RestController
public class TaskController {

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

    @Autowired
    TaskService taskService;

    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "build")
    public BasicResponse<String> build(@RequestParam("token") String token,
                                       @ApiParam(value="SpiderConfig")  @RequestBody SpiderConfig spiderConfig) {
        try {
            if (!"tspider".equals(token)) {
                return new BasicResponse<>(403, "forbidden");
            }
            System.out.println(spiderConfig.getData());
            taskBuilder.build(spiderConfig.getData(), workers).start();
            taskService.addTask(token, taskBuilder.getTask().getId());
            return new BasicResponse<>(200, "success",
                    taskBuilder.getTask().getId());
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse<>(404, "error");
        }
    }

    /**
     * Returns a JSON configuration file of the task specified by the {@code taskId}.
     * @param token
     * @param id
     * @param httpServletResponse
     */
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    @GetMapping(value = "result/{id}", produces = "application/force-download")
    public void downloadConfigJsonFile(@RequestParam("token") String token,
                                        @ApiParam(value = "id", required = true) @PathVariable("id") String id,
                                       HttpServletResponse httpServletResponse) {
        if (!"tspider".equals(token)) {
            try {
                httpServletResponse.sendError(403, "forbidden");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if (id == null || id.isEmpty() || id.equals("{id}")) {
            try {
                httpServletResponse.sendError(404, "id is null");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            String content = hdfsService.getFile(id);
            String fileName = id + ".txt";
            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(content);
            writer.close();
        } catch (Exception exception) {
            try {
                httpServletResponse.sendError(404, "id is null");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "progress/{id}")
    public BasicResponse<Progress> viewProgress(@RequestParam("token") String token,
                                                @ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        try {
            if (!"tspider".equals(token)) {
                return new BasicResponse<>(403, "forbidden");
            }
            return new BasicResponse<>(200, "success",
                    taskProgressService.getProgress(id));
        } catch (Exception e) {
            return new BasicResponse<>(404, "error");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping(value = "detail/{id}")
    public BasicResponse<List<UrlState>> viewDetail(@RequestParam("token") String token,
                                                    @ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        try {
            if (!"tspider".equals(token)) {
                return new BasicResponse<>(403, "forbidden");
            }
            return new BasicResponse<>(200, "success",
                    taskProgressService.getDetailUrlStates(id));
        } catch (Exception e) {
            return new BasicResponse<>(404, "error");
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "start", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "offset", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "list/{user_id}")
    public BasicResponse<List<DbTask>> viewDetail(@RequestParam("token") String token,
                                                  @RequestParam("start") int start,
                                                  @RequestParam("offset") int offset,
                                                  @ApiParam(value = "user_id", required = true)
                                                  @PathVariable("user_id") String user_id) {
        try {
            if (!"tspider".equals(token)) {
                return new BasicResponse<>(403, "forbidden");
            }
            return new BasicResponse<>(200, "success",
                    taskService.getTasks(user_id, start, offset));
        } catch (Exception e) {
            return new BasicResponse<>(404, "error");
        }
    }
}

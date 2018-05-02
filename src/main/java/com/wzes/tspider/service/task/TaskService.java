package com.wzes.tspider.service.task;

import com.wzes.tspider.mapper.DbTaskMapper;
import com.wzes.tspider.module.db.DbTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@Service
public class TaskService {

    @Autowired
    DbTaskMapper taskMapper;

    public boolean addTask(String user_id, String task_id) {
        try {
            taskMapper.addTask(new DbTask(user_id, task_id));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<DbTask> getTasks(String user_id, int start, int offset) {
        try {
            return taskMapper.list(user_id, start, offset);
        } catch (Exception e) {
            return null;
        }
    }
}

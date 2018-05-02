package com.wzes.tspider.module.db;

import java.sql.Timestamp;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
public class DbTask {
    private String user_id;
    private String task_id;
    private Timestamp create_time;

    public DbTask(String user_id, String task_id) {
        this.user_id = user_id;
        this.task_id = task_id;
    }

    public DbTask() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}

package com.wzes.tspider.mapper;

import com.wzes.tspider.module.db.DbTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@Mapper
@Service
public interface DbTaskMapper {

    @Insert("INSERT INTO task(user_id, task_id, create_time) VALUES (#{user_id} ," +
            "#{create_time}, now())")
    void addTask(DbTask task);

    @Select("SELECT * FROM task WHERE task_id = #{task_id}")
    DbTask get(@Param("task_id") String task_id);

    @Select("SELECT * FROM task WHERE user_id = #{user_id} limit #{start}, #{offset}")
    List<DbTask> list(@Param("user_id") String user_id,
                      @Param("start") int start,
                      @Param("offset") int offset);
}

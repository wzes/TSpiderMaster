package com.wzes.tspider.mapper;

import com.wzes.tspider.module.db.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@Mapper
@Service
public interface UserMapper {

    @Insert("INSERT INTO user(user_id, username, password, email) VALUES " +
            "(#{user_id}, #{username}, #{password,}, #{email})")
    void addUser(User user);
}

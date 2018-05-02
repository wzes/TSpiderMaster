package com.wzes.tspider.service;

import com.wzes.tspider.mapper.UserMapper;
import com.wzes.tspider.module.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public boolean register(String user_id, String username, String email, String password) {
        try {
            userMapper.addUser(new User(user_id, password, email, username));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

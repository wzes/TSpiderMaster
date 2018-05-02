package com.wzes.tspider.controller;

import com.wzes.tspider.module.BasicResponse;
import com.wzes.tspider.service.UserService;
import com.wzes.tspider.util.IdUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Create by xuantang
 * @date on 5/2/18
 */
@RequestMapping("user/")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "email", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "register")
    public BasicResponse<String> viewDetail(@RequestParam("username") String username,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("email") String email) {
        try {
            String uuid = IdUtils.getUUID();
            if (userService.register(uuid, password, email, username)) {
                return new BasicResponse<>(200, "success", uuid);
            }
        } catch (Exception e) {
            return new BasicResponse<>(404, "error");
        }
        return new BasicResponse<>(400, "error");
    }
}

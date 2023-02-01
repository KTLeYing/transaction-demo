package com.mzl.transaction.controller;

import com.mzl.transaction.request.AddUserRequest;
import com.mzl.transaction.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mzl
 * @since 2023-02-01
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody AddUserRequest addUserRequest){
        return userService.addUser(addUserRequest);
    }

    @PostMapping("/addUserOne")
    public String addUserOne(@RequestBody AddUserRequest addUserRequest){
        return userService.addUserOne(addUserRequest);
    }

    @PostMapping("/addUserTwo")
    public String addUserTwo(@RequestBody AddUserRequest addUserRequest){
        return userService.addUserTwo(addUserRequest);
    }

    @PostMapping("/addUserThree")
    public String addUserThree(@RequestBody AddUserRequest addUserRequest){
        return userService.addUserThree(addUserRequest);
    }

    @PostMapping("/addUserFour")
    public String addUserFour(@RequestBody AddUserRequest addUserRequest){
        return userService.addUserFour(addUserRequest);
    }

}


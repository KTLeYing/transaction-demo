package com.mzl.transaction.service;

import com.mzl.transaction.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl.transaction.request.AddUserRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mzl
 * @since 2023-02-01
 */
public interface UserService extends IService<User> {

    String addUser(AddUserRequest userRequest);

    String addUserOne(AddUserRequest userRequest);

    String addUserTwo(AddUserRequest userRequest);

    String addUserThree(AddUserRequest userRequest);

    String addUserFour(AddUserRequest userRequest);

}

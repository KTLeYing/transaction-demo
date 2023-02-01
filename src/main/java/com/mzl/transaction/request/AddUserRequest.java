package com.mzl.transaction.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName :   UserRequest
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2023/2/1 00:54
 * @Version: 1.0
 */
@Data
public class AddUserRequest {

    private String username;

    private String password;

    private Integer sex;

    private Integer age;

    private Integer integralNum;

    private Integer activityId;

    private String note;

    private Integer isGetIntegral;

}
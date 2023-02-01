package com.mzl.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mzl
 * @since 2023-02-01
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="Sign对象", description="")
public class Sign implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "签到表自增id")
        @TableId(value = "sign_id", type = IdType.AUTO)
      private Integer signId;

      @ApiModelProperty(value = "用户id")
      private Integer userId;

      @ApiModelProperty(value = "活动id")
      private Integer activityId;

      @ApiModelProperty(value = "签到时间")
      private Date signTime;

      @ApiModelProperty(value = "签到备注")
      private String note;

    private Integer isGetIntegral;


}

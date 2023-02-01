package com.mzl.transaction.service.impl;

import com.mzl.transaction.entity.Sign;
import com.mzl.transaction.mapper.SignMapper;
import com.mzl.transaction.service.SignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mzl
 * @since 2023-02-01
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {

}

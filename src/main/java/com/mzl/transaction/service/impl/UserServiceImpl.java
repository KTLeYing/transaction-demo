package com.mzl.transaction.service.impl;

import com.mzl.transaction.entity.Sign;
import com.mzl.transaction.entity.User;
import com.mzl.transaction.mapper.SignMapper;
import com.mzl.transaction.mapper.UserMapper;
import com.mzl.transaction.request.AddUserRequest;
import com.mzl.transaction.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mzl
 * @since 2023-02-01
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SignMapper signMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PlatformTransactionManager transactionManager;

    /**
     * 自动回滚方式
     * @param addUserRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //自动回滚方式
    public String addUser(AddUserRequest addUserRequest) {
        //新增用户
        User user = new User();
        BeanUtils.copyProperties(addUserRequest, user);
        Integer userId  = userMapper.insert(user);
        log.info("用户新增后主键id={}", userId);

        //新增签到记录
        Sign sign = new Sign();
        BeanUtils.copyProperties(addUserRequest, sign);
        sign.setUserId(userId);
        sign.setSignTime(new Date());
        signMapper.insert(sign);

        //模拟造异常回滚
//        int num = 2 / 0;

        return "新增用户成功";
    }

    /**
     * 编程式事务【更好控制事务粗细粒度】
     * @param addUserRequest
     * @return
     */
    @Override
    public String addUserOne(AddUserRequest addUserRequest) {
        //这里预先设置指定传播策略【也可以直接使用默认的】
//        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //新增用户
                User user = new User();
                BeanUtils.copyProperties(addUserRequest, user);
                Integer userId  = userMapper.insert(user);
                log.info("用户新增后主键id={}", userId);

                //新增签到记录
                Sign sign = new Sign();
                BeanUtils.copyProperties(addUserRequest, sign);
                sign.setUserId(userId);
                sign.setSignTime(new Date());
                signMapper.insert(sign);

                //模拟造异常回滚
//                int num = 2 / 0;
            }
        });

//        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        Integer result = transactionTemplate.execute(new TransactionCallback<Integer>() {
//            @Override
//            public Integer doInTransaction(TransactionStatus status) {
//                //新增用户
//                User user = new User();
//                BeanUtils.copyProperties(addUserRequest, user);
//                Integer userId  = userMapper.insert(user);
//                log.info("用户新增后主键id={}", userId);
//
//                //新增签到记录
//                Sign sign = new Sign();
//                BeanUtils.copyProperties(addUserRequest, sign);
//                sign.setUserId(userId);
//                sign.setSignTime(new Date());
//                signMapper.insert(sign);
//
//                //模拟造异常回滚
//                int num = 2 / 0;
//                return sign.getSignId() ;
//            }
//        }) ;
        return "新增用户成功";
    }

    /**
     * 编程式事务【更好控制事务粗细粒度+手动回滚】
     * @param addUserRequest
     * @return
     */
    @Override
    public String addUserTwo(AddUserRequest addUserRequest) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    //新增用户
                    User user = new User();
                    BeanUtils.copyProperties(addUserRequest, user);
                    Integer userId  = userMapper.insert(user);
                    log.info("用户新增后主键id={}", userId);

                    //新增签到记录
                    Sign sign = new Sign();
                    BeanUtils.copyProperties(addUserRequest, sign);
                    sign.setUserId(userId);
                    sign.setSignTime(new Date());
                    signMapper.insert(sign);

                    //模拟造异常回滚
                    int num = 2 / 0;
                } catch (Exception e) {
                    log.error("插入失败, 进行数据库回滚", e);
                    status.setRollbackOnly();
                }
            }
        });

        return "新增用户成功";
    }

    /**
     * 手动回滚
     * @param addUserRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class) //一定要加此注解先开启事务，否则手动回滚事务失败
    public String addUserThree(AddUserRequest addUserRequest) {
        try {
            //新增用户
            User user = new User();
            BeanUtils.copyProperties(addUserRequest, user);
            Integer userId  = userMapper.insert(user);
            log.info("用户新增后主键id={}", userId);

            //新增签到记录
            Sign sign = new Sign();
            BeanUtils.copyProperties(addUserRequest, sign);
            sign.setUserId(userId);
            sign.setSignTime(new Date());
            signMapper.insert(sign);

            //模拟造异常回滚
            int num = 2 / 0;
        } catch (Exception e) {
            log.error("插入失败, 进行数据库回滚", e);
            //try-catch使@Transactional事务失效，手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return "新增用户成功";
    }

    /**
     * 编程式事务【使用TransactionManager来开启、提交、回滚】
     * @param addUserRequest
     * @return
     */
    @Override
    public String addUserFour(AddUserRequest addUserRequest) {
        //开启事务
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);

        try {
            //新增用户
            User user = new User();
            BeanUtils.copyProperties(addUserRequest, user);
            Integer userId  = userMapper.insert(user);
            log.info("用户新增后主键id={}", userId);

            //新增签到记录
            Sign sign = new Sign();
            BeanUtils.copyProperties(addUserRequest, sign);
            sign.setUserId(userId);
            sign.setSignTime(new Date());
            signMapper.insert(sign);

            //模拟造异常回滚
            int num = 2 / 0;

            //提交事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            log.error("插入失败, 进行数据库回滚", e);
            //异常手动回滚事务
            transactionManager.rollback(transactionStatus);
        }

        return "新增用户成功";
    }

}

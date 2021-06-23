package com.cncodehub.service.impl;

import com.cncodehub.entity.User;
import com.cncodehub.mapper.UserMapper;
import com.cncodehub.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cnCodeHub
 * @since 2021-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

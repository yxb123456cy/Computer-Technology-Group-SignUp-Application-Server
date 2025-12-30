package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.domain.User;
import com.lemon.computer.service.UserService;
import com.lemon.computer.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user(学生用户表)】的数据库操作Service实现
* @createDate 2025-12-31 01:00:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





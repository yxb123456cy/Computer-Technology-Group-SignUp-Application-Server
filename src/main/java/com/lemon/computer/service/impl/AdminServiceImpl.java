package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.domain.Admin;
import com.lemon.computer.service.AdminService;
import com.lemon.computer.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【admin(管理员表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}





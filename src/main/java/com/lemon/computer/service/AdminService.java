package com.lemon.computer.service;

import com.lemon.computer.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【admin(管理员表)】的数据库操作Service
* @createDate 2025-12-31 00:56:48
*/
public interface AdminService extends IService<Admin> {

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录token
     */
    String login(String username, String password);

    /**
     * 获取管理员信息
     * @return 管理员信息
     */
    Admin getAdminInfo();

    /**
     * 退出登录
     */
    void logout();
}

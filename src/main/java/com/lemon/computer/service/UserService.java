package com.lemon.computer.service;

import com.lemon.computer.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user(学生用户表)】的数据库操作Service
* @createDate 2025-12-31 01:00:43
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param studentId 学号
     * @param password 密码
     * @param name 姓名
     * @return 登录Token
     */
    String register(String studentId, String password, String name);

    /**
     * 用户登录
     * @param studentId 学号
     * @param password 密码
     * @return 登录Token
     */
    String login(String studentId, String password);

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    User getUserInfo();

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUserInfo(User user);

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void updatePassword(String oldPassword, String newPassword);

    /**
     * 退出登录
     */
    void logout();
}

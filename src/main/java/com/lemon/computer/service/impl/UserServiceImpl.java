package com.lemon.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.common.constants.GlobalSystemConstants;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.domain.User;
import com.lemon.computer.service.UserService;
import com.lemon.computer.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
* @author Administrator
* @description 针对表【user(学生用户表)】的数据库操作Service实现
* @createDate 2025-12-31 01:00:43
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public String register(String studentId, String password, String name) {
        // 1. 校验学号是否存在
        User existUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getStudentId, studentId));
        if (existUser != null) {
            throw new BizException("该学号已注册");
        }

        // 2. 校验密码强度 (6-20位，字母+数字)
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")) {
            throw new BizException("密码必须包含字母和数字，长度6-20位");
        }

        // 3. 创建用户
        User user = new User();
        user.setStudentId(studentId);
        user.setName(name);
        user.setRegisterTime(LocalDateTime.now());

        // 4. 密码加密
        String saltPassword = password + GlobalSystemConstants.PASSWORD_SALT;
        user.setPassword(DigestUtils.md5DigestAsHex(saltPassword.getBytes(StandardCharsets.UTF_8)));

        this.save(user);

        // 5. 自动登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public String login(String studentId, String password) {
        // 1. 查询用户
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getStudentId, studentId));
        if (user == null) {
            throw new BizException("账号或密码错误");
        }

        // 2. 校验密码
        String saltPassword = password + GlobalSystemConstants.PASSWORD_SALT;
        String encryptedPassword = DigestUtils.md5DigestAsHex(saltPassword.getBytes(StandardCharsets.UTF_8));

        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BizException("账号或密码错误");
        }

        // 3. 登录
        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }

    @Override
    public User getUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();
        User user = this.getById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        // 脱敏
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateUserInfo(User user) {
        int userId = StpUtil.getLoginIdAsInt();
        User dbUser = this.getById(userId);
        if (dbUser == null) {
            throw new BizException("用户不存在");
        }

        // 更新字段
        if (StringUtils.isNotBlank(user.getName())) {
            dbUser.setName(user.getName());
        }
        if (StringUtils.isNotBlank(user.getMajor())) {
            dbUser.setMajor(user.getMajor());
        }
        if (StringUtils.isNotBlank(user.getGrade())) {
            dbUser.setGrade(user.getGrade());
        }
        if (StringUtils.isNotBlank(user.getClassRoom())) {
            dbUser.setClassRoom(user.getClassRoom());
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            // 校验手机号
            if (!user.getPhone().matches("^1[3-9]\\d{9}$")) {
                throw new BizException("手机号格式不正确");
            }
            dbUser.setPhone(user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getAvatar())) {
            dbUser.setAvatar(user.getAvatar());
        }

        dbUser.setUpdateTime(LocalDateTime.now());
        this.updateById(dbUser);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        int userId = StpUtil.getLoginIdAsInt();
        User user = this.getById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 校验旧密码
        String saltOld = oldPassword + GlobalSystemConstants.PASSWORD_SALT;
        if (!DigestUtils.md5DigestAsHex(saltOld.getBytes(StandardCharsets.UTF_8)).equals(user.getPassword())) {
            throw new BizException("原密码错误");
        }

        // 校验新密码
        if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$")) {
            throw new BizException("新密码必须包含字母和数字，长度6-20位");
        }

        // 更新密码
        String saltNew = newPassword + GlobalSystemConstants.PASSWORD_SALT;
        user.setPassword(DigestUtils.md5DigestAsHex(saltNew.getBytes(StandardCharsets.UTF_8)));
        user.setUpdateTime(LocalDateTime.now());
        this.updateById(user);

        // 退出登录
        StpUtil.logout();
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}





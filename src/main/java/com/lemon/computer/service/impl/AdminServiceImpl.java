package com.lemon.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.common.constants.GlobalSystemConstants;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.domain.Admin;
import com.lemon.computer.service.AdminService;
import com.lemon.computer.mapper.AdminMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【admin(管理员表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Override
    public String login(String username, String password) {
        // 1. 根据用户名查询管理员
        Admin admin = this.getOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username));
        // 2. 校验管理员是否存在
        if (admin == null) {
            throw new BizException("用户名或密码错误");
        }
        // 3. 校验密码 (MD5 + 盐值)
        String encryptedPassword = DigestUtils.md5Hex(password + GlobalSystemConstants.PASSWORD_SALT);
        if (!encryptedPassword.equals(admin.getPassword())) {
            throw new BizException("用户名或密码错误");
        }
        // 4. 登录，生成Token
        StpUtil.login(admin.getId());
        // 5. 返回Token
        return StpUtil.getTokenValue();
    }

    @Override
    public Admin getAdminInfo() {
        // 1. 获取当前登录用户ID
        int adminId = StpUtil.getLoginIdAsInt();
        // 2. 查询用户信息
        Admin admin = this.getById(adminId);
        if (admin == null) {
            throw new BizException("用户不存在");
        }
        // 3. 脱敏处理
        admin.setPassword(null);
        return admin;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}





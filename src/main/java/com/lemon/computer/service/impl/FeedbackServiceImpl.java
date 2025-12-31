package com.lemon.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.domain.Feedback;
import com.lemon.computer.domain.User;
import com.lemon.computer.mapper.UserMapper;
import com.lemon.computer.service.FeedbackService;
import com.lemon.computer.mapper.FeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author Administrator
* @description 针对表【feedback(用户反馈表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

    private final UserMapper userMapper;

    @Override
    public void submitFeedback(Feedback feedback) {
        // 获取当前登录用户ID
        int userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectById(userId);
        
        feedback.setStudentId(user != null ? user.getStudentId() : null);
        feedback.setStatus(0); // 未处理
        feedback.setCreateTime(LocalDateTime.now());
        
        this.save(feedback);
    }

    @Override
    public Page<Feedback> getFeedbackList(int pageNum, int pageSize, String type, Integer status) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq(Feedback::getType, type);
        }
        if (status != null) {
            wrapper.eq(Feedback::getStatus, status);
        }
        wrapper.orderByDesc(Feedback::getCreateTime);
        
        return this.page(page, wrapper);
    }

    @Override
    public void handleFeedback(Integer id) {
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new BizException("反馈不存在");
        }
        feedback.setStatus(1); // 已处理
        feedback.setUpdateTime(LocalDateTime.now());
        this.updateById(feedback);
    }

    @Override
    public void deleteFeedback(Integer id) {
        if (!this.removeById(id)) {
            throw new BizException("反馈不存在");
        }
    }
}





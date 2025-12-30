package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.domain.Feedback;
import com.lemon.computer.service.FeedbackService;
import com.lemon.computer.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【feedback(用户反馈表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback>
    implements FeedbackService{

}





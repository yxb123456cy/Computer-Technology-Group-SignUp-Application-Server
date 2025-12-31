package com.lemon.computer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.domain.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【feedback(用户反馈表)】的数据库操作Service
* @createDate 2025-12-31 00:56:48
*/
public interface FeedbackService extends IService<Feedback> {

    /**
     * 提交反馈
     * @param feedback 反馈信息
     */
    void submitFeedback(Feedback feedback);

    /**
     * 分页查询反馈列表
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param type 反馈类型
     * @param status 处理状态
     * @return 分页结果
     */
    Page<Feedback> getFeedbackList(int pageNum, int pageSize, String type, Integer status);

    /**
     * 处理反馈
     * @param id 反馈ID
     */
    void handleFeedback(Integer id);

    /**
     * 删除反馈
     * @param id 反馈ID
     */
    void deleteFeedback(Integer id);
}

package com.lemon.computer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.domain.RegistrationRecord;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
* @author Administrator
* @description 针对表【registration_record(学生报名记录表)】的数据库操作Service
* @createDate 2025-12-31 00:56:48
*/
public interface RegistrationRecordService extends IService<RegistrationRecord> {

    /**
     * 提交报名信息
     * @param record 报名记录
     */
    void submitRegistration(RegistrationRecord record);

    /**
     * 获取当前用户的报名记录
     * @return 报名记录列表
     */
    List<RegistrationRecord> getUserRecords();

    /**
     * 查询当前用户在指定批次的报名状态
     * @param batchId 批次ID
     * @return 报名记录
     */
    RegistrationRecord getUserRecordInBatch(Integer batchId);

    /**
     * 分页查询报名记录（管理员）
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @param batchId 批次ID
     * @param status 审核状态
     * @param keyword 搜索关键词（学号/姓名）
     * @return 分页结果
     */
    Page<RegistrationRecord> getRecordList(int pageNum, int pageSize, Integer batchId, Integer status, String keyword);

    /**
     * 审核报名记录
     * @param id 记录ID
     * @param status 审核状态
     * @param reviewOpinion 审核意见
     */
    void reviewRecord(Integer id, Integer status, String reviewOpinion);
    
    /**
     * 导出报名记录
     * @param batchId 批次ID
     * @param status 审核状态
     * @return 记录列表
     */
    List<RegistrationRecord> exportRecords(Integer batchId, Integer status);
}

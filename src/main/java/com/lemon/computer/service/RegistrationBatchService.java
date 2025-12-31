package com.lemon.computer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.domain.RegistrationBatch;
import com.baomidou.mybatisplus.extension.service.IService;



/**
* @author Administrator
* @description 针对表【registration_batch(报名批次表)】的数据库操作Service
* @createDate 2025-12-31 00:56:48
*/
public interface RegistrationBatchService extends IService<RegistrationBatch> {

    /**
     * 获取当前活跃批次
     * @return 活跃批次
     */
    RegistrationBatch getCurrentActiveBatch();

    /**
     * 分页查询批次列表
     * @param pageNum 当前页
     * @param pageSize 每页大小
     * @return 分页结果
     */
    Page<RegistrationBatch> getBatchList(int pageNum, int pageSize);

    /**
     * 新增批次
     * @param batch 批次信息
     */
    void addBatch(RegistrationBatch batch);

    /**
     * 更新批次
     * @param batch 批次信息
     */
    void updateBatch(RegistrationBatch batch);

    /**
     * 删除批次
     * @param id 批次ID
     */
    void deleteBatch(Integer id);

    /**
     * 设置活跃批次
     * @param id 批次ID
     */
    void setActiveBatch(Integer id);
}

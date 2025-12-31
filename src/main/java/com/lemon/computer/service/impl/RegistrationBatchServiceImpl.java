package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.domain.RegistrationBatch;
import com.lemon.computer.service.RegistrationBatchService;
import com.lemon.computer.mapper.RegistrationBatchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
* @author Administrator
* @description 针对表【registration_batch(报名批次表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
public class RegistrationBatchServiceImpl extends ServiceImpl<RegistrationBatchMapper, RegistrationBatch>
    implements RegistrationBatchService{

    @Override
    public RegistrationBatch getCurrentActiveBatch() {
        return this.getOne(new LambdaQueryWrapper<RegistrationBatch>()
                .eq(RegistrationBatch::getIsActive, 1)
                .orderByDesc(RegistrationBatch::getCreateTime)
                .last("LIMIT 1"));
    }

    @Override
    public Page<RegistrationBatch> getBatchList(int pageNum, int pageSize) {
        Page<RegistrationBatch> page = new Page<>(pageNum, pageSize);
        return this.page(page, new LambdaQueryWrapper<RegistrationBatch>()
                .orderByDesc(RegistrationBatch::getCreateTime));
    }

    @Override
    public void addBatch(RegistrationBatch batch) {
        // 校验批次名称是否存在
        if (this.count(new LambdaQueryWrapper<RegistrationBatch>().eq(RegistrationBatch::getBatchName, batch.getBatchName())) > 0) {
            throw new BizException("批次名称已存在");
        }
        batch.setCreateTime(LocalDateTime.now());
        batch.setIsActive(0); // 默认非活跃，需手动激活
        this.save(batch);
    }

    @Override
    public void updateBatch(RegistrationBatch batch) {
        RegistrationBatch dbBatch = this.getById(batch.getId());
        if (dbBatch == null) {
            throw new BizException("批次不存在");
        }
        
        // 校验批次名称是否存在（排除自身）
        if (this.count(new LambdaQueryWrapper<RegistrationBatch>()
                .eq(RegistrationBatch::getBatchName, batch.getBatchName())
                .ne(RegistrationBatch::getId, batch.getId())) > 0) {
            throw new BizException("批次名称已存在");
        }

        dbBatch.setBatchName(batch.getBatchName());
        dbBatch.setStartTime(batch.getStartTime());
        dbBatch.setEndTime(batch.getEndTime());
        dbBatch.setNotice(batch.getNotice());
        dbBatch.setUpdateTime(LocalDateTime.now());
        
        this.updateById(dbBatch);
    }

    @Override
    public void deleteBatch(Integer id) {
        RegistrationBatch batch = this.getById(id);
        if (batch == null) {
            throw new BizException("批次不存在");
        }
        if (batch.getIsActive() == 1) {
            throw new BizException("无法删除当前活跃批次");
        }
        // TODO: 校验是否有报名记录关联 (暂不实现，需求未提及)
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setActiveBatch(Integer id) {
        // 1. 将所有批次设置为非活跃
        this.update(new LambdaUpdateWrapper<RegistrationBatch>().set(RegistrationBatch::getIsActive, 0));
        
        // 2. 将指定批次设置为活跃
        if (!this.update(new LambdaUpdateWrapper<RegistrationBatch>()
                .eq(RegistrationBatch::getId, id)
                .set(RegistrationBatch::getIsActive, 1))) {
            throw new BizException("批次不存在");
        }
    }
}





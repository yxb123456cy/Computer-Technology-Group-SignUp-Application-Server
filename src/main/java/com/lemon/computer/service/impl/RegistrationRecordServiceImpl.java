package com.lemon.computer.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.domain.RegistrationBatch;
import com.lemon.computer.domain.RegistrationRecord;
import com.lemon.computer.domain.User;
import com.lemon.computer.mapper.RegistrationBatchMapper;
import com.lemon.computer.mapper.UserMapper;
import com.lemon.computer.service.RegistrationRecordService;
import com.lemon.computer.mapper.RegistrationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author Administrator
* @description 针对表【registration_record(学生报名记录表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
@RequiredArgsConstructor
public class RegistrationRecordServiceImpl extends ServiceImpl<RegistrationRecordMapper, RegistrationRecord>
    implements RegistrationRecordService{

    private final UserMapper userMapper;
    private final RegistrationBatchMapper registrationBatchMapper;

    @Override
    public void submitRegistration(RegistrationRecord record) {
        // 1. 获取当前用户
        int userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 2. 校验批次是否有效
        RegistrationBatch batch = registrationBatchMapper.selectById(record.getBatchId());
        if (batch == null || batch.getIsActive() != 1) {
            throw new BizException("该报名批次无效或已结束");
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(batch.getStartTime()) || now.isAfter(batch.getEndTime())) {
            throw new BizException("不在报名时间内");
        }

        // 3. 校验是否已报名
        long count = this.count(new LambdaQueryWrapper<RegistrationRecord>()
                .eq(RegistrationRecord::getStudentId, user.getStudentId())
                .eq(RegistrationRecord::getBatchId, record.getBatchId()));
        if (count > 0) {
            throw new BizException("您已报名该批次，请勿重复报名");
        }

        // 4. 保存记录
        record.setStudentId(user.getStudentId());
        record.setStatus(0); // 待审核
        record.setCreateTime(now);
        this.save(record);
    }

    @Override
    public List<RegistrationRecord> getUserRecords() {
        int userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        return this.list(new LambdaQueryWrapper<RegistrationRecord>()
                .eq(RegistrationRecord::getStudentId, user.getStudentId())
                .orderByDesc(RegistrationRecord::getCreateTime));
    }

    @Override
    public RegistrationRecord getUserRecordInBatch(Integer batchId) {
        int userId = StpUtil.getLoginIdAsInt();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        return this.getOne(new LambdaQueryWrapper<RegistrationRecord>()
                .eq(RegistrationRecord::getStudentId, user.getStudentId())
                .eq(RegistrationRecord::getBatchId, batchId));
    }

    @Override
    public Page<RegistrationRecord> getRecordList(int pageNum, int pageSize, Integer batchId, Integer status, String keyword) {
        Page<RegistrationRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<RegistrationRecord> wrapper = new LambdaQueryWrapper<>();

        if (batchId != null) {
            wrapper.eq(RegistrationRecord::getBatchId, batchId);
        }
        if (status != null) {
            wrapper.eq(RegistrationRecord::getStatus, status);
        }
        if (StringUtils.isNotBlank(keyword)) {
            // 需要关联查询或者子查询，这里简化处理，假设只查学号
            // 如果需要查姓名，可能需要联表，MP做联表略复杂，这里暂只支持学号搜索
            // 实际上可以通过自定义SQL或者In查询实现
            // 方案：先查User表匹配的studentId，再In查询
            List<String> studentIds = userMapper.selectList(new LambdaQueryWrapper<User>()
                    .like(User::getName, keyword)
                    .or()
                    .like(User::getStudentId, keyword))
                    .stream().map(User::getStudentId).toList();
            
            if (studentIds.isEmpty()) {
                return page; // 无匹配用户
            }
            wrapper.in(RegistrationRecord::getStudentId, studentIds);
        }
        
        wrapper.orderByDesc(RegistrationRecord::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public void reviewRecord(Integer id, Integer status, String reviewOpinion) {
        RegistrationRecord record = this.getById(id);
        if (record == null) {
            throw new BizException("报名记录不存在");
        }
        record.setStatus(status);
        record.setReviewOpinion(reviewOpinion);
        record.setReviewTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        this.updateById(record);
    }

    @Override
    public List<RegistrationRecord> exportRecords(Integer batchId, Integer status) {
        LambdaQueryWrapper<RegistrationRecord> wrapper = new LambdaQueryWrapper<>();
        if (batchId != null) {
            wrapper.eq(RegistrationRecord::getBatchId, batchId);
        }
        if (status != null) {
            wrapper.eq(RegistrationRecord::getStatus, status);
        }
        wrapper.orderByDesc(RegistrationRecord::getCreateTime);
        return this.list(wrapper);
    }
}





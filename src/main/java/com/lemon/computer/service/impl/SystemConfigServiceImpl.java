package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.domain.SystemConfig;
import com.lemon.computer.service.SystemConfigService;
import com.lemon.computer.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 针对表【system_config(系统配置表)】的数据库操作Service实现
 * @createDate 2025-12-31 00:56:48
 */
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    @Override
    public String getConfigValue(String key) {
        SystemConfig config = this.getOne(new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, key));
        return config != null ? config.getConfigValue() : null;
    }

    @Override
    public List<SystemConfig> getAllConfigs() {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfigs(Map<String, String> configs) {
        for (Map.Entry<String, String> entry : configs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            SystemConfig config = this.getOne(new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, key));

            if (config != null) {
                config.setConfigValue(value);
                config.setUpdateTime(LocalDateTime.now());
                this.updateById(config);
            } else {
                // 如果配置不存在，可以选择忽略或新建（根据需求，这里假设只更新已存在的配置）
                // 也可以选择新建：
                SystemConfig newConfig = new SystemConfig();
                newConfig.setConfigKey(key);
                newConfig.setConfigValue(value);
                newConfig.setCreateTime(LocalDateTime.now());
                this.save(newConfig);
            }
        }
    }
}





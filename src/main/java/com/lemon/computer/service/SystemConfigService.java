package com.lemon.computer.service;

import com.lemon.computer.domain.SystemConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【system_config(系统配置表)】的数据库操作Service
* @createDate 2025-12-31 00:56:48
*/
public interface SystemConfigService extends IService<SystemConfig> {

    /**
     * 根据键名获取配置值
     * @param key 配置键
     * @return 配置值
     */
    String getConfigValue(String key);

    /**
     * 获取所有配置（管理员用）
     * @return 配置列表
     */
    List<SystemConfig> getAllConfigs();

    /**
     * 批量更新配置
     * @param configs 配置键值对
     */
    void updateConfigs(Map<String, String> configs);
}

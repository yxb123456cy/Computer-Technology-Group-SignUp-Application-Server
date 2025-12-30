package com.lemon.computer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.computer.domain.SystemConfig;
import com.lemon.computer.service.SystemConfigService;
import com.lemon.computer.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【system_config(系统配置表)】的数据库操作Service实现
* @createDate 2025-12-31 00:56:48
*/
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig>
    implements SystemConfigService{

}





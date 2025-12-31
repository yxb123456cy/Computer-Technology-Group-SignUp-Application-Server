package com.lemon.computer.common.constants;

public class GlobalSystemConstants {

    // mapper扫描包配置;
    public static final String  MAPPER_SCAN_PATH="com.lemon.computer.mapper";
    // 安全阈值，限制批量更新或删除的记录数不超过 10000 条
    public static final Integer BATCH_UPDATE_LIMIT=10000;
    // 密码加密盐值
    public static final String PASSWORD_SALT = "lemon_computer_group";
}

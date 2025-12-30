package com.lemon.computer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色编码枚举
 */
@Getter
@AllArgsConstructor
public enum SysRoleCodeEnum {
    HR_ADMIN("hr_admin", "HR总管"),
    HR_NORMAL("hr_normal", "普通HR"),
    EMPLOYEE("employee", "普通员工"),
    JOB_SEEKER("job_seeker", "求职者");

    private final String code;
    private final String desc;
}

package com.lemon.computer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 职位级别枚举
 */
@Getter
@AllArgsConstructor
public enum JobLevelEnum {
    P1("P1", "P1"),
    P2("P2", "P2"),
    P3("P3", "P3"),
    P4("P4", "P4"),
    P5("P5", "P5"),
    P6("P6", "P6"),
    P7("P7", "P7"),
    P8("P8", "P8"),
    P9("P9", "P9"),
    P10("P10", "P10");

    private final String code;
    private final String desc;

    public static boolean isValid(String code) {
        for (JobLevelEnum value : values()) {
            if (value.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}

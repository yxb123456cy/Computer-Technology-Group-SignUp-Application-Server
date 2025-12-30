package com.lemon.computer.common.enums;


import com.lemon.computer.common.exceptions.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("AUTH-10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("AUTH-10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    SNIPPET_NOT_FOUND("SNIPPET-10001", "代码片段不存在或无权限访问"),
    TAG_NAME_EXISTS("TAG-10001", "标签名称已存在"),
    TAG_NOT_FOUND("TAG-10002", "标签不存在或无权限访问"),
    TAG_IN_USE("TAG-10003", "标签正在使用中，无法删除"),
    ;

    // 异常码
    private final String errorCode;
    // 错误信息
    private final String errorMessage;
}


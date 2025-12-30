package com.lemon.computer.cotroller;

import com.lemon.computer.common.constants.ApiVersion;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理员相关接口",description = "计科小组报名小程序及其配套后台管理系统-管理员相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    // TODO 待实现接口1:管理员后台登录;
    // TODO 待实现接口2:获取管理员信息;
    // TODO 待实现接口3:后台管理管理员退出登录;
}

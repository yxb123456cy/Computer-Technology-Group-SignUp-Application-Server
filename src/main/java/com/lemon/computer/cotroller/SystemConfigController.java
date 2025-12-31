package com.lemon.computer.cotroller;

import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.SystemConfig;
import com.lemon.computer.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "系统配置相关接口",description = "计科小组报名小程序及其配套后台管理系统-系统配置相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/systemConfig")
@Slf4j
@RequiredArgsConstructor
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    @GetMapping("/value")
    @Operation(summary = "获取指定配置值(公开)")
    public Response<String> getConfigValue(@RequestParam String key) {
        return Response.success(systemConfigService.getConfigValue(key));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有配置(管理员)")
    public Response<List<SystemConfig>> getAllConfigs() {
        return Response.success(systemConfigService.getAllConfigs());
    }

    @PutMapping("/update")
    @Operation(summary = "批量更新配置(管理员)")
    public Response<Void> updateConfigs(@RequestBody Map<String, String> configs) {
        systemConfigService.updateConfigs(configs);
        return Response.success();
    }
}

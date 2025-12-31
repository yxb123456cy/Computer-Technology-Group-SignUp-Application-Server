package com.lemon.computer.cotroller;

import com.lemon.computer.aspect.ApiOperationLog;
import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.Admin;
import com.lemon.computer.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "管理员相关接口",description = "计科小组报名小程序及其配套后台管理系统-管理员相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**@status success
     * @description 管理员登录
     * @param request AdminLoginRequest
     * @return Response<String>
     */
    @ApiOperationLog(description = "管理员登录")
    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Response<String> login(@RequestBody @Valid AdminLoginRequest request) {
        String token = adminService.login(request.getUsername(), request.getPassword());
        return Response.success(token);
    }
    /**@status success
     * @description 获取管理员信息
     * @return Response<Admin>
     */
    @ApiOperationLog(description = "获取管理员信息")
    @Operation(summary = "获取管理员信息")
    @GetMapping("/info")
    public Response<Admin> getAdminInfo() {
        return Response.success(adminService.getAdminInfo());
    }
    /**@status success
     * @description 管理员退出登录
     * @return Response<Void>
     */
    @ApiOperationLog(description = "管理员退出登录")
    @Operation(summary = "管理员退出登录")
    @PostMapping("/logout")
    public Response<Void> logout() {
        adminService.logout();
        return Response.success();
    }

    @Data
    public static class AdminLoginRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
    }
}

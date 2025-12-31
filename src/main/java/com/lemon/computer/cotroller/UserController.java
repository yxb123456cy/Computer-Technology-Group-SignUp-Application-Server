package com.lemon.computer.cotroller;

import com.lemon.computer.aspect.ApiOperationLog;
import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.exceptions.BizException;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.User;
import com.lemon.computer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "小程序用户相关接口", description = "计科小组报名小程序及其配套后台管理系统-小程序用户相关API")
@RestController
@RequestMapping("/api" + ApiVersion.V1 + "/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @ApiOperationLog(description = "用户注册")
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Response<String> register(@RequestBody @Valid UserRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BizException("两次输入的密码不一致");
        }
        String token = userService.register(request.getStudentId(), request.getPassword(), request.getName());
        return Response.success(token);
    }

    @ApiOperationLog(description = "用户登录")
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Response<String> login(@RequestBody @Valid UserLoginRequest request) {
        String token = userService.login(request.getStudentId(), request.getPassword());
        return Response.success(token);
    }

    @ApiOperationLog(description = "获取当前用户信息")
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Response<User> getUserInfo() {
        return Response.success(userService.getUserInfo());
    }

    @ApiOperationLog(description = "更新用户信息")
    @PutMapping("/info")
    @Operation(summary = "更新用户信息")
    public Response<Void> updateUserInfo(@RequestBody UserUpdateRequest request) {
        User user = new User();
        user.setAvatar(request.getAvatar());
        user.setName(request.getName());
        user.setMajor(request.getMajor());
        user.setGrade(request.getGrade());
        user.setClassRoom(request.getClassRoom());
        user.setPhone(request.getPhone());
        userService.updateUserInfo(user);
        return Response.success();
    }

    @ApiOperationLog(description = "修改密码")
    @PostMapping("/password")
    @Operation(summary = "修改密码")
    public Response<Void> updatePassword(@RequestBody @Valid UserPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BizException("两次输入的密码不一致");
        }
        userService.updatePassword(request.getOldPassword(), request.getNewPassword());
        return Response.success();
    }

    @ApiOperationLog(description = "退出登录")
    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Response<Void> logout() {
        userService.logout();
        return Response.success();
    }

    @Data
    public static class UserRegisterRequest {
        @NotBlank(message = "学号不能为空")
        private String studentId;
        @NotBlank(message = "密码不能为空")
        private String password;
        @NotBlank(message = "确认密码不能为空")
        private String confirmPassword;
        private String name;
    }

    @Data
    public static class UserLoginRequest {
        @NotBlank(message = "学号不能为空")
        private String studentId;
        @NotBlank(message = "密码不能为空")
        private String password;
    }

    @Data
    public static class UserUpdateRequest {
        private String avatar;
        private String name;
        private String major;
        private String grade;
        private String classRoom;
        private String phone;
    }

    @Data
    public static class UserPasswordRequest {
        @NotBlank(message = "原密码不能为空")
        private String oldPassword;
        @NotBlank(message = "新密码不能为空")
        private String newPassword;
        @NotBlank(message = "确认新密码不能为空")
        private String confirmNewPassword;
    }
}

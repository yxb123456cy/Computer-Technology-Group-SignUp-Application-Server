package com.lemon.computer.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.RegistrationBatch;
import com.lemon.computer.service.RegistrationBatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "报名批次相关接口",description = "计科小组报名小程序及其配套后台管理系统-报名批次相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/registrationBatch")
@Slf4j
@RequiredArgsConstructor
public class RegistrationBatchController {

    private final RegistrationBatchService registrationBatchService;

    @GetMapping("/active")
    @Operation(summary = "获取当前活跃批次")
    public Response<RegistrationBatch> getCurrentActiveBatch() {
        return Response.success(registrationBatchService.getCurrentActiveBatch());
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询批次列表")
    public Response<Page<RegistrationBatch>> getBatchList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Response.success(registrationBatchService.getBatchList(pageNum, pageSize));
    }

    @PostMapping("/add")
    @Operation(summary = "新增批次")
    public Response<Void> addBatch(@RequestBody @Valid BatchAddRequest request) {
        RegistrationBatch batch = new RegistrationBatch();
        batch.setBatchName(request.getBatchName());
        batch.setStartTime(request.getStartTime());
        batch.setEndTime(request.getEndTime());
        batch.setNotice(request.getNotice());
        registrationBatchService.addBatch(batch);
        return Response.success();
    }

    @PutMapping("/update")
    @Operation(summary = "更新批次")
    public Response<Void> updateBatch(@RequestBody @Valid BatchUpdateRequest request) {
        RegistrationBatch batch = new RegistrationBatch();
        batch.setId(request.getId());
        batch.setBatchName(request.getBatchName());
        batch.setStartTime(request.getStartTime());
        batch.setEndTime(request.getEndTime());
        batch.setNotice(request.getNotice());
        registrationBatchService.updateBatch(batch);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除批次")
    public Response<Void> deleteBatch(@PathVariable Integer id) {
        registrationBatchService.deleteBatch(id);
        return Response.success();
    }

    @PutMapping("/active/{id}")
    @Operation(summary = "设置活跃批次")
    public Response<Void> setActiveBatch(@PathVariable Integer id) {
        registrationBatchService.setActiveBatch(id);
        return Response.success();
    }

    @Data
    public static class BatchAddRequest {
        @NotBlank(message = "批次名称不能为空")
        private String batchName;
        @NotNull(message = "开始时间不能为空")
        private LocalDateTime startTime;
        @NotNull(message = "截止时间不能为空")
        private LocalDateTime endTime;
        private String notice;
    }

    @Data
    public static class BatchUpdateRequest {
        @NotNull(message = "批次ID不能为空")
        private Integer id;
        @NotBlank(message = "批次名称不能为空")
        private String batchName;
        @NotNull(message = "开始时间不能为空")
        private LocalDateTime startTime;
        @NotNull(message = "截止时间不能为空")
        private LocalDateTime endTime;
        private String notice;
    }
}

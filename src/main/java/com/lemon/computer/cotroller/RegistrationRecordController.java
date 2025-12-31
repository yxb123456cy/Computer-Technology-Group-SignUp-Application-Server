package com.lemon.computer.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.RegistrationRecord;
import com.lemon.computer.service.RegistrationRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "报名记录相关接口",description = "计科小组报名小程序及其配套后台管理系统-报名记录相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/registrationRecord")
@Slf4j
@RequiredArgsConstructor
public class RegistrationRecordController {

    private final RegistrationRecordService registrationRecordService;

    @PostMapping("/submit")
    @Operation(summary = "提交报名信息")
    public Response<Void> submitRegistration(@RequestBody @Valid RegistrationSubmitRequest request) {
        RegistrationRecord record = new RegistrationRecord();
        record.setBatchId(request.getBatchId());
        record.setIntention(request.getIntention());
        record.setIntroduction(request.getIntroduction());
        registrationRecordService.submitRegistration(record);
        return Response.success();
    }

    @GetMapping("/user/list")
    @Operation(summary = "获取当前用户的报名记录")
    public Response<List<RegistrationRecord>> getUserRecords() {
        return Response.success(registrationRecordService.getUserRecords());
    }

    @GetMapping("/user/status")
    @Operation(summary = "查询当前用户在指定批次的报名状态")
    public Response<RegistrationRecord> getUserRecordInBatch(@RequestParam Integer batchId) {
        return Response.success(registrationRecordService.getUserRecordInBatch(batchId));
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询报名记录(管理员)")
    public Response<Page<RegistrationRecord>> getRecordList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Integer batchId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String keyword) {
        return Response.success(registrationRecordService.getRecordList(pageNum, pageSize, batchId, status, keyword));
    }

    @PutMapping("/review")
    @Operation(summary = "审核报名记录(管理员)")
    public Response<Void> reviewRecord(@RequestBody @Valid RecordReviewRequest request) {
        registrationRecordService.reviewRecord(request.getId(), request.getStatus(), request.getReviewOpinion());
        return Response.success();
    }

    @Data
    public static class RegistrationSubmitRequest {
        @NotNull(message = "批次ID不能为空")
        private Integer batchId;
        @NotBlank(message = "报名意向不能为空")
        private String intention;
        private String introduction;
    }

    @Data
    public static class RecordReviewRequest {
        @NotNull(message = "记录ID不能为空")
        private Integer id;
        @NotNull(message = "审核状态不能为空")
        private Integer status;
        private String reviewOpinion;
    }
}

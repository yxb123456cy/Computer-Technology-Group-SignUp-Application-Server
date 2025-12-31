package com.lemon.computer.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import com.lemon.computer.domain.Feedback;
import com.lemon.computer.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "反馈相关接口",description = "计科小组报名小程序及其配套后台管理系统-反馈相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/feedback")
@Slf4j
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/submit")
    @Operation(summary = "提交反馈")
    public Response<Void> submitFeedback(@RequestBody @Valid FeedbackSubmitRequest request) {
        Feedback feedback = new Feedback();
        feedback.setType(request.getType());
        feedback.setContent(request.getContent());
        feedback.setContact(request.getContact());
        feedbackService.submitFeedback(feedback);
        return Response.success();
    }

    @GetMapping("/list")
    @Operation(summary = "分页查询反馈列表")
    public Response<Page<Feedback>> getFeedbackList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer status) {
        return Response.success(feedbackService.getFeedbackList(pageNum, pageSize, type, status));
    }

    @PutMapping("/handle/{id}")
    @Operation(summary = "处理反馈")
    public Response<Void> handleFeedback(@PathVariable Integer id) {
        feedbackService.handleFeedback(id);
        return Response.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除反馈")
    public Response<Void> deleteFeedback(@PathVariable Integer id) {
        feedbackService.deleteFeedback(id);
        return Response.success();
    }

    @Data
    public static class FeedbackSubmitRequest {
        @NotBlank(message = "反馈类型不能为空")
        private String type;
        @NotBlank(message = "反馈内容不能为空")
        private String content;
        private String contact;
    }
}

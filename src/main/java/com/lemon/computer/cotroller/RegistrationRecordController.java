package com.lemon.computer.cotroller;

import com.lemon.computer.common.constants.ApiVersion;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "报名记录相关接口",description = "计科小组报名小程序及其配套后台管理系统-报名记录相关API")
@RestController
@RequestMapping("/api"+ ApiVersion.V1+"/registrationRecord")
@Slf4j
@RequiredArgsConstructor
public class RegistrationRecordController {
}

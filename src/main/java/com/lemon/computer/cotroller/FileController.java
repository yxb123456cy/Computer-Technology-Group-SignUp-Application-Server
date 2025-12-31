package com.lemon.computer.cotroller;

import com.lemon.computer.common.constants.ApiVersion;
import com.lemon.computer.common.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件相关接口", description = "计科小组报名小程序及其配套后台管理系统-文件相关API")
@RestController
@RequestMapping("/api" + ApiVersion.V1 + "/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    //注入实列
    @Resource
    private FileStorageService fileStorageService;

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload")
    public Response<String> upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file).setPath("computer-report-app/") //保存到相对路径下，为了方便管理，不需要可以不写
                .upload();  //将文件上传到对应地方
        return fileInfo == null ? Response.fail("上传失败！") : Response.success(fileInfo.getUrl());
    }
}

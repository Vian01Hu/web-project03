package com.usermanagement.controller;
import com.usermanagement.pojo.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    /**
     * 上传头像文件
     * 前端的Element Plus组件默认会使用 "file" 作为字段名
     */
    @PostMapping
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file,
                                                            HttpServletRequest request) {
        try {
            log.info("接收到上传文件请求");
            // 检查文件类型
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            if (!isValidImageFormat(fileExtension)) {
                return ApiResponse.success("只支持PNG、JPEG、JPG格式的图片", null);
            }

            // 检查文件大小（2MB）
            if (file.getSize() > 2 * 1024 * 1024) {
                return ApiResponse.success("文件大小不能超过2MB", null);
            }
            // 创建上传目录
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    return ApiResponse.success("创建上传目录失败", null);
                }
            }

            // 生成唯一文件名
            String fileName = generateFileName(fileExtension);
            Path filePath = Paths.get(uploadPath, fileName);

            // 保存文件
            Files.copy(file.getInputStream(), filePath);

            // 生成完整的访问URL
            String baseUrl = getBaseUrl(request);
            String fullImageUrl = baseUrl + "/images/" + fileName;

            return ApiResponse.success("上传成功", fullImageUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * 验证图片格式
     */
    private boolean isValidImageFormat(String extension) {
        return extension.equals(".png") ||
                extension.equals(".jpg") ||
                extension.equals(".jpeg") ||
                extension.equals(".gif");
    }

    /**
     * 生成唯一文件名
     */
    private String generateFileName(String extension) {
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }


    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 获取基础URL
     */
    private String getBaseUrl(HttpServletRequest request) {
        // 方式1：从请求中获取（推荐）
        String scheme = request.getScheme(); // http 或 https
        String serverName = request.getServerName(); // localhost 或域名
        int serverPort = request.getServerPort(); // 端口

        String baseUrl = scheme + "://" + serverName;
        if (("http".equals(scheme) && serverPort != 80) ||
                ("https".equals(scheme) && serverPort != 443)) {
            baseUrl += ":" + serverPort;
        }
        baseUrl += contextPath;

        return baseUrl;

        // 方式2：使用配置（如果使用反向代理可能需要调整）
        // return "http://localhost:" + serverPort + contextPath;
    }
}
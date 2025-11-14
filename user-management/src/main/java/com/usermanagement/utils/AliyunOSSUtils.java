/*
package com.usermanagment.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public class AliyunOSSUtils {

    private static final Logger log = LoggerFactory.getLogger(AliyunOSSUtils.class);

    */
/**
     * 上传文件到阿里云OSS
     * @param endpoint endpoint域名
     * @param bucketName 存储空间的名字
     * @param content 内容字节数组
     * @param extName 文件扩展名（如：.jpg、.png）
     * @return 文件在OSS上的访问URL
     * @throws Exception 上传过程中可能出现的异常
     *//*

    public static String upload(String endpoint, String bucketName, byte[] content, String extName) throws Exception {
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 生成唯一的文件名
        String objectName = UUID.randomUUID() + (extName.startsWith(".") ? extName : "." + extName);

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content));

            // 可选：设置元数据
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setContentLength(content.length);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件到OSS
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            // 构建文件的访问URL
            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
            log.info("文件上传成功，URL: {}", fileUrl);

            return fileUrl;

        } catch (com.aliyun.oss.OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: " + oe.getErrorMessage());
            log.error("Error Code: " + oe.getErrorCode());
            log.error("Request ID: " + oe.getRequestId());
            log.error("Host ID: " + oe.getHostId());
            throw oe;
        } catch (com.aliyun.oss.ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ce.getMessage());
            throw ce;
        } finally {
            // 关闭OSSClient
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    */
/**
     * 上传文件并指定存储路径
     * @param endpoint endpoint域名
     * @param bucketName 存储空间的名字
     * @param content 内容字节数组
     * @param folder 存储文件夹路径（如：images/）
     * @param extName 文件扩展名
     * @return 文件在OSS上的访问URL
     * @throws Exception 上传过程中可能出现的异常
     *//*

    public static String uploadWithFolder(String endpoint, String bucketName, byte[] content, String folder, String extName) throws Exception {
        // 确保文件夹路径以/结尾
        if (!folder.endsWith("/")) {
            folder = folder + "/";
        }

        String objectName = folder + UUID.randomUUID() + (extName.startsWith(".") ? extName : "." + extName);

        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content));
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + objectName;
            log.info("文件上传成功，URL: {}", fileUrl);

            return fileUrl;

        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw e;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    */
/**
     * 删除OSS上的文件
     * @param endpoint endpoint域名
     * @param bucketName 存储空间的名字
     * @param objectName 文件在OSS上的完整路径
     * @throws Exception 删除过程中可能出现的异常
     *//*

    public static void deleteFile(String endpoint, String bucketName, String objectName) throws Exception {
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            ossClient.deleteObject(bucketName, objectName);
            log.info("文件删除成功: {}", objectName);
        } catch (Exception e) {
            log.error("文件删除失败: {}", objectName, e);
            throw e;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}*/

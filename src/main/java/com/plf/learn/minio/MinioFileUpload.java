package com.plf.learn.minio;

import io.minio.*;

/**
 * @author panlf
 * @date 2021/12/6
 */
public class MinioFileUpload {
    public static void main(String[] args) {
        try{
            MinioClient minioClient = MinioClient
                    .builder().endpoint("http://192.168.164.130:9000")
                    .credentials("admin","12345678").build();
            /*
            存储桶的命名定义规则为：
            1.存储桶名称必须介于 3 到 63 个字符之间
            2.存储桶名称只能由小写字母、数字、句点 (.) 和连字符 (-) 组成
            3.存储桶名称必须以字母或数字开头和结尾
            4.存储桶名称不得采用 IP 地址格式
            */
            String bucketName = "pdf-file";
            boolean found = minioClient
                    .bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!found){
                //如果不存在就创建
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            //上传文件
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object("阿里巴巴Java开发手册1.4.0.pdf")
                    .filename("C:\\Users\\panlf\\Desktop\\阿里巴巴Java开发手册1.4.0.pdf")
                    .build());

            //下载文件
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object("阿里巴巴Java开发手册1.4.0.pdf")
                    .filename("阿里巴巴Java开发手册1.4.0.pdf").build());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

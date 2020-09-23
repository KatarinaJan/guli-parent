package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import com.atguigu.servicebase.exception.GuliException;
import com.mysql.cj.util.StringUtils;
import lombok.SneakyThrows;
import org.apache.poi.util.StringUtil;
import org.apache.tomcat.util.bcel.Const;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-08 21:57
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流
        InputStream inputStream = null;
        try {
            // 获取上传文件流
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获取文件名称
        String fileName = file.getOriginalFilename();

        // 1、在文件名称中添加随机唯一值 去横杠 eqgs83tsg01.jpg
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        fileName = uuid + fileName;

        // 2、把文件按日期进行分类 2020/09/09/01.jpg
        // 获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" + fileName;

        // 调用oss方法实现上传
        // 参数1：bucket名称
        // 参数2：上传到oss文件路径和文件名称 /aa/bb/1.jpg(带路径) 1.jpg(不带路径)
        ossClient.putObject(bucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        // 把上传之后的文件路径进行返回
        // https://guli-file4.oss-cn-shanghai.aliyuncs.com/01.jpg
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }

    @Override
    public String upload(MultipartFile file) {

        // 获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl = null;

        try {
            // 创建oos实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
            // 判断实例是否存在
            if (!ossClient.doesBucketExist(bucketName)) {
                // 创建bucket
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            // 获取上传文件流
            InputStream inputStream = file.getInputStream();

            //构建日期路径：avatar/2019/02/26/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");
            //文件名：uuid.扩展名
            String fileName = UUID.randomUUID().toString(); // eaeg-serw-gsdf-afge
            String original = file.getOriginalFilename(); // 01.jpg
            assert original != null;
            String fileType = original.substring(original.lastIndexOf(".")); // .jpg
            String newName = fileName + fileType; // eaeg-serw-gsdf-afge.jpg
            String fileUrl = filePath + "/" + newName; // 2019/02/26/eaeg-serw-gsdf-afge.jpg

            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient
            ossClient.shutdown();

            //获取url地址 用于返回文件在oss上的fileUrl
            // https://guli-file4.oss-cn-shanghai.aliyuncs.com/01.jpg
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
        } catch (IOException e) {
            throw new GuliException(ResultCode.ERROR, "上传文件出现异常");
        }

        return uploadUrl;
    }

}

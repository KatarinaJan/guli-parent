package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @Project: guli-parent
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-21 10:09
 */
public class TestVod {

    /**
     * 视频上传 本地文件
     */
    @Test
    public void upload() {
        String accessKeyId = "LTAI4G241mS6dJwt5sEZfkaC";
        String accessKeySecret = "t57FYd0OzthNcRHlV2JoLsoBiRerLH";
        String title = "6 - What If I Want to Move Faster.mp4 - upload by sdk"; // 上传之后文件名称
        String fileName = "D:\\File\\学习资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4"; // 本地文件路径和名称
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 根据视频ID获取播放凭证
     * @throws ClientException HttpClient异常
     */
    @Test
    public void getVideoAuth() throws ClientException {
        // 初始化客户端、请求对象和相应对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G241mS6dJwt5sEZfkaC", "t57FYd0OzthNcRHlV2JoLsoBiRerLH");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response;
        // 设置视频id
        request.setVideoId("448c015b4b0a481aa2d883b6e51c251c");
        // 获取请求响应
        response = client.getAcsResponse(request);
        // 输出请求结果
        // 播放凭证
        System.out.println("PlayAuth => " + response.getPlayAuth());
        // VideoMeta信息
        System.out.println("VideoMeta.Title => " + response.getVideoMeta().getTitle());
        // request id
        System.out.println("RequestId => " + response.getRequestId());
    }

    /**
     * 根据视频ID获取视频地址
     * @throws ClientException HttpClient异常
     */
    @Test
    public void getVideoAddress() throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4G241mS6dJwt5sEZfkaC", "t57FYd0OzthNcRHlV2JoLsoBiRerLH");
        // 创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;
        // 向request对象里面设置视频id
        request.setVideoId("448c015b4b0a481aa2d883b6e51c251c");
        // 调用初始化对象里面的方法，传递request获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        // 播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo: playInfoList){
            System.out.println("PlayInfo.PlayURL => " + playInfo.getPlayURL());
        }
        // Base信息
        System.out.println("VideoBase.Title => " + response.getVideoBase().getTitle());
        System.out.println("RequestId => " + response.getRequestId());
    }
}
